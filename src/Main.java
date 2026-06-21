import java.util.Optional;

public class Main 
{
    public static void main(String arg[])
    {
        // MessageQueue<MessageDelivery> mq = new MessageQueue<>(5);

        ReliableQueue rq = new ReliableQueue(5);

        System.out.println(rq.publish(new Message("hi")));

        Message message = rq.receive().get();

        System.out.println(rq.ack(message.getId()));

        rq.receive().get();
    }
}
