import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    int port;
    Socket client;

    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    private String name;
    private String location;

    public Client(int port , String name , String location) {
        this.name = name;
        this.port = port;
        this.location = location;
    }

    public void run(){
        connect2Server();
        getIOStreams();
        process();
    }

    private void connect2Server() {
        try {
            client = new Socket("localhost", port);
            System.out.println("Client connected to server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getIOStreams() {
        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());
            System.out.println("IO Stream set up!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void process() {
        System.out.println("Starting Process!");
        SenderThread.senderThreadInit(name , objectOutputStream , location);
        ReceiverThread receiverThread = new ReceiverThread(name , objectInputStream , location);

//        senderThread.start();
        receiverThread.start();
    }

}
