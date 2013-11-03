#!/usr/bin/env ruby

require "json"
require "set"

outDocs = []
for file in ARGV[0..-1]
  docs = []
  open(file, "r") do |f|
    docs = JSON.load(f)
  end
  outDocs += docs
end

puts "small docs: #{outDocs.reject{|x| x['wikiText'].length > 100}.map {|x| x['title']}}"
groups = outDocs.group_by { |x| x["type"] }.map { |type, set| [set.length, type] }.to_a
groups.sort! { |a,b| a[0] <=> b[0] }
puts groups

