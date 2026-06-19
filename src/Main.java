public class Main 
{
    public static void main(String arg[])
    {
        MessageQueue mq = new MessageQueue(5);

        // mq.offer(new Message(""));

        Producer producer = new Producer(mq);

        System.out.println(producer.send(null));
    }
}
