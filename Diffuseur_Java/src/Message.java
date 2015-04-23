public class Message {
    private String message;
    private String id;
    private boolean already_send;

    public Message(String message, String id){
        this.message = message;
        this.id = id;
        this.already_send = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAlready_send() {
        return already_send;
    }

    public void setAlready_send(boolean already_send) {
        this.already_send = already_send;
    }
}
