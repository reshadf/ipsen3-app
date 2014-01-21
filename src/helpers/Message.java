package helpers;

/**
 * User: Chris
 * Class: Message Class shower
 */
public class Message {

    private String message;
    private int type;

    /**
     * Class Constructor
     *
     * @param message
     * @param type
     */
    public Message(String message, int type) {
        this.message = message;
        this.type = type;
    }

    /**
     * override of toString method, create new string of message types
     * @return new String
     */
    public String toString() {
        String tmp = "";

        switch(type) {
            case 0 :
                tmp += "[Succes] - "+this.message+"\n";
                break;
            case 1 :
                tmp += "[Fout] - "+this.message+"\n";
                break;
        }

        return tmp;
    }

}
