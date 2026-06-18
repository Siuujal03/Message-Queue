public class Message
{
    private final String payload;

    public Message(String payload)
    {
        if(payload == null || payload.isBlank())
        {
            throw new IllegalArgumentException("Message passed cannot be null or blank");
        }
        this.payload = payload;
    }

    public String getPayload()
    {
        return this.payload;
    }
}