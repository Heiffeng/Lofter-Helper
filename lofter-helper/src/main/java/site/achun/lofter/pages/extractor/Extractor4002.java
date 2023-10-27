package site.achun.lofter.pages.extractor;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.pages.PostPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Extractor4002 extends AbstractExtractor{

    @Override
    public String nextPage() {
        Element element = document.body().select("body > div.body.index > a.page.nxt").first();
        if(element!=null){
            return getHost() + element.attr("href");
        }
        return super.nextPage();
    }

    @Override
    public List<String> getPostLinks() {
        Elements eles = document.body().select("body > div.body.index > ul > li > a");
        return eles.stream().map(ele -> ele.attr("href")).collect(Collectors.toList());
    }

    @Override
    public List<Picture> getPictures() {
        Elements pictureElements = document.body().select("div.content > a.imgclasstag");
        List<Picture> pictures = pictureElements.stream().map(ele -> {
            String src = ele.attr("bigimgsrc");
            String group = ele.attr("imggroup");
            String width = ele.attr("bigimgwidth");
            String height = ele.attr("bigimgheight");
            return new Picture(src, group, Long.parseLong(width), Long.parseLong(height));
        }).collect(Collectors.toList());
        return pictures;
    }

    @Override
    public String getContent() {
        return document.body().select("div.article > div.text").html();
    }

    @Override
    public List<String> getTags() {
        Elements eles = document.body().select("div.info > ul > li.itm1 > a");
        if(eles == null || eles.size() == 0) return new ArrayList<>();
        return eles.stream().map(ele->ele.html()).collect(Collectors.toList());
    }

    @Override
    public String getThemeCode() {
        return "4002";
    }
}
