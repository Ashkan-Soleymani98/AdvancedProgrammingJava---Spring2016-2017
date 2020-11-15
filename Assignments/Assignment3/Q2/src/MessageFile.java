import java.io.Serializable;

public class MessageFile implements Serializable{
    private String message;
    private String sender;
    private String receiver;
    private String senderLocation;
    private String type = "Non";
    private byte[] bytes;

    public MessageFile(String message, String sender, String receiver , String senderLocation) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.senderLocation = senderLocation;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getSenderLocation() {
        return senderLocation;
    }


}
