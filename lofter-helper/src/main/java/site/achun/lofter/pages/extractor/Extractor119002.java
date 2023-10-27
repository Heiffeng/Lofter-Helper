package site.achun.lofter.pages.extractor;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Comment;
import site.achun.lofter.bean.Picture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Extractor119002 extends AbstractExtractor {
    @Override
    public LocalDateTime getPostTime() {
        Element ele = document.body().select("div.info.box > a.date").first();
        String dateString = ele.html();
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return LocalDateTime.of(localDate, LocalTime.of(0,0));
    }

    @Override
    public List<Picture> getPictures() {
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

    @Override
    public String getContent() {
        return document.body().select("div.text").html();
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }

    @Override
    public Integer getHot() {
        return -1;
    }

    @Override
    public String getThemeCode() {
        return "119002";
    }

}
