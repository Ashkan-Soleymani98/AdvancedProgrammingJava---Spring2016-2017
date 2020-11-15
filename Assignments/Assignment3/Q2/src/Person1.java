import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;

public class Person1 {
    public static Server server;
    public static Client client;
    public static String name;
    public static String location;
    public static boolean registration = false;
    public static boolean isServer = false;

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                Application.launch(Graphic1.class , args);
            }
        }.start();

        while (true) {
            System.out.println("waiting for your registration Man");
            if(registration) {

                System.out.println("name : " + name);
                System.out.println("location : " + location);
                System.out.println("isServer : " + isServer);

                if(isServer) {
                    server = new Server(12000, name , location);
                    server.run();
                }else{
                    client = new Client(12000, name , location);
                    client.run();
                }
                break;
            }
        }

    }
}

