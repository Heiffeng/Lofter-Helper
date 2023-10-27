package site.achun.lofter.pages.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Comment;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.pages.PostPage;
import site.achun.lofter.utils.UrlHandler;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExtractor {

    protected Document document;

    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * 获取发布时间
     * @return
     */
    public LocalDateTime getPostTime(){
        return null;
    }


    public List<Comment> getComments(){
        return null;
    }

    public Integer getHot(){
        return null;
    }

    public String nextPage(){
        Element element = document.body().select("div.m-pager > a.next").first();
        if(element!=null){
            return getHost() + element.attr("href");
        }
        return null;
    }

    public String getHost(){
        String url = document.baseUri();
        if(url == null) return null;
        try {
            UrlHandler handler = UrlHandler.create(url);
            return handler.getProtocol()+"://"+handler.getHost();
        } catch (MalformedURLException e) {}
        return null;
    }


    public List<String> getPostLinks(){
        Elements eles = document.body().select(".m-post");
        return eles.stream().map(ele -> ele.select("div.pic > a").first().attr("href")).collect(Collectors.toList());
    }

    public List<String> getTags(){
        Elements eles = document.body().select("div.box > a.tag");
        if(eles == null || eles.size() == 0) return new ArrayList<>();
        return eles.stream().map(ele->ele.html()).collect(Collectors.toList());
    }
    public String getContent(){
        return document.body().select("div.text").html();
    }
    public List<Picture> getPictures(){
        return null;
    }
    public abstract String getThemeCode();

}
