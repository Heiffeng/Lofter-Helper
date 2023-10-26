package site.achun.lofter.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import site.achun.lofter.bean.PostInfo;
import site.achun.lofter.utils.HttpClientUtil;

import java.io.IOException;
import java.nio.file.Path;

public class PostTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://sboneqiu.lofter.com/post/1d631d67_125fc4a5";
        Document document = HttpClientUtil.getDocument(url);
        Post post = new Post(document);
        System.out.println(post.getPostTime());
    }

}
