#!/usr/bin/env ruby

require "net/http"
require "uri"
require 'json'

path = "/article_types/"
host = "arturdwornik.no-ip.biz"
port = 5984

docs = JSON.parse(STDIN.each_line.to_a.join(""))
i = 0
for doc in docs
  id = "#{doc["wikiId"]}_#{doc["pageId"]}"
  puts id
  req = Net::HTTP::Put.new(path + id, 'Content-Type' => 'application/json')
  req.body = JSON.dump(doc)
  begin
    response = Net::HTTP.new(host, port).start {|http| http.request(req) }
    puts response.body
  rescue
    puts "retry"
    sleep 10
    begin
      response = Net::HTTP.new(host, port).start {|http| http.request(req) }
      puts response.body
    rescue
      puts "fail"
    end
  end
  i += 1
  if i % 20 == 0 then sleep 1 end
end

