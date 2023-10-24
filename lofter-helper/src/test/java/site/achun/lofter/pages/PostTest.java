package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import site.achun.lofter.utils.HttpClientUtil;

import java.io.IOException;

public class PostTest {

    public static void main(String[] args) throws IOException, InterruptedException {
//    https://sakalee.lofter.com/post/acbce_45a41f
        String url = "https://sboneqiu.lofter.com/post/1d631d67_125fc4a5";
        Document document = HttpClientUtil.getDocument(url);
        Post post = new Post(document);
//        post.getPictures().stream().forEach(System.out::println);
        post.getTags().stream().forEach(System.out::println);
    }
}
