#!/usr/bin/env ruby

require "json"

map = {}

for file in ARGV[0..-1]
  docs = []
  open(file, "r") do |f|
    docs = JSON.load(f)
  end
  STDERR.write("read #{file} (#{docs.length})\n")
  for doc in docs
    map[[doc["wikiId"],doc["type"]]] ||= []
    map[[doc["wikiId"],doc["type"]]] << doc
  end
end
out = map.values.map { |list| if list[0]["type"] != "other" then list.shuffle[0..3] else list.shuffle[0..10] end  }.flatten
STDERR.write("write #{out.length}\n")
puts JSON.pretty_generate(out)
