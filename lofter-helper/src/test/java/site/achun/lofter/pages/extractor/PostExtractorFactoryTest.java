package site.achun.lofter.pages.extractor;

import org.jsoup.nodes.Document;
import site.achun.lofter.bean.Comment;
import site.achun.lofter.utils.HttpClientUtil;

import java.io.IOException;

public class PostExtractorFactoryTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://sakalee.lofter.com/post/acbce_1cb620f89";
        Document document = HttpClientUtil.getDocument(url);
        AbstractPostExtractor extractor = PostExtractorFactory.create("119002", document);
        System.out.println(extractor.getPostTime());
        for (Comment comment : extractor.getComments()) {
            System.out.println(comment.getContent());
        }
    }
}
