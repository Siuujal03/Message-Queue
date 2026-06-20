public class MessageDelivery
{
    private final Message message;

    public MessageDelivery(Message message)
    {
        if(message == null)
        {
            throw new IllegalArgumentException("Message cannot be null");
        }

        this.message = message;
    }

    public Message getMessage()
    {
        return this.message;
    }

    @Override
    public String toString() {
        return "MessageDelivery{" +
                "message=" + message +
                '}';
    }
}