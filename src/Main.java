import java.util.Optional;

public class Main 
{
    public static void main(String arg[])
    {
        MessageQueue mq = new MessageQueue(5);

        // mq.offer(new Message(""));

        Producer producer = new Producer(mq);

        System.out.println(producer.send(new Message("demo")));

        Consumer consumer = new Consumer(mq);

        Optional<Message> message = consumer.consume();

        Optional<Message> message1 = consumer.consume();

        System.out.println(message.get());

        if(!message1.isPresent())
        {
            System.out.println("Absent");
    
        }
        

        
    }
}
