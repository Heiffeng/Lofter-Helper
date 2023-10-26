package site.achun.lofter.pages.extractor;

import org.jsoup.nodes.Document;
import site.achun.lofter.bean.Comment;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractPostExtractor {
    protected Document document;

    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * 获取发布时间
     * @return
     */
    public abstract LocalDateTime getPostTime();


    public abstract List<Comment> getComments();

    public abstract Integer getHot();


}
