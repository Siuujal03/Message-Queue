public class Producer
{
    private final MessageQueue messageQueue;

    public Producer(MessageQueue messageQueue)
    {
        if(messageQueue == null)
        {
            throw new IllegalArgumentException("Message Queue cannot be null");
        }

        this.messageQueue = messageQueue;
    }

    public boolean send(Message message)
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Message cannot be null");
        }

        return messageQueue.offer(message);

    }
}