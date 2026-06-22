import java.util.Optional;

public class Main 
{
    public static void main(String arg[]) throws Exception
    {
        // MessageQueue<MessageDelivery> mq = new MessageQueue<>(5);
        ReliableQueue rq = new ReliableQueue(5);

        Message message1 = new Message("Task 1");
        Message message2 = new Message("Task 2");
        Message message3 = new Message("Task 3");
        Message message4 = new Message("Task 4");

        rq.publish(message1);
        rq.receive();

        // rq.ack(rq.receive().get().getId());

        rq.start();

        for(int i = 0; i < 4; i++)
        {
            Thread.sleep(40000);
            // rq.publish(message1);
            rq.receive();
        }

        rq.stop();
    }
}
