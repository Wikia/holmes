#!/usr/bin/env ruby
require "net/http"
require "uri"
require 'json'

@@docsUrl = "http://megimikos.pl:5984/facts/_all_docs?include_docs=true"

def log( str )
  STDERR.write("#{str}\n")
end

def getDocs()
  response = Net::HTTP.get_response(URI(@@docsUrl))
  data = JSON.parse(response.body)
  data["rows"].map { |x| x["doc"] }
end

def getCurrentRevision( wikiUrl, pageId )
  response = Net::HTTP.get_response( URI("http://#{wikiUrl}").host,
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

out = []

for doc in getDocs()
  begin
    rev = getCurrentRevision( doc["domain"], doc["articleId"] )
  rescue Exception => err
    log(err)
  end
  if rev
    out.push(
      "wikiId" => doc["cityId"],
      "pageId" => doc["articleId"],
      "title" => doc["title"],
      "wikiText" => rev[:revision],
      "type" => doc["type"]
    )
  else
    log( "missing: #{doc["url"]}" )
  end
end

puts out.to_json
