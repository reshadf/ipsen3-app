package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Chris
 * Class: Message Queue Class to queue up messages to display
 */
public class MessageQueue {
    private List<Message> messages = new ArrayList<Message>();
    private static MessageQueue instance = null;

    /**
     * Class Constructor
     */
    protected MessageQueue() {

    }

    /**
     * Singleton protection, only one instance of this class needed
     * @return class instance
     */
    public static MessageQueue getInstance() {
        if(instance == null) {
            instance = new MessageQueue();
        }
        return instance;
    }

    /**
     * Add a message to the queue
     *
     * @param message
     * @param type
     */
    public void addMessage(String message, int type) {
        messages.add(new Message(message, type));
    }

    /**
     * Get all the messages
     *
     * @return List of messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Clear the queue
     */
    public void clearQueue() {
        messages.clear();
    }
}
