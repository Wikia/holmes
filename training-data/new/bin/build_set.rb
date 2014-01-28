#!/usr/bin/env ruby

require "json"

require "log"
require "sources"
require "filters"

docs = CouchSource.new.getDocs

docs = CompositeFilter.default.do(docs)

puts JSON.pretty_generate(docs)
