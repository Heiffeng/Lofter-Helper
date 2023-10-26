package site.achun.lofter.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import site.achun.lofter.bean.PostInfo;
import site.achun.lofter.utils.HttpClientUtil;

import java.io.IOException;
import java.nio.file.Path;

public class PostTest {

//    public static void main(String[] args) throws IOException, InterruptedException {
////    https://sakalee.lofter.com/post/acbce_45a41f
//        String url = "https://sboneqiu.lofter.com/post/1d631d67_125fc4a5";
//        Document document = HttpClientUtil.getDocument(url);
//        Post post = new Post(document);
////        post.getPictures().stream().forEach(System.out::println);
//        post.getTags().stream().forEach(System.out::println);
//        System.out.println(post.getPostCode());
//    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://sboneqiu.lofter.com/post/1d631d67_125fc4a5";
        Document document = HttpClientUtil.getDocument(url);
        Post post = new Post(document);
        PostInfo postInfo = new PostInfo();
        postInfo.setContent(post.getContent());
        postInfo.setTags(post.getTags());
        postInfo.setPictures(post.getPictures());
        postInfo.setPostTime(post.getPostTime());
        postInfo.setHot(post.getHot());
        postInfo.setComments(post.getComments());

        System.out.println(new ObjectMapper().writeValueAsString(postInfo));
    }
}
