#!/usr/bin/env ruby

require "net/http"
require "uri"
require "json"

def fetch( url, limit = 5 )
  return if limit < 1
  response = Net::HTTP.get_response( URI(url) )
  case response
    when Net::HTTPSuccess     then response.body
    when Net::HTTPRedirection then fetch(response['location'], limit - 1)
    else
      response.error!
  end
end

def getWikiId( url )
  content = fetch(URI(url))
  m = content.match(/wgCityId=\"(\d+)\"/)
  if m
    return m[1].to_i
  end
end

def getWikiIdCached( url )
  @@wikiIdCache ||= {}
  @@wikiIdCache[url] ||= getWikiId(url)
end

content = STDIN.readlines.join
json = JSON.parse(content)

i = 1
for page in json
  id = getWikiIdCached(page["wikiUrl"])
  STDERR.write( "#{page["wikiUrl"]} #{id} #{i}/#{json.length}\n")
  page["wikiId"] = id
  i+=1
end
puts JSON.pretty_generate(json)

