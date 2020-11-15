import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class ConfirmBox {
    static boolean anwser = false;
    public static boolean display(String title , String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(50);
        window.setMinWidth(200);
        window.setTitle(title);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            anwser = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            anwser = false;
            window.close();
        });

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(yesButton , noButton);
        hBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label , hBox);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();

        return anwser;
    }
}
