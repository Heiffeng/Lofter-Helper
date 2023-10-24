package site.achun.lofter.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jsoup.nodes.Document;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.pages.Post;
import site.achun.lofter.pages.PostPage;
import site.achun.lofter.utils.HttpClientUtil;
import site.achun.lofter.utils.UrlHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class HomeView extends VBox {

    private TextField textField;
    private Label tipLabel;

    public HomeView(){
        setSpacing(10);
        textField = new TextField("输入要爬取的地址");
        Button button = new Button("确定");

        button.setOnAction(this::onAction);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(textField,button);

        this.getChildren().add(hBox);

        tipLabel = new Label();
        this.getChildren().add(tipLabel);
    }

    private void onAction(ActionEvent event) {
        String url = textField.getText();
        //
        try {
           dealPage(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void dealPage(String pageUrl) throws IOException, InterruptedException {
        Document document = HttpClientUtil.getDocument(pageUrl);
        PostPage page = new PostPage(document);
        dealPage(page);
        String nextPageUrl = page.nextPage();
        if(nextPageUrl!=null){
            dealPage(nextPageUrl);
        }else{
            tipLabel.setText("爬取完毕");
        }
    }

    private void dealPage(PostPage page){
        String tips = String.format("正在爬取第%d页内容", page.getPageIndex());
        tipLabel.setText(tips);
        System.out.println(tips);
        try {
            Thread.sleep(2000);
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
            List<Picture> pics = post.getPictures();
            if(pics!=null) {
                for (Picture pic : pics) {
                    download(pic.url());
                }
            }
        }
    }

    public static void download(String pictureUrl) {
        String targetDirectory = "D:\\downloads"; // 替换为目标下载目录
        try {
            // 创建URL对象
            URL url = new URL(pictureUrl);

            // 打开URL连接
            InputStream inputStream = url.openStream();

            // 获取文件名，假设它位于URL的末尾
            String fileName = UrlHandler.create(pictureUrl).getPath();

            // 创建目标文件的路径
            Path targetPath = Path.of(targetDirectory, fileName);

            // 下载文件并保存到本地
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件下载完成：" + targetPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
