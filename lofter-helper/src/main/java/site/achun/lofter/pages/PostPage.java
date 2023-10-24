package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.achun.lofter.utils.UrlHandler;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostPage {

    private Document document;
    public PostPage(Document document){
        this.document = document;
    }

    public Integer getPageIndex(){
        String url = document.baseUri();
        try {
            Map<String, String> queryMap = UrlHandler.create(url).getQueryMap();
            if(queryMap!=null && queryMap.containsKey("page")){
                return Integer.parseInt(queryMap.get("page"));
            }
        } catch (MalformedURLException e) {}
        return 1;
    }
    public List<String> getPostLinks(){
        Elements eles = document.body().select(".m-post");
        List<MPost> posts = eles.stream().map(ele -> new MPost(ele)).collect(Collectors.toList());
        return posts.stream().map(p->p.getLink()).collect(Collectors.toList());
    }

    public String nextPage(){
//        div.m-pager > a.next
        Element element = document.body().select("div.m-pager > a.next").first();
        if(element!=null){
            return getHost() + element.attr("href");
        }
        return null;
    }

    public String getHost(){
        String url = document.baseUri();
        if(url == null) return null;
        try {
            UrlHandler handler = UrlHandler.create(url);
            return handler.getProtocol()+"://"+handler.getHost();
        } catch (MalformedURLException e) {}
        return null;
    }

    public static class MPost{
        private Element element;
        public MPost(Element element){
            this.element = element;
        }

        public String getLink(){
            Element a = element.select("div.pic > a").first();
            if(a!=null){
                return a.attr("href");
            }else{
                return null;
            }
        }

        public String getContent(){
            Element text = element.select("div.text > p").first();
            return text.html();
        }
    }
}
