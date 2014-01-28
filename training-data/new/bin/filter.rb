#!/usr/bin/env ruby

require "json"

require "log"
require "filters"

docs = JSON.parse(STDIN.each_line.to_a.join(""))

docs = CompositeFilter.default.do(docs)

puts JSON.pretty_generate(docs)
