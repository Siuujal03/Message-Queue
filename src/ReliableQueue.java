import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class ReliableQueue
{
    private final MessageQueue<MessageDelivery> availableQueue;
    private final Map<UUID, MessageDelivery> inFlightMessages;

    public ReliableQueue(int capacity)
    {
        if(capacity <= 0)
        {
            throw new IllegalArgumentException("Capacity cannot be <= 0");
        }
        this.availableQueue = new MessageQueue<>(5);
        this.inFlightMessages = new HashMap<>();
    }

    public boolean publish(Message message)
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Message cannot be null");
        }
        MessageDelivery messageDelivery = new MessageDelivery(message);
        return availableQueue.offer(messageDelivery);
    }

    public Optional<Message> receive()
    {
        MessageDelivery messageDelivery = availableQueue.poll();

        if(messageDelivery == null)
        {
            return Optional.empty();
        }

        Message message = messageDelivery.getMessage();
        UUID id = message.getId();

        inFlightMessages.put(id, messageDelivery);
        
        return Optional.ofNullable(message);

    }

    public boolean ack(UUID id)
    {
        return inFlightMessages.remove(id) != null;
    }




}