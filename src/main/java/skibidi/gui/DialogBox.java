package skibidi.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DialogBox extends HBox {
    public enum dialogType {
            USER,
            SKIBIDI
    }
    private static Logger logger = Logger.getLogger(DialogBox.class.getName());

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
                image = new Image(Objects.requireNonNull(this.getClass()
                        .getResourceAsStream("/images/skibidi.jpg")));
                text.setAlignment(Pos.TOP_LEFT);
                text.setStyle("-fx-background-color: lightgray");
            } else {
                image = new Image(Objects.requireNonNull(this.getClass()
                        .getResourceAsStream("/images/camera.jpg")));
                text.setAlignment(Pos.TOP_RIGHT);
            }
            HBox.setHgrow(text, Priority.ALWAYS);
            text.setText(s);
            displayPicture.setImage(image);

            Circle circle = new Circle(displayPicture.getFitHeight() / 2,
                    displayPicture.getFitWidth() / 2, displayPicture.getFitHeight() / 2);
            displayPicture.setClip(circle);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load dialog box", e);
        }

    }

    public static DialogBox getDialogBox(String s, dialogType type) {
        return new DialogBox(s, type);
    }
}

