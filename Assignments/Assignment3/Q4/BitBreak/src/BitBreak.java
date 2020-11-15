import com.sun.javafx.tk.Toolkit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BitBreak extends Application{
    private Pane root;
    private double width = 800;
    private double height = 800;
    private int bricksInRow = 8;
    private int bricksInColumn = 12;
    private int score;
    private Border border;
    private Scene scene;
    private BorderWidths borderWidths;
    private File file;
    private Image image;
    private FileInputStream fileInputStream;
    private ImagePattern imagePattern;
    private Ball ball;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private Bat bat;
    private double maxReflectionAngle = 22.5;

    private class Ball{
        private Circle view;
        private double velocityX;
        private double velocityY;
        private double centerX;
        private double centerY;
        private double radius;

        public Ball(Circle view, double velocityX, double velocityY) {
            this.view = view;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            centerX = view.getCenterX();
            centerY = view.getCenterY();
            radius = view.getRadius();
        }

        public double getCenterX() {
            return centerX;
        }

        public double getCenterY() {
            return centerY;
        }

        public void setVelocityX(double velocityX) {
            this.velocityX = velocityX;
        }

        public void setVelocityY(double velocityY) {
            this.velocityY = velocityY;
        }

        public void move() {
            view.setCenterX(view.getCenterX() + velocityX);
            view.setCenterY(view.getCenterY() + velocityY);

            centerX = view.getCenterX();
            centerY = view.getCenterY();

            Bounds bounds = root.getBoundsInLocal();

            //If the ball reaches the left or right border make the step negative
            if (view.getCenterX() <= (bounds.getMinX() + view.getRadius() + 1.5 * borderWidths.getLeft()) ||
                    view.getCenterX() >= (bounds.getMaxX() - view.getRadius() - 1.5 * borderWidths.getRight())) {
                velocityX = -velocityX;
            }

            //If the ball reaches the bottom or top border make the step negative
            if ((view.getCenterY() >= (bounds.getMaxX() - view.getRadius()) - 1.5 * borderWidths.getTop()) ||
                    (view.getCenterY() <= (bounds.getMinY() + view.getRadius() + 1.5 * borderWidths.getBottom()))) {
                velocityY = -velocityY;
            }

        }

        public double getRadius() {
            return radius;
        }

        public void reflect(Bat bat , double maxAngle) {
            if (velocityX < 0) {
                double angle = Math.toRadians(Math.abs(bat.getCenterX() - centerX) / maxAngle * bat.getWidth() / 2);
                double rx = (velocityX * Math.cos(2 * angle)) - (velocityY * Math.sin(2 * angle));
                double ry = (velocityX * Math.sin(2 * angle)) + (velocityY * Math.cos(2 * angle));
                velocityX = rx;
                velocityY = ry;
            }else if(velocityX < 0){
                double angle = -1 * (Math.toRadians(Math.abs(bat.getCenterX() - centerX) / maxAngle * bat.getWidth() / 2));
                double rx = (velocityX * Math.cos(2 * angle)) - (velocityY * Math.sin(2 * angle));
                double ry = (velocityX * Math.sin(2 * angle)) + (velocityY * Math.cos(2 * angle));
                velocityX = rx;
                velocityY = ry;
            }else {
                velocityY *= -1;
            }
        }
    }

    private class Brick{
        private Rectangle view;
        private int quality;
        private int hitNum;
        private double centerX;
        private double centerY;
        private double width;
        private double height;

        public Brick(double x , double y , double height , double width, int quality) {
            this.quality = quality;
            centerX = x + width / 2;
            centerY = y + height / 2;
            this.height = height;
            this.width = width;
            try {
                switch (quality){
                    case 1:
                        file = new File("C:\\Users\\bpshop\\Desktop\\Game\\pics\\brick1.jpg");
                        fileInputStream = new FileInputStream(file);
                        image = new Image(fileInputStream);
                        imagePattern = new ImagePattern(image , 0 , 0 , width , height, false);
                        break;
                    case 2:
                        file = new File("C:\\Users\\bpshop\\Desktop\\Game\\pics\\brick2.jpg");
                        fileInputStream = new FileInputStream(file);
                        image = new Image(fileInputStream);
                        imagePattern = new ImagePattern(image , 0 , 0 , width , height, false);
                        break;
                    case 3:
                        file = new File("C:\\Users\\bpshop\\Desktop\\Game\\pics\\brick3.jpg");
                        fileInputStream = new FileInputStream(file);
                        image = new Image(fileInputStream);
                        imagePattern = new ImagePattern(image , 0 , 0 , width , height, false);
                        break;
                    case 4:
                        file = new File("C:\\Users\\bpshop\\Desktop\\Game\\pics\\brick4.jpg");
                        fileInputStream = new FileInputStream(file);
                        image = new Image(fileInputStream);
                        imagePattern = new ImagePattern(image , 0 , 0 , width , height, false);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            view = new Rectangle(x , y , width , height);
            view.setFill(imagePattern);
        }

        public double getCenterX() {
            return centerX;
        }

        public double getCenterY() {
            return centerY;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }

    private class Bat{
        private Rectangle view;
        private double centerX;
        private double centerY;
        private double width;
        private double height;
        private double x;
        private double y;
        private double moveX;
        private double moveY;

        public Bat(double width, double height, double x, double y , double moveX , double moveY) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            this.moveX = moveX;
            this.moveY = moveY;
            centerX = x + width / 2;
            centerY = y + height / 2;
            try {
                file = new File("C:\\Users\\bpshop\\Desktop\\Game\\pics\\Bat.jpg");
                fileInputStream = new FileInputStream(file);
                image = new Image(fileInputStream);
                fileInputStream.close();
                imagePattern = new ImagePattern(image, 0, 0, width, height, false);
            }catch (Exception e){
                e.printStackTrace();
            }
            view = new Rectangle(x , y , width , height);
            view.setFill(Color.BEIGE);
        }


        public Rectangle getView() {
            return view;
        }

        public double getCenterX() {
            return centerX;
        }

        public double getCenterY() {
            return centerY;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void resize(int width){
            view.setWidth(width);
        }

        public void moveRight(){
            Bounds bounds = root.getBoundsInLocal();
            if(x + moveX + width<= bounds.getMaxX() - 1.5 * borderWidths.getRight()) {
                x += moveX;
                centerX += moveX;
                view.setX(x);
            }
            else {

            }
        }

        public void moveLeft(){
            Bounds bounds = root.getBoundsInLocal();
            if(x - moveX >= bounds.getMinX() + 1.5 * borderWidths.getLeft()) {
                x -= moveX;
                centerX -= moveX;
                view.setX(x);
            }
            else {

            }
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new Pane();
        root.setPrefSize(width , height);

        file = new File("C:\\Users\\bpshop\\Desktop\\Game\\pics\\BorderImage.jpg");
        fileInputStream = new FileInputStream(file);
        image = new Image(fileInputStream);
        fileInputStream.close();
        imagePattern = new ImagePattern(image);
        border = new Border(new BorderStroke(imagePattern ,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, borderWidths = new BorderWidths(8)));
        root.setBorder(border);


        ball = new Ball(new Circle(width / 2 , height* 13 / 14 , 5 , Color.YELLOW) , 7 , 7);
        root.getChildren().add(ball.view);

        for(int j = 1 ; j <= bricksInRow / 2 ; j++){
            for(int i = 1; i <= bricksInColumn; i++) {
                double brickHeight = (height / 2) / (bricksInRow + 2);
                double brickWidth = width / (bricksInColumn + 2);
                int random;
                Brick brick = new Brick(i * brickWidth, j * brickHeight, brickHeight
                        , brickWidth, random = (new Random().nextInt(4) + 1));
                bricks.add(brick);
                Brick reflectedBrick = new Brick(i * brickWidth,  height / 2 - (j - 1) * brickHeight, brickHeight
                        , brickWidth, random);
                bricks.add(reflectedBrick);
                root.getChildren().addAll(brick.view , reflectedBrick.view);
            }
        }

        int batWidth = 100;
        int batHeight = 15;
        bat = new Bat(batWidth , batHeight , (width - batWidth) / 2 , height * 13 / 14 , 20 ,20);
        root.getChildren().add(bat.view);

//        Brick brick = new Brick(0, 0 , 20 , 10 , 1);
//        root.getChildren().addAll(brick.view);

        scene = new Scene(root , width , height);
        file = new File("C:\\Users\\bpshop\\Desktop\\Game\\pics\\backGround.jpg");
        fileInputStream = new FileInputStream(file);
        Image image = new Image(fileInputStream);
        fileInputStream.close();
        imagePattern = new ImagePattern(image , 0 , 0 , width , height, false);
        scene.setFill(imagePattern);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (event.getCode().equals(KeyCode.RIGHT)) {
                            bat.moveRight();
                        }else if(event.getCode().equals(KeyCode.LEFT)){
                            bat.moveLeft();
                        }
                    }
                });
            }
        });

        primaryStage.setScene(scene);

        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        updateBricks();
//                        updateBat();
                    }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    private void updateBricks(){
        ball.move();
        new Thread(){
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(Math.abs(bat.getCenterX() - ball.getCenterX()) <= (bat.getWidth() / 2 + ball.getRadius())){
                            if(Math.abs(bat.getCenterY() - ball.getCenterY()) <= (bat.getHeight() / 2 + ball.getRadius())){
                                ball.reflect(bat , maxReflectionAngle);
                            }
                        }
                        for(int i = 0 ; i < bricks.size() ; i++){
                            if( Math.abs(bricks.get(i).getCenterX() - ball.getCenterX()) <= (bricks.get(i).getWidth() / 2 + ball.getRadius())){
                                if( Math.abs(bricks.get(i).getCenterY() - ball.getCenterY()) <= (bricks.get(i).getHeight() / 2 + ball.getRadius()) ){
                                    bricks.get(i).hitNum++;
                                    if (bricks.get(i).hitNum < bricks.get(i).quality) {
                                        bricks.get(i).view.setEffect(new Bloom(bricks.get(i).hitNum / bricks.get(i).quality / 2));
                                        if( Math.abs(bricks.get(i).getCenterX() - ball.getCenterX()) <= bricks.get(i).getWidth() / 2){
                                            ball.velocityY *= -1;
                                        }else if( Math.abs(bricks.get(i).getCenterY() - ball.getCenterY()) <= bricks.get(i).getHeight() / 2){
                                            ball.velocityX += -1;
                                        }
                                        System.out.println("hitnum increased");
                                    } else if (bricks.get(i).hitNum >= bricks.get(i).quality) {
                                        score++;
                                        root.getChildren().remove(bricks.get(i).view);
                                        bricks.remove(i);
                                        if (bricks.size() == 0) {
                                            finish();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }.start();


    }

//    private void updateBat(){
//        new Thread(){
//            @Override
//            public void run() {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//            }
//        }.start();
//    }

    private void finish(){
        System.out.println("finish");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
