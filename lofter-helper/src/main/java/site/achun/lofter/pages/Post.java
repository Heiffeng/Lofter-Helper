package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Picture;

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
//        body > div > div.g-bd > div > div.m-postdtl > div > div > div.info.box > div.tags.box > a
        Elements eles = document.body().select("div.box > a.tag");
        if(eles == null || eles.size() == 0) return new ArrayList<>();
        return eles.stream().map(ele->ele.html()).collect(Collectors.toList());
    }

    public String getContent(){
        return document.body().select("div.text").html();
    }

}
