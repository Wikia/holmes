
require "filters"

LogConfig.disable_logs

describe MappingFilter, "#do" do
  it "maps types of input files" do
    filter = MappingFilter.new({ "a" => "b" })
    docs = filter.do([{"type" => "a"}, {"type"=> "b"}])
    docs.each do |doc|
      doc["type"].should eql("b")
    end
  end

  it "handles empty list well" do
    filter = MappingFilter.new
    docs = filter.do([])
    docs.should eql([])
  end

  it "ingores unmapped types and returns docs in same order" do
    filter = MappingFilter.new({ "a" => "b" })
    docs = filter.do([{"type" => "x"}, {"type"=> "y"}])
    docs[0]["type"].should eql("x")
    docs[1]["type"].should eql("y")
  end
end

describe DocSizeFilter, "#do" do
  it "removes documents with wikiText smaller than x characters" do
    filter = DocSizeFilter.new(13)
    docs = filter.do([{"pageId" => 1,"wikiText" => "123"}, {"pageId" => 2, "wikiText" => "a"*13}])
    docs.should eql([{"pageId" => 2, "wikiText" => "a" * 13}])
  end

  it "handles empty list well" do
    filter = DocSizeFilter.new(2)
    docs = filter.do([])
    docs.should eql([])
  end
end

describe TypeFilter, "#do" do
  it "changes type of not white-listed types to \"other\"" do
    filter = TypeFilter.new ["a", "b", "c"]
    docs = filter.do([{"type" => "x"}, {"type"=>"b"}])
    docs.should eql([{"type" => "other"}, {"type"=>"b"}])
  end

  it "handles empty list well" do
    filter = TypeFilter.new []
    docs = filter.do([])
    docs.should eql([])
  end
end

describe LimitDocsPerWikiAndTypeFilter, "#do" do
  it "limits number of documents per pair (wiki,type) to given number (2)" do
    filter = LimitDocsPerWikiAndTypeFilter.new(2,42)
    docs = filter.do([
                     {"wikiId" => 10, "pageId" => 1, "type" => "foo"},
                     {"wikiId" => 10, "pageId" => 2, "type" => "foo"},
                     {"wikiId" => 10, "pageId" => 3, "type" => "foo"},
                     {"wikiId" => 99, "pageId" => 3, "type" => "foo"},

                     {"wikiId" => 10, "pageId" => 1, "type" => "other"},
                     {"wikiId" => 10, "pageId" => 2, "type" => "other"},
                     {"wikiId" => 10, "pageId" => 3, "type" => "other"},
                     {"wikiId" => 10, "pageId" => 4, "type" => "other"},
                     {"wikiId" => 99, "pageId" => 3, "type" => "other"},
    ])
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[10, "foo"]].length.should == 2
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[10, "other"]].length.should == 4
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[99, "foo"]].length.should == 1
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[99, "other"]].length.should == 1
    docs.length.should == 8
  end

  it "uses separate limit for \"other\" class" do
    filter = LimitDocsPerWikiAndTypeFilter.new(42,3)
    docs = filter.do([
                     {"wikiId" => 10, "pageId" => 1, "type" => "foo"},
                     {"wikiId" => 10, "pageId" => 2, "type" => "foo"},
                     {"wikiId" => 10, "pageId" => 3, "type" => "foo"},
                     {"wikiId" => 99, "pageId" => 3, "type" => "foo"},

                     {"wikiId" => 10, "pageId" => 1, "type" => "other"},
                     {"wikiId" => 10, "pageId" => 2, "type" => "other"},
                     {"wikiId" => 10, "pageId" => 3, "type" => "other"},
                     {"wikiId" => 10, "pageId" => 4, "type" => "other"},
                     {"wikiId" => 99, "pageId" => 3, "type" => "other"},
    ])
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[10, "foo"]].length.should == 3
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[10, "other"]].length.should == 3
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[99, "foo"]].length.should == 1
    docs.group_by { |x| [ x["wikiId"], x["type"] ] }[[99, "other"]].length.should == 1
    docs.length.should == 8
  end

  it "handles empty list well" do
    filter = LimitDocsPerWikiAndTypeFilter.new(2,3)
    docs = filter.do([])
    docs.should eql([])
  end
end

