import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.time.Instant;

public class ReliableQueue
{
    private final MessageQueue<MessageDelivery> availableQueue;
    private final Map<UUID, MessageDelivery> inFlightMessages;
    private final MessageQueue<MessageDelivery> deadLetterQueue;
    private final int maxRetries;
    

    public ReliableQueue(int capacity)
    {
        if(capacity <= 0)
        {
            throw new IllegalArgumentException("Capacity cannot be <= 0");
        }
        this.availableQueue = new MessageQueue<>(5);
        this.deadLetterQueue = new MessageQueue<>(5);
        this.inFlightMessages = new HashMap<>();

        this.maxRetries = 3;
    }

    public boolean publish(Message message)
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Message cannot be null");
        }
        MessageDelivery messageDelivery = new MessageDelivery(message, 0);
        return availableQueue.offer(messageDelivery);
    }

    public Optional<Message> receive()
    {
        MessageDelivery oldMessageDelivery = availableQueue.poll();

        if(oldMessageDelivery == null)
        {
            return Optional.empty();
        }

        Message message = oldMessageDelivery.getMessage();
        UUID id = message.getId();
        int retryCount = oldMessageDelivery.getRetryCount();

        if(retryCount > maxRetries)
        {
            deadLetterQueue.offer(oldMessageDelivery);
            return Optional.empty();
        }

        MessageDelivery newMessageDelivery = new MessageDelivery(message, Instant.now(), retryCount + 1);

        inFlightMessages.put(id, newMessageDelivery);
        
        return Optional.ofNullable(message);

    }

    public boolean ack(UUID id)
    {
        return inFlightMessages.remove(id) != null;
    }




}