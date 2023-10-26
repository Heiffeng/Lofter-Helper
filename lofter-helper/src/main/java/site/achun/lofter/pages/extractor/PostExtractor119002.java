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
//        body > div.bcmt > div.bcmtlst > ul > li:nth-child(1)
//
        System.out.println(document.body().html());
        Elements elements = document.body().select("div.bcmtlsta");
        if(elements == null || elements.size() == 0){
            return null;
        }
        List<Comment> list = elements.stream().map(ele -> {
            Comment comment = new Comment();
            UserInfo userInfo = new UserInfo();
            userInfo.setUrl(ele.select("a.bcmtlstk").attr("href"));
            userInfo.setUserName(ele.select("a.bcmtlstk").html());
            comment.setUserInfo(userInfo);
            comment.setContent(ele.select("span.bcmtlstf.s-fc4.itag").html());
            return comment;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public Integer getHot() {
        return -1;
    }

}
