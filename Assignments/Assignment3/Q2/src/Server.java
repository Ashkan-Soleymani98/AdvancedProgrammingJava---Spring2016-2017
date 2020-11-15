import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    int port;
    ServerSocket serverSocket;
    Socket socket;
    static boolean setUp = false;

    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    private String name;
    private String location;

    public Server(int port , String name ,String location) {
        this.name = name;
        this.port = port;
        this.location = location;
    }

    public void run() {
        setUp = true;
        setUpServer();
//        waitForClient();
        getIOStreams();
        process();
    }

    public void setUpServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            this.serverSocket = serverSocket;
            System.out.println("Server Set Up!");
            socket = serverSocket.accept();
            System.out.println("Server Connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void waitForClient() {
//        try {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void getIOStreams() {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("IO Stream Set!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(){
        System.out.println("Starting Process!");
        SenderThread.senderThreadInit(name , objectOutputStream , location);
        ReceiverThread receiverThread = new ReceiverThread(name , objectInputStream , location);

//        senderThread.start();
        receiverThread.start();
    }
}
