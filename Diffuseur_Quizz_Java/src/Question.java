public class Question extends Message{

    private String[] reponses;
    private String[] hints;

    public Question(String id, String message, String reponse, String hint) {
        super(id, message);
        this.reponses = reponse.split(":");
        this.hints = hint.split(":");
    }

    public String toString(){
        return super.toString();
    }

    public boolean win(String s){
        for (String rep : this.getReponses()) {
            if (s.equals(rep)){
                return true;
            }
        }
        return false;
    }


    // *************
    // * GET & SET *
    // *************


    public String[] getReponses() {
        return reponses;
    }
    public String[] getHints() {
        return hints;
    }
}
