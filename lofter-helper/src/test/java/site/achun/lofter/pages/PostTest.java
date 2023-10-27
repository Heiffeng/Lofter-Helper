package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.utils.HttpClientUtil;

import java.io.IOException;

public class PostTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://chuancao.lofter.com/post/3d3eaa_2b9db5835";
        Document document = HttpClientUtil.getDocument(url);
        Post post = new Post(document);
        System.out.println(post.getPostTime());
        post.getPictures().stream().map(Picture::url).forEach(System.out::println);
        post.getTags().stream().forEach(System.out::println);
        System.out.println(post.getContent());
    }

}
