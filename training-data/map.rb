#!/usr/bin/env ruby

require "json"
require "set"

typeMap = {
  "game" => "video_game",
  "tv_story" => "tv_episode"
}

outDocs = []
count = 0
countOthers = 0
for file in ARGV[0..-1]
  docs = []
  open(file, "r") do |f|
    docs = JSON.load(f)
  end
  STDERR.write("read #{file} (#{docs.length})\n")
  for doc in docs
    if typeMap[doc["type"]]
      doc["type"] = typeMap[doc["type"]]
      count += 1
    end
    outDocs << doc
  end
  goodTypes = outDocs.group_by { |x| x["type"] }.reject { |type, x| x.length < 50 }.map { |type, x| x[0]['type'] }
  STDERR.write( "types left: #{goodTypes}\n" )
  typeSet = Set.new goodTypes
  for doc in docs
    if not typeSet.include? doc['type']
      doc["type"] = 'other'
      countOthers += 1
    end
    outDocs << doc
  end

end
STDERR.write("#{countOthers} changed to \"other\"\n")
STDERR.write("write #{outDocs.length} (#{count} changed)\n")
puts JSON.pretty_generate(outDocs)
