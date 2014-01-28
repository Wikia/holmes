require "net/http"
require "uri"
require "json"

class CouchSource
  def initialize
    @docsUrl = "http://arturdwornik.no-ip.biz:5984/article_types/_all_docs?include_docs=true"
  end

  def getDocs
    response = Net::HTTP.get_response(URI(@docsUrl))
    data = JSON.parse(response.body)
    docs = data["rows"].map { |x| x["doc"] }
    log("fetched #{docs.length}")
    return docs
  end
end
