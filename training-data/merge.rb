#!/usr/bin/env ruby

require "json"

map = {}
merged = []

for file in ARGV[0..-1]
  docs = []
  open(file, "r") do |f|
    docs = JSON.load(f)
  end
  STDERR.write("read (#{docs.length})\n")
  for doc in docs
    map[[doc["wikiId"],doc["pageId"]]] ||= doc
  end
end
STDERR.write("write #{map.values.length}\n")
puts JSON.pretty_generate(map.values)
