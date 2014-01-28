require "net/http"
require 'json'

while true do
  line = gets
  break if line == nil
  wikiId, pageId, title = line.split
  response = Net::HTTP.get_response("www.wikia.com",
    "/wikia.php?controller=WikisApi&method=getWikiData&ids=" + wikiId)
  data = JSON.parse(response.body)
  puts "#{data["items"][0]["url"]}wiki/#{title}"

end
