package ch01.ThreadDemo.v4;

/**
 * The type Thread demo.
 */
public class ThreadDemo
{

   /**
    * The entry point of application.
    *
    * @param args the input arguments
    */
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         String name = Thread.currentThread().getName();
                         int count = 0;
                         while (!Thread.interrupted())
                            System.out.println(name + ": " + count++);
                      }
                   };
      Thread thdA = new Thread(r);
      Thread thdB = new Thread(r);
      thdA.start();
      thdB.start();
      try
      {
         //用线程自身提供的睡眠功能 代替性能差的“忙循环”
         Thread.sleep(2000);
      }
      catch (InterruptedException ie)
      {
      }
      thdA.interrupt();
      thdB.interrupt();
   }
}