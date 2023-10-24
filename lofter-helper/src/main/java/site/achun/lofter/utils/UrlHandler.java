package site.achun.lofter.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UrlHandler {

    private URL url ;

    private UrlHandler(String spec) throws MalformedURLException {
        this.url = new URL(spec);
    }

    public static UrlHandler create(String spec) throws MalformedURLException {
        return new UrlHandler(spec);
    }

    public String getProtocol() {
        return url.getProtocol();
    }
    public String getHost() {
        return url.getHost();
    }
    public int getPort() {
        return url.getPort();
    }
    public String getQuery() {
        return url.getQuery();
    }
    public String getPath() {
        return url.getPath();
    }
    public String getFile() {
        return url.getFile();
    }
    public String getRef() {
        return url.getRef();
    }
    public Map<String,String> getQueryMap(){
        String query = getQuery();
        if(StringUtils.isEmpty(query)) return null;

        Map<String,String> respMap = new HashMap<>();
        String[] kvs = query.split("&");
        for(String kv : kvs) {
            if(kv.contains("=")) {
                String[] param = kv.split("=");
                respMap.put(param[0], param[1]);
            }else {
                respMap.put(kv, "");
            }
        }
        return respMap ;
    }

    /**
     * 删除某个查询字段
     * @param name
     * @return 删除成功，返回true，
     *  查询部分为空，不包含此字段导致的删除失败，返回false
     * @throws MalformedURLException
     */
    public boolean removeQueryByName(String name) throws MalformedURLException {
        Map<String,String> queryMap = getQueryMap();
        if(queryMap==null) return false;
        if(queryMap.containsKey(name)) {
            queryMap.remove(name);
            setQuery(queryMap);
            return true;
        }else {
            return false;
        }
    }
    public void addQuery(String key,String value) throws MalformedURLException {
        Map<String,String> queryMap = getQueryMap();
        queryMap.put(key,value);
        setQuery(queryMap);
    }
    public void setQuery(Map<String,String> queryMap) throws MalformedURLException {
        String queryString = toQueryString(queryMap);
        String file = url.getPath();
        if(queryString!=null) {
            file = url.getPath()+"?"+queryString;
        }
        this.url = new URL(url.getProtocol(),url.getHost(),url.getPort(),file);
    }

    public void removeRef() throws MalformedURLException {
        this.url = new URL(url.getProtocol(),url.getHost(),url.getPort(),url.getFile());
    }

    @Override
    public String toString() {
        // pre-compute length of StringBuffer
        int len = url.getProtocol().length() + 1;
        if (url.getAuthority() != null && url.getAuthority().length() > 0)
            len += 2 + url.getAuthority().length();
        if (url.getPath() != null) {
            len += url.getPath().length();
        }
        if (url.getQuery() != null) {
            len += 1 + url.getQuery().length();
        }
        if (url.getRef() != null)
            len += 1 + url.getRef().length();

        StringBuffer result = new StringBuffer(len);
        result.append(url.getProtocol());
        result.append(":");
        if (url.getAuthority() != null && url.getAuthority().length() > 0) {
            result.append("//");
            result.append(url.getAuthority());
        }
        if (StringUtils.isNotBlank(url.getPath()) && !url.getPath().equals("/")) {
            result.append(url.getPath());
        }
        if (StringUtils.isNotBlank(url.getQuery())) {
            result.append('?');
            result.append(url.getQuery());
        }
        if (StringUtils.isNotBlank(url.getRef())) {
            result.append("#");
            result.append(url.getRef());
        }
        return result.toString();
    }
    private String toQueryString(Map<String,String> queryMap) {
        if(queryMap==null||queryMap.size()==0) return null;
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String, String> m : queryMap.entrySet()) {
            sb.append(m.getKey()+"="+m.getValue()+"&");
        }
        return sb.toString().substring(0, sb.length()-1);

    }

    private static class StringUtils {
        public static boolean isEmpty(String str) {
            return (str == null||str.replace(" ","").equals(""));
        }

        public static boolean isNotBlank(String str) {
            return str!=null&&!str.replace(" ","").equals("");
        }
    }
}
