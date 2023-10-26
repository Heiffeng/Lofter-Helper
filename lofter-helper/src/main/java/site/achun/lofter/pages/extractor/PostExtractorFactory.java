package site.achun.lofter.pages.extractor;

import org.jsoup.nodes.Document;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PostExtractorFactory {

    private static Map<String,Class> map = new HashMap<>();
    static{
        map.put("119002",PostExtractor119002.class);
    }

    public static AbstractPostExtractor create(String code, Document document){
        try {
            AbstractPostExtractor response = null;
            Class<AbstractPostExtractor> extractorClazz = map.get(code);
            if(extractorClazz == null){
                response = new DefaultPostExtractor();
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
}
