import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenderThread{
    static String sender;
    static String receiver;
    static String senderLocation;
    static ObjectOutputStream objectOutputStream;

    public static void senderThreadInit(String name, ObjectOutputStream objectOutputStream, String senderLocation) {
        sender = name;
        SenderThread.objectOutputStream = objectOutputStream;
        SenderThread.senderLocation = senderLocation;
    }

//    public void run() {
//        Scanner scanner = new Scanner(System.in);
//        while(true){
//            sendData(scanner.next());
//        }
//    }

    public void sendData(String input) {
        String pattern;
        Pattern compiledPattern;
        Matcher matcher;
//        boolean hasFormat = false;

        MessageFile message = new MessageFile(input , sender , receiver , senderLocation);

        System.out.println(sender + " : " + input );
        pattern = "\\S*.jpg\\s*";
        compiledPattern = Pattern.compile(pattern);
        matcher = compiledPattern.matcher(input);

        if(matcher.matches()){
            sendFile(message , "jpg" , input);
//            hasFormat = true;
        }

        pattern = "\\S*.mp3\\s*";
        compiledPattern = Pattern.compile(pattern);
        matcher = compiledPattern.matcher(input);

        if(matcher.matches()){
            sendFile(message , "mp3" , input);
//            hasFormat = true;
        }

        pattern = "\\S*.gif\\s*";
        compiledPattern = Pattern.compile(pattern);
        matcher = compiledPattern.matcher(input);

        if(matcher.matches()){
            sendFile(message , "gif" , input);
//            hasFormat = true;
        }

        pattern = "\\S*.wav\\s*";
        compiledPattern = Pattern.compile(pattern);
        matcher = compiledPattern.matcher(input);

        if(matcher.matches()){
            sendFile(message , "wav" , input);
//            hasFormat = true;
        }

        pattern = "\\S*.png\\s*";
        compiledPattern = Pattern.compile(pattern);
        matcher = compiledPattern.matcher(input);

        if(matcher.matches()){
            sendFile(message , "png" , input);
//            hasFormat = true;
        }

        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendFile(MessageFile message, String format , String address){
        message.setType(format);
        try {
            File file = new File(address);
            FileInputStream fileInputStream = new FileInputStream(file);
            int bCode;
            List<Byte> byteList = new ArrayList<>();
            while(-1 != (bCode = fileInputStream.read())){
                byteList.add((byte) bCode);
//                System.out.println((byte)bCode);
            }
            byte[] bytes = new byte[byteList.size()];
            for(int i = 0 ; i < byteList.size() ; i++){
                bytes[i] = byteList.get(i);
            }
            message.setBytes(bytes);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        } catch (IOException e) {
            System.out.println("IOException!");
            e.printStackTrace();
        }
    }

}
