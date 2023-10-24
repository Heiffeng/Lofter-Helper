package site.achun.lofter.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;
import site.achun.lofter.bean.Picture;
import site.achun.lofter.pages.Post;
import site.achun.lofter.pages.PostPage;
import site.achun.lofter.utils.HttpClientUtil;
import site.achun.lofter.utils.UrlHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class HomeView extends VBox {

    private TextField textField;
    private Label tipLabel;
    Label pathLabel;
    private File saveDir;

    public HomeView(){
        setSpacing(10);

        // 输入框
        HBox hBox = new HBox(10);
        textField = new TextField("输入要爬取的地址");
        hBox.getChildren().addAll(new Label("爬取地址："),textField);
        this.getChildren().add(hBox);

        // 保存路径选择
        HBox pathHBox = new HBox(10);
        pathLabel = new Label();
        Button chooseButton = new Button("选择...");
        chooseButton.setOnAction(this::chooseSavePath);
        pathHBox.getChildren().addAll(new Label("保存路径："),pathLabel,chooseButton);
        this.getChildren().add(pathHBox);

        // 确定按钮
        Button button = new Button("确定");
        button.setOnAction(this::onAction);
        this.getChildren().add(button);

        // 提示
        tipLabel = new Label();
        this.getChildren().add(tipLabel);
    }

    private void chooseSavePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File chooseDir = directoryChooser.showDialog(new Stage());
        if(chooseDir!=null && chooseDir.listFiles()!=null&&chooseDir.listFiles().length > 0){
            tipLabel.setText("选择的目录不为空，请重新选择");
            return;
        }
        saveDir = chooseDir;
        pathLabel.setText(saveDir.getPath());
        Path.of(saveDir.getPath(),"img").toFile().mkdirs();
    }


    private void onAction(ActionEvent event) {
        String url = textField.getText();
        //
        CompletableFuture.runAsync(()->{
            try {
                dealPage(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void dealPage(String pageUrl) throws IOException, InterruptedException {
        Document document = HttpClientUtil.getDocument(pageUrl);
        PostPage page = new PostPage(document);
        dealPage(page);
        String nextPageUrl = page.nextPage();
        if(nextPageUrl!=null){
            dealPage(nextPageUrl);
        }else{
            Platform.runLater(()->{
                tipLabel.setText("爬取完毕");
            });
        }
    }

    private void dealPage(PostPage page){
        String tips = String.format("正在爬取第%d页内容", page.getPageIndex());
        Platform.runLater(()->{
            tipLabel.setText(tips);
        });
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

    public void download(String pictureUrl) {
        try {
            // 创建URL对象
            URL url = new URL(pictureUrl);

            // 打开URL连接
            InputStream inputStream = url.openStream();

            // 获取文件名，假设它位于URL的末尾
            String fileName = UrlHandler.create(pictureUrl).getPath();

            // 创建目标文件的路径
            Path targetPath = Path.of(saveDir.getPath(), fileName);

            // 下载文件并保存到本地
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件下载完成：" + targetPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
