package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Picture;

import java.util.List;
import java.util.stream.Collectors;

public class Post {
    private Document document;
    public Post(Document document){
        this.document = document;
    }

//    body > div > div.g-mn > div > div > div.m-detail.m-detail-img > div > div.ct > div > div.pic > a

    public List<Picture> getPictures(){
        System.out.println(document.body());
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

    public String getContent(){
        return document.body().select("div.text").html();
    }

}
