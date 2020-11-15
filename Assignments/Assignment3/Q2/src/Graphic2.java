import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Graphic2 extends Application{
    static Scene scene;
    static Scene chatScene;
    static Stage window;
    static boolean sent = false;
    static String sentMessage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        //Label for name
        Text nameLabel = new Text("Name");

        //Text field for name
        TextField nameText = new TextField();
        nameText.setPromptText("Enter your name");

        //Label for date of birth
        Text dobLabel = new Text("Date of birth");

        //date picker to choose date
        DatePicker datePicker = new DatePicker();

        //Label for gender
        Text genderLabel = new Text("Network State");

        //Toggle group of radio buttons
        ToggleGroup groupGender = new ToggleGroup();
        RadioButton maleRadio = new RadioButton("server");
        maleRadio.setToggleGroup(groupGender);
        RadioButton femaleRadio = new RadioButton("client");
        femaleRadio.setToggleGroup(groupGender);

        //Label for location
        Text locationLabel = new Text("location");

        //Choice box for location
        ChoiceBox locationchoiceBox = new ChoiceBox();
        locationchoiceBox.getItems().addAll
                ("Tehran", "Paris", "London", "Rome", "Moscow");

        //Label for register
        Button buttonRegister = new Button("Register");
        buttonRegister.setOnMouseClicked(e ->{
            RadioButton chk = (RadioButton)groupGender.getSelectedToggle();
            if(chk.getText().equals("server"))
                Person2.isServer = true;
            else
                Person2.isServer = false;
            Person2.name = nameText.getText();
            Person2.location = (String)locationchoiceBox.getSelectionModel().getSelectedItem();
            Person2.registration = true;
            initializeChatScene();
            primaryStage.setScene(chatScene);
//            System.out.println(Person1.registration);
        });

        //Creating a Grid Pane
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(500, 500);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameText, 1, 0);

        gridPane.add(dobLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);

        gridPane.add(genderLabel, 0, 2);
        gridPane.add(maleRadio, 1, 2);
        gridPane.add(femaleRadio, 2, 2);

        gridPane.add(locationLabel, 0, 6);
        gridPane.add(locationchoiceBox, 1, 6);

        gridPane.add(buttonRegister, 2, 8);

        //Styling nodes
        buttonRegister.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
        nameLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        dobLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        genderLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        locationLabel.setStyle("-fx-font: normal bold 15px 'serif' ");

        //Setting the back ground color
        gridPane.setStyle("-fx-background-color: BEIGE;");

        //Creating a scene object
        scene = new Scene(gridPane);

        //Setting title to the Stage
        primaryStage.setTitle("Registration Form");

        //Adding scene to the stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            Boolean answer = ConfirmBox.display("Exit" , "Are you sure you want to close?");
            if(answer)
                primaryStage.close();
        });

    }

    private void initializeChatScene() {
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(500, 500);
        gridPane.setStyle("-fx-background-color: BEIGE;");

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);
        TextField textField = new TextField("Enter your response below!");
        gridPane.add(textField , 0 ,2);

        Button buttonRegister = new Button("Send");
        buttonRegister.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
        buttonRegister.setOnMouseClicked(e -> {
            sent = true;
            sentMessage = textField.getText();
            SenderThread senderThread = new SenderThread();
            senderThread.sendData(sentMessage);
            textField.setPromptText("");
        });
        gridPane.add(buttonRegister , 0 , 3);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
        backButton.setOnMouseClicked(e ->{
            window.setScene(scene);
        });
        gridPane.add(backButton , 1 ,3);

        chatScene = new Scene(gridPane);
    }

    public static void updateChatScene(MessageFile message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GridPane gridPane = new GridPane();
                gridPane.setMinSize(500, 500);
                gridPane.setStyle("-fx-background-color: BEIGE;");
//        gridPane.setLayoutX(500);
//        gridPane.setLayoutY(500);

                //Setting the padding
                gridPane.setPadding(new Insets(10, 10, 10, 10));

                //Setting the vertical and horizontal gaps between the columns
                gridPane.setVgap(10);
                gridPane.setHgap(10);

                //Setting the Grid alignment
                gridPane.setAlignment(Pos.CENTER);
                Text nameText = new Text(message.getSender() + " in " + message.getSenderLocation() + " sent a " +
                        (!message.getType().equals("Non") ? message.getType() : "text") );
                nameText.setFont(Font.font("Arial" , 20));
                nameText.setFill(Color.BLACK);
                gridPane.add(nameText , 0 , 0);
                Image image;
                ImageView imageView;
                Media media;
                MediaPlayer mediaPlayer;
                MediaView mediaView;
                File file;
                FileInputStream fileInputStream;
                Button playButton;
                Button stopButton;
                String MEDIA_URL;
                try{
                    switch (message.getType()) {
                        case "jpg":
                            file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\a.jpg");
                            fileInputStream = new FileInputStream(file);
                            image = new Image(fileInputStream);
                            imageView = new ImageView(image);
                            gridPane.add(imageView, 0, 1);
                            break;
                        case "mp3":
                            file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\a.mp3");
                            MEDIA_URL = file.toURI().toString();
                            media = new Media(MEDIA_URL);
                            mediaPlayer = new MediaPlayer(media);
                            playButton = new Button("Play");
                            playButton.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
                            playButton.setOnMouseClicked(e -> {
                                mediaPlayer.play();
                            });
                            stopButton = new Button("Stop");
                            stopButton.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
                            stopButton.setOnMouseClicked(e -> {
                                mediaPlayer.stop();
                            });
                            mediaView = new MediaView(mediaPlayer);
                            gridPane.add(mediaView, 2, 1);
                            gridPane.add(playButton, 1, 1);
                            gridPane.add(stopButton, 0, 1);
                            break;
                        case "gif":
                            file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\a.jpg");
                            fileInputStream = new FileInputStream(file);
                            image = new Image(fileInputStream);
                            imageView = new ImageView(image);
                            gridPane.add(imageView, 0, 1);
                            break;
                        case "wav":
                            file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\a.wav");
                            MEDIA_URL = file.toURI().toString();
                            media = new Media(MEDIA_URL);
                            mediaPlayer = new MediaPlayer(media);
                            playButton = new Button("Play");
                            playButton.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
                            playButton.setOnMouseClicked(e -> {
                                mediaPlayer.play();
                            });
                            stopButton = new Button("Stop");
                            stopButton.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
                            stopButton.setOnMouseClicked(e -> {
                                mediaPlayer.stop();
                            });
                            mediaView = new MediaView(mediaPlayer);
                            gridPane.add(mediaView, 2, 1);
                            gridPane.add(playButton, 1, 1);
                            gridPane.add(stopButton, 0, 1);
                            break;
                        case "png":
                            file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\a.png");
                            fileInputStream = new FileInputStream(file);
                            image = new Image(fileInputStream);
                            imageView = new ImageView(image);
                            gridPane.add(imageView, 0, 1);
                            break;
                        case "Non":
                            Text text = new Text(message.getMessage());
                            text.setFont(Font.font("Arial", 20));
                            text.setFill(Color.RED);
                            gridPane.add(text, 0, 1);
                            int i = 0;

                            if(message.getMessage().contains("_!!_")) {
                                file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\emojis\\thank.png");
                                fileInputStream = new FileInputStream(file);
                                image = new Image(fileInputStream);
                                imageView = new ImageView(image);
                                gridPane.add(imageView, i, 1);
                                i++;
                            }
                            if(message.getMessage().contains(":)")){
                                file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\emojis\\happy.png");
                                fileInputStream = new FileInputStream(file);
                                Image image2 = new Image(fileInputStream);
                                ImageView imageView2 = new ImageView(image2);
                                gridPane.add(imageView2, i, 1);
                                i++;
                            }
                            if(message.getMessage().contains("'!'")){
                                file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\emojis\\poker.png");
                                fileInputStream = new FileInputStream(file);
                                Image image3 = new Image(fileInputStream);
                                ImageView imageView3 = new ImageView(image3);
                                gridPane.add(imageView3, i, 1);
                                i++;
                            }
                            if(message.getMessage().contains(":(")){
                                file = new File("C:\\Users\\bpshop\\Desktop\\Ap Excercies\\ChatRoom\\emojis\\sad.png");
                                fileInputStream = new FileInputStream(file);
                                Image image4 = new Image(fileInputStream);
                                ImageView imageView4 = new ImageView(image4);
                                gridPane.add(imageView4, i, 1);
                                i++;
                            }

                            System.out.println(message.getSender() + ": " + message.getMessage());
                            break;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                TextField textField = new TextField("Enter your response below!");
                gridPane.add(textField , 0 ,2);
                Button buttonRegister = new Button("Send");
                buttonRegister.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
                buttonRegister.setOnMouseClicked(e -> {
                    sent = true;
                    sentMessage = textField.getText();
                    SenderThread senderThread = new SenderThread();
                    senderThread.sendData(sentMessage);
                    textField.setPromptText("");
                });
                gridPane.add(buttonRegister , 0 , 3);

                Button backButton = new Button("Back");
                backButton.setStyle("-fx-background-color: yellowgreen; -fx-textfill: blue;");
                backButton.setOnMouseClicked(e ->{
                    window.setScene(scene);
                });
                gridPane.add(backButton , 1 ,3);

                chatScene = new Scene(gridPane);
                window.setScene(chatScene);
            }
        });

    }
}
