package site.achun.lofter.pages;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import site.achun.lofter.pages.extractor.AbstractExtractor;
import site.achun.lofter.pages.extractor.ExtractorFactory;
import site.achun.lofter.utils.UrlHandler;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostPage {

    private Document document;
    private AbstractExtractor extractor;
    public PostPage(Document document){
        this.document = document;
        this.extractor = ExtractorFactory.create(document);
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
        return extractor.getPostLinks();
    }

    public String nextPage(){
        return extractor.nextPage();
    }
}
