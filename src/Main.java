public class Main 
{
    public static void main(String arg[])
    {
        MessageQueue mq = new MessageQueue();

        mq.offer(new Message("a"));
    }
}
