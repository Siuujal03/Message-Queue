import java.time.Instant;
public class MessageDelivery
{
    private final Message message;
    private final Instant receivedAt;
    private final int retryCount;

    public MessageDelivery(Message message)
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Message cannot be null");
        }

        this.message = message;
    }

    public MessageDelivery(Message message, Instant receivedAt, int retryCount)
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Message cannot be null");
        }

        if(receivedAt.isAfter(Instant.now()))
        {
            throw new IllegalArgumentException("Incorrect time provided");
        }

        if(retryCount < 0)
        {
            throw new IllegalArgumentException("Retry count cannot be negative");
        }

        this.message = message;
        this.receivedAt = receivedAt;
        this.retryCount = retryCount;
    }

    public Message getMessage()
    {
        return this.message;
    }

    public int getRetryCount()
    {
        return this.retryCount;
    }

    public Instant getReceivedAt()
    {
        return receivedAt;
    }

    @Override
    public String toString() {
        return "MessageDelivery{" +
                "message=" + message +
                '}';
    }
}