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
import org.jsoup.internal.StringUtil;
import site.achun.lofter.service.LofterUserService;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class HomeView extends VBox {

    private TextField textField;
    private Label tipLabel;
    Label pathLabel;
    private File saveDir;

    public HomeView(){
        setSpacing(10);

        // 输入框
        HBox hBox = new HBox(10);
        textField = new TextField("");
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
        if(chooseDir==null){
            return;
        }
        saveDir = chooseDir;
        pathLabel.setText(saveDir.getPath());
    }

    private void onAction(ActionEvent event) {
        String url = textField.getText();
        if(StringUtil.isBlank(url)){
            tipLabel.setText("请输入地址后重试");
            return;
        }
        if(saveDir == null){
            tipLabel.setText("请选择要保存的文件目录");
            return;
        }

        LofterUserService lofterUserService = new LofterUserService(url,saveDir);
        lofterUserService.setLog(message->{
            Platform.runLater(()->{
                tipLabel.setText(message);
            });
        });
        CompletableFuture.runAsync(()->{
                lofterUserService.startDownloadPosts();
        });
    }
}
