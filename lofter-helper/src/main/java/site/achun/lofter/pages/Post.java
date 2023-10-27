package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Comment;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.pages.extractor.AbstractExtractor;
import site.achun.lofter.pages.extractor.ExtractorFactory;
import site.achun.lofter.utils.UrlHandler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Post {
    private Document document;

    private AbstractExtractor extractor;
    public Post(Document document){
        this.document = document;
        this.extractor = ExtractorFactory.create(document);
    }

    public List<Picture> getPictures(){
        return extractor.getPictures();
    }

    public List<String> getTags(){
        return extractor.getTags();
    }

    public String getContent(){
        return document.body().select("div.text").html();
    }

    public String getPostCode(){
        try {
            return UrlHandler.create(document.baseUri()).getPath().replace("/post/","");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPostTime() {
        Elements eles = document.body().select("a.date");
        if(eles != null && eles.size() > 0 ){
            return eles.first().html();
        }
        return null;
    }

    public Integer getHot(){
        return -1;
    }
    public List<Comment> getComments(){
        return extractor.getComments();
    }

    public Document getDocument() {
        return document;
    }

    public AbstractExtractor getExtractor() {
        return extractor;
    }
}
