package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Comment;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.utils.UrlHandler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Post {
    private Document document;
    public Post(Document document){
        this.document = document;
    }

    public List<Picture> getPictures(){
        Elements pictureElements = document.body().select("div.pic > a");
        List<Picture> pictures = pictureElements.stream().map(ele -> {
            String src = ele.attr("bigimgsrc");
            String group = ele.attr("imggroup");
            String width = ele.attr("bigimgwidth");
            String height = ele.attr("bigimgheight");
            return new Picture(src, group, Long.parseLong(width), Long.parseLong(height));
        }).collect(Collectors.toList());
        return pictures;
    }

    public List<String> getTags(){
        Elements eles = document.body().select("div.box > a.tag");
        if(eles == null || eles.size() == 0) return new ArrayList<>();
        return eles.stream().map(ele->ele.html()).collect(Collectors.toList());
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

    public String getThemeName(){
        return null;
    }

    public Integer getHot(){
        return -1;
    }
    public List<Comment> getComments(){
        return null;
    }
}
