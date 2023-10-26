package site.achun.lofter.pages.extractor;

import site.achun.lofter.bean.Comment;

import java.time.LocalDateTime;
import java.util.List;

public class DefaultPostExtractor extends AbstractPostExtractor{

    @Override
    public LocalDateTime getPostTime() {
        return null;
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
