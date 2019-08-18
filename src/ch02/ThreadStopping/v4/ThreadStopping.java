package ch02.ThreadStopping.v4;

/**
 * The type Thread stopping.
 */
public class ThreadStopping
{

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args)
   {
      class StoppableThread extends Thread
      {
         private volatile boolean stopped; // defaults to false

         @Override
         public void run()
         {
            while(!stopped)
              System.out.println("running");
         }

         void stopThread()
         {
            stopped = true;
         }
      }
      StoppableThread thd = new StoppableThread();
      thd.start();
      try
      {
         Thread.sleep(1000); // sleep for 1 second
      }
      catch (InterruptedException ie)
      {
      }
      thd.stopThread();
   }
}