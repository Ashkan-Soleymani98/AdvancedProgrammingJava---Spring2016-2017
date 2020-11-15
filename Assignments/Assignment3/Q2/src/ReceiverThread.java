import javafx.application.Platform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReceiverThread extends Thread{
    private String name;
    private ObjectInputStream objectInputStream;
    private String location;

    public ReceiverThread(String name, ObjectInputStream objectInputStream , String location) {
        this.name = name;
        this.objectInputStream = objectInputStream;
        this.location = location;
    }

    @Override
    public void run() {
        while(true){
            try {
                MessageFile message = (MessageFile) (objectInputStream.readObject());
                FileOutputStream fileOutputStream;
                File file;
                switch (message.getType()){
                    case "jpg":
                        file = new File("a.jpg");
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(message.getBytes());
                        break;
                    case "mp3":
                        file = new File("a.mp3");
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(message.getBytes());
                        break;
                    case "gif":
                        file = new File("a.gif");
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(message.getBytes());
                        break;
                    case "wav":
                        file = new File("a.wav");
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(message.getBytes());
                        break;
                    case "png":
                        file = new File("a.png");
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(message.getBytes());
                        break;
                    case "Non":
                        System.out.println(message.getSender() + ": " + message.getMessage());
                        break;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(name.equals(Person1.name)) {
                            Graphic1.updateChatScene(message);
                        } else if(name.equals(Person2.name)) {
                            Graphic2.updateChatScene(message);
                        }
                    }
                });

            } catch (ClassNotFoundException| IOException e) {
                e.printStackTrace();
            }
        }
    }
}
