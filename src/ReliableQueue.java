import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Optional;
import java.time.Instant;
import java.time.Duration;

public class ReliableQueue
{
    private final MessageQueue<MessageDelivery> availableQueue;
    private final Map<UUID, MessageDelivery> inFlightMessages;
    private final MessageQueue<MessageDelivery> deadLetterQueue;
    private final int maxRetries;
    private final int timeoutSeconds;
    private final ScheduledExecutorService scheduler;
    

    public ReliableQueue(int capacity)
    {
        if(capacity <= 0)
        {
            throw new IllegalArgumentException("Capacity cannot be <= 0");
        }
        this.availableQueue = new MessageQueue<>(5);
        this.deadLetterQueue = new MessageQueue<>(5);
        this.inFlightMessages = new ConcurrentHashMap<>();

        this.maxRetries = 3;
        this.timeoutSeconds = 30;
        this.scheduler = Executors.newScheduledThreadPool(1);
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


        MessageDelivery newMessageDelivery = new MessageDelivery(message, Instant.now(), retryCount);

        // inFlightMessages.put(id, newMessageDelivery);

        inFlightMessages.computeIfAbsent(id, key -> newMessageDelivery);
        
        return Optional.ofNullable(message);

    }

    public boolean ack(UUID id)
    {
        return inFlightMessages.remove(id) != null;
    }


    public void start()
    {

        scheduler.scheduleWithFixedDelay(()->{
            System.out.println("Scheduler Started");
            inFlightMessages.forEach((key, value)->{
                
                Instant receivedAt = value.getReceivedAt();

                Duration duration = Duration.between(receivedAt, Instant.now());

                if(duration.compareTo(Duration.ofSeconds(timeoutSeconds)) >= 0)
                {
                    inFlightMessages.remove(key, value);
                    int retryCount = value.getRetryCount();

                    System.out.println("Retry Count : " + retryCount);

                    if(retryCount == maxRetries)
                    {
                        System.out.println("Added to Dead Letter Queue");
                        boolean isAccepted = deadLetterQueue.offer(value);
                    }
                    else
                    {
                        System.out.println("Added Back to Available Queue");
                        MessageDelivery messageDelivery = new MessageDelivery(value.getMessage(), retryCount + 1);
                        boolean isAccepted = availableQueue.offer(messageDelivery);
                    }
                }
                
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stop()
    {
        System.out.println("closing");
        scheduler.shutdown();
    }


}