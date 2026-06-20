import java.util.UUID;

public class Message
{
    private final UUID id;
    private final String payload;

    public Message(String payload)
    {
        if(payload == null || payload.isBlank())
        {
            throw new IllegalArgumentException("Message passed cannot be null or blank");
        }

        this.id = UUID.randomUUID();
        this.payload = payload;
    }
    
    public String getPayload()
    {
        return this.payload;
    }

    public UUID getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "Message{" +
                "id=" + id +
                ", payload='" + payload + '\'' +
                '}';
    }
}