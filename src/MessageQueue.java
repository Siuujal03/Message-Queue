import java.util.ArrayDeque;
import java.util.Queue;

public class MessageQueue 
{
    private final Queue<Message> messages;
    private final int capacity;

    public MessageQueue(int capacity) 
    {
        if(capacity <= 0)
        {
            throw new IllegalArgumentException("Capacity cannot be <= 0");
        }
        this.capacity = capacity;
        this.messages = new ArrayDeque<>();
    }

    private boolean isFull()
    {
        return messages.size() >= capacity;
    }

    private boolean isEmpty()
    {
        return messages.isEmpty();
    }

    private int getSize()
    {
        return messages.size();
    }
    
    public boolean offer(Message message)
    {
        if(!isFull())
        {
            return false;
        }
        messages.offer(message);
        return true;
    }

    public Message poll()
    {
        if(isEmpty())
        {
            return null;
        }

        return messages.poll();
    }
}
