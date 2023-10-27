package site.achun.lofter.pages.extractor;

import org.jsoup.nodes.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ExtractorFactory {

    private static Map<String,Class> map = new HashMap<>();
    static{
        map.put("119002", Extractor119002.class);
        map.put("4002", Extractor4002.class);
    }

    public static AbstractExtractor create(Document document){
        try {
            String code = getThemeCode(document);
            AbstractExtractor response = null;
            Class<AbstractExtractor> extractorClazz = map.get(code);
            if(extractorClazz == null){
                response = new DefaultExtractor();
            }else{
                response = extractorClazz.getDeclaredConstructor().newInstance();
            }
            response.setDocument(document);
            return response;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getThemeCode(Document document){
        return document.head().select("meta[name='themename']").first().attr("content");
    }
}
