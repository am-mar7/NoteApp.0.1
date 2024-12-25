package NotePackge;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.Serializable;

public class Image implements Serializable {

    private String imagePath;
    public Image (String path) {
        this.imagePath = path;
    }
    public String getPath() {
        return imagePath;
    }
    public static void ShowImage(Image showedimage) {
        File file = new File(showedimage.getPath());
        javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(160);imageView.setFitHeight(160);
        Scene scene = new Scene(new VBox(imageView), 800,600);
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());
        Stage stage = new Stage(); stage.setTitle("Image Viewer");
        stage.setWidth(800);stage.setHeight(600);
        stage.setScene(scene); stage.show();
    }
}