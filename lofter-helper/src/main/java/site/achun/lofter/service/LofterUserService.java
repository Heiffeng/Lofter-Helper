package site.achun.lofter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.bean.PostInfo;
import site.achun.lofter.pages.Post;
import site.achun.lofter.pages.PostPage;
import site.achun.lofter.pages.extractor.ExtractorFactory;
import site.achun.lofter.utils.HttpClientUtil;
import site.achun.lofter.utils.UrlHandler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LofterUserService {
    private String url;
    private String userCode;
    private File baseDir;
    private File imageFileDir;
    private File postFileDir;

    private List<PostInfo> posts;

    private Consumer<String> log;

    public LofterUserService(String url,File baseDir){
        this.url = url;
        this.userCode = getUserCode();
        this.posts = new ArrayList<>();
        this.baseDir = baseDir;
        this.imageFileDir = Path.of(baseDir.getPath(),this.userCode,"images").toFile();
        this.postFileDir = Path.of(baseDir.getPath(),this.userCode,"posts").toFile();
    }

    public void startDownloadPosts(){
        // 创建目录
        if(!imageFileDir.exists()) imageFileDir.mkdirs();
        if(!postFileDir.exists()) postFileDir.mkdirs();
        //
        try {
            dealPostPageUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private void dealPostPageUrl(String pageUrl) throws Exception{
        Document document = HttpClientUtil.getDocument(pageUrl);
        PostPage page = new PostPage(document);
        dealPostPage(page);
        String nextPageUrl = page.nextPage();
        if(nextPageUrl!=null){
            dealPostPageUrl(nextPageUrl);
        }else{
            log("爬取完毕");
        }
    }
    private void dealPostPage(PostPage page){
        String tips = String.format("正在爬取第%d页内容", page.getPageIndex());
        log(tips);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        List<Post> postList = page.getPostLinks().stream()
                .filter(link->link!=null)
                .map(link -> {
                    try {
                        Document document = HttpClientUtil.getDocument(link);
                        return new Post(document);
                    } catch (IOException e) {
                    } catch (InterruptedException e) {
                    }
                    return null;
                })
                .filter(p->p!=null)
                .collect(Collectors.toList());

        for (Post post : postList) {
            dealPost(post);
        }
    }
    private void dealPost(Post post){
        // 获取是否存在post
        String postCode = post.getPostCode();
        File postFileInfo = Path.of(postFileDir.getPath(), postCode + ".json").toFile();
        if(postFileInfo.exists()){
            // postJson存在，说明已经爬取过了。
            return;
        }
        PostInfo postInfo = new PostInfo();
        postInfo.setContent(post.getContent());
        postInfo.setTags(post.getTags());
        postInfo.setPictures(post.getPictures());
        postInfo.setPostTime(post.getPostTime());
        postInfo.setHot(post.getHot());
        postInfo.setComments(post.getComments());
        postInfo.setThemeName(post.getExtractor().getThemeCode());

        List<Picture> pics = post.getPictures();
        if(pics!=null) {
            for (Picture pic : pics) {
                download(pic.url());
            }
        }
        writePostInfo(postFileInfo,postInfo);
    }

    private void writePostInfo(File postFileInfo,PostInfo postInfo){
        try{
            postFileInfo.createNewFile();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(postFileInfo)){
            String json = new ObjectMapper().writeValueAsString(postInfo);
            System.out.println(json);
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void download(String pictureUrl) {
        try {
            // 创建URL对象
            URL url = new URL(pictureUrl);
            // 打开URL连接
            InputStream inputStream = url.openStream();
            // 获取文件名，假设它位于URL的末尾
            String path = UrlHandler.create(pictureUrl).getPath();
            String fileName = path.substring(path.lastIndexOf('/') + 1);
            // 创建目标文件的路径
            Path targetPath = imageFileDir.toPath().resolve(fileName);
            // 下载文件并保存到本地
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件下载完成：" + targetPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserCode(){
        try {
            return UrlHandler.create(url).getHost().replace(".lofter.com","");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLog(Consumer<String> log) {
        this.log = log;
    }
    private void log(String message){
        log.accept(message);
    }
}
