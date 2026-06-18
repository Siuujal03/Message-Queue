public class Main 
{
    public static void main(String arg[])
    {
        MessageQueue mq = new MessageQueue(5);

        // mq.offer(new Message(""));

        for(int i = 0; i < 6; i++)
        {
            System.out.println(mq.offer(new Message("message")));

        }
    }
}
