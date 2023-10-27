package site.achun.lofter.pages;

import site.achun.lofter.utils.UrlHandler;

import java.net.MalformedURLException;

public class UrlHandlerTest {
    public static void main(String[] args) throws MalformedURLException {
        String url = "https://imglf6.lf127.net/img/f6e27ae101a06c5a/SGQwQ1lMQlB1WlV2dmNlRmhwb0dLay9WMCsxa1RicG9aUFA0UmVCS1hKVT0.jpg?imageView&thumbnail=1680x0&quality=96&stripmeta=0&type=jpg";
        // 找到最后一个斜杠的位置
        String path = UrlHandler.create(url).getPath();
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        System.out.println("文件名: " + fileName);
    }
}
