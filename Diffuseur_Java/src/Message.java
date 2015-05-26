public class Message {


    private String id;
    private String message;
    private int num;

    public Message(String id, String message){
        if(id.length() > 8) {
            this.id = id.substring(0, 8);
        }
        else this.id = id;
        while (this.id.length() < 8){
            this.id += "#";
        }
        this.message = message;
        
    }

    public String toString(){
        return this.id + " "+ this.message;
    }





    // *************
    // * GET & SET *
    // *************


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
