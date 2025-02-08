package skibidi.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class DialogBox extends HBox {
    public enum dialogType {
            USER,
            SKIBIDI
    }

    @FXML
    private Label text;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String s, dialogType type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            Image image;
            if (type == dialogType.SKIBIDI) {
                ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
                Collections.reverse(tmp);
                getChildren().setAll(tmp);
                setAlignment(Pos.TOP_LEFT);
                image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/skibidi.jpg")));
            } else {
                image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/camera.jpg")));
            }
            text.setText(s);
            displayPicture.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static DialogBox getDialogBox(String s, dialogType type) {
        return new DialogBox(s, type);
    }
}

