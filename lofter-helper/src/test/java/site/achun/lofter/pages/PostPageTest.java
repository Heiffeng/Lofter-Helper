package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import site.achun.lofter.utils.HttpClientUtil;

import java.io.IOException;

public class PostPageTest {

    public static void main(String[] args) throws IOException, InterruptedException {
//        https://sboneqiu.lofter.com
        String url = "https://chuancao.lofter.com/";
        Document document = HttpClientUtil.getDocument(url);
        document.setBaseUri(url);

        PostPage page = new PostPage(document);
        System.out.println(page.getPageIndex());
        System.out.println("NextPage:"+page.nextPage());
        page.getPostLinks().stream().forEach(System.out::println);
    }
}
