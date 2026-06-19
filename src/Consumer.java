import java.util.Optional;
public class Consumer
{
    private final MessageQueue messageQueue;

    public Consumer(MessageQueue messageQueue)
    {
        if(messageQueue == null)
        {
            throw new IllegalArgumentException("Message Queue cannot be null");
        }
        this.messageQueue = messageQueue;
    }

    public Optional<Message> consume()
    {
        Message message = messageQueue.poll();

        return Optional.ofNullable(message);
    }
}