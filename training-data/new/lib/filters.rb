require "log"
require "set"

class MappingFilter
  attr_accessor :type_map
  def initialize type_map = nil
    if type_map == nil
      type_map = {
        "game" => "video_game",
        "tv_story" => "tv_episode",
        "game_expansion" => "video_game",
        "game_show" => "tv_show",
        "flash_game" => "mini_game",
      }
    end
    @type_map = type_map
  end

  def do(docs)
    log("mapping types")
    outDocs = []
    changeCounters = {}
    for oldDoc in docs
      doc = oldDoc.clone
      if @type_map[doc["type"]]
        changeCounters[doc["type"]] ||= 0
        changeCounters[doc["type"]] += 1
        doc["type"] = @type_map[doc["type"]]
      end
      outDocs << doc
    end

    for type, count in changeCounters
      log "  #{count} changes #{type} => #{@type_map[type]}"
    end
    return outDocs
  end
end

class TypeFilter
  def initialize white_list
    @white_list = Set.new white_list
  end

  def do(docs)
    log("narrowing down types to #{@white_list.to_a}")
    groups = Hash[docs.group_by {|doc| doc["type"]}.map { |type, docs| [type, docs.length] }]
    log(groups)
    for type, size in groups
      if not @white_list.include?(type)
        log("  changing #{size} of type #{type} to other")
      end
    end
    log(" -- ")
    for type in @white_list
      size = groups[type] || 0
      log("  leaving #{size} of type #{type}")
    end

    newDocs = []
    for doc in docs
      newDoc = doc.clone
      if not @white_list.include?(doc["type"])
        newDoc["type"] = "other"
      end
      newDocs << newDoc
    end
    return newDocs
  end
end

class DocSizeFilter
  def initialize min_size
    @min_size = min_size
  end

  def do docs
    log("rejecting docs smaller than #{@min_size}")
    filteredDocs = docs.reject {|doc| ( doc["wikiText"] || "" ).length < @min_size}.map { |doc| doc.clone }
    log("  #{docs.length - filteredDocs.length} rejected from #{docs.length} leaving #{filteredDocs.length} documents")
    return filteredDocs
  end
end


class LimitDocsPerWikiAndTypeFilter
  def initialize(max_docs, max_others)
    @max_docs = max_docs
    @max_others = max_others
  end

  def do(docs)
    groups = docs.group_by { |x| [x["wikiId"],x["type"]] }
    filteredDocs = []
    log("limit number of docs per wiki and type to #{@max_docs} (#{@max_others} for others)") 
    for key, group in groups
      filteredGroup = group.shuffle
      size = (if key[1] != "other"
        @max_docs
      else
        @max_others
      end)
      if group.length > size
        log( "  rejecting #{group.length - size} from #{key}" )
        filteredGroup = group.shuffle.to_a[0..size-1]
        filteredDocs += filteredGroup
      else
        filteredDocs += group
      end
    end
    return filteredDocs
  end
end

class CompositeFilter
  def initialize(filters)
    @filters = filters
  end
  def self.default
    types = [
      "movie",
      "character",
      "person",
      "tv_season",
      "tv_series",
      "tv_episode",
      "book",
      "comic_book",
      "video_game",
      "location",
      "organization",
      "weapon",
      "mini_game"
    ]

    filters = [
      DocSizeFilter.new(400),
      LimitDocsPerWikiAndTypeFilter.new(5,10),
      MappingFilter.new,
      TypeFilter.new(types)
    ]
    return CompositeFilter.new(filters)
  end

  def do(docs)
    for filter in @filters
      docs = filter.do(docs)
    end
    return docs
  end
end
