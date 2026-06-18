import java.util.ArrayDeque;
import java.util.Queue;

public class MessageQueue 
{
    private final Queue<Message> messages;

    public MessageQueue()
    {
        this.messages = new ArrayDeque<>();
    }

    public boolean offer(Message message)
    {
        messages.offer(message);
        return true;
    }
}
