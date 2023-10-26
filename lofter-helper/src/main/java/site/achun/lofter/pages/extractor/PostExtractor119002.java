package site.achun.lofter.pages.extractor;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.achun.lofter.bean.Comment;
import site.achun.lofter.bean.UserInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostExtractor119002 extends AbstractPostExtractor{
    @Override
    public LocalDateTime getPostTime() {
        Element ele = document.body().select("div.info.box > a.date").first();
        String dateString = ele.html();
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return LocalDateTime.of(localDate, LocalTime.of(0,0));
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }

    @Override
    public Integer getHot() {
        return -1;
    }

}
