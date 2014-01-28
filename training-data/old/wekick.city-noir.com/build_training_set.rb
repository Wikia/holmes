require "net/http"
require "uri"
require 'json'

def log( str )
  STDERR.write("#{str}\n")
end

def getWikiData( wikiId )
  @@wikidataCache ||= {}
  return @@wikidataCache[wikiId] if @@wikidataCache[wikiId]
  response = Net::HTTP.get_response("www.wikia.com",
    "/wikia.php?controller=WikisApi&method=getWikiData&ids=#{wikiId}")
  data = JSON.parse(response.body)
  
  @@wikidataCache[wikiId] = {
    :url => data["items"][0]["url"],
    :title => data["items"][0]["title"],
  }
end

def getCurrentRevision( wikiUrl, pageId )
  response = Net::HTTP.get_response( URI(wikiUrl).host,
    "/api.php?action=query&prop=revisions&format=json&rvprop=content&rvlimit=1&pageids=#{pageId}&redirects=")
  log "#{wikiUrl}/api.php?action=query&prop=revisions&format=json&rvprop=content&rvlimit=1&pageids=#{pageId}&redirects="
  data = JSON.parse(response.body)
  if data and not data.is_a?(Array) and data["query"] and data["query"]["pages"] and data["query"]["pages"][pageId.to_s]
    pageData = data["query"]["pages"][pageId.to_s]
    if not pageData["missing"]
      {
        :pageId => pageData["pageid"],
        :namespace => pageData["ns"],
        :title => pageData["title"],
        :revision => pageData["revisions"][0]["*"]
      }
    else
      nil
    end
  else
    nil
  end
end

def getPageData( wikiUrl, pageId )
  response = Net::HTTP.get_response( URI(wikiUrl).host,
    "/wikia.php?controller=ArticlesApi&method=getDetails&ids=#{pageId}")
  data = JSON.parse(response.body)
  if data["items"] and data["items"][pageId.to_s]
    {
      :pageId => pageId,
      :url => data["items"][pageId.to_s]["url"],
      :basepath => data["items"][pageId.to_s]["basepath"],
      :title => data["items"][pageId.to_s]["title"],
    }
  end
end

def with_retry( times )
  for i in 1..times
    begin
      yield
      break
    rescue Exception => ex
      log("will retry due to #{ex}")
      if i < times
        sleep 2**i
        log("retrying (attempt:#{i+1})")
      else
        raise ex
      end
    end
  end
end

out = []

while true do
  line = gets
  break if line == nil
  wikiId, pageId, type = line.split(";")
  wikiId, pageId, type = [wikiId[1..-2].to_i, pageId[1..-2].to_i, type.strip[1..-2]]
  wikiData = nil
  with_retry(10) do
    wikiData = getWikiData( wikiId )
  end
  #pageData = getPageData( wikiData[:url], pageId )
  if wikiData
    begin
      revData = nil
      with_retry(8) do
        revData = getCurrentRevision( wikiData[:url], pageId )
      end
      if wikiData and revData
        log "#{wikiData[:url]} #{revData[:title]} #{type}"
        out.push(
          :wikiId => wikiId,
          :pageId => revData[:pageId],
          :namespace => revData[:namespace],
          :title => revData[:title],
          :wikiText => revData[:revision],
          :type => type
        )
      else
        log "skip #{line.strip}"
      end
    rescue Exception => ex
      log( ex )
      log("failed load #{line}")
    end
  else
    log( "cannot get data for wikiId: #{wikiId}" )
  end
end

puts JSON.pretty_generate(out)
