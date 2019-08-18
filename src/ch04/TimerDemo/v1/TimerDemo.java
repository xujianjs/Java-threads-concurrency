package ch04.TimerDemo.v1;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Timer demo.
 */
public class TimerDemo
{

   /**
    * The entry point of application.
    *
    * @param args the input arguments
    * @throws InterruptedException the interrupted exception
    */
   public static void main(String[] args) throws InterruptedException {
      TimerTask task = new TimerTask()
                       {
                          @Override
                          public void run()
                          {
                             System.out.println("alarm going off");
                             System.exit(0);

                          }
                       };
      Timer timer = new Timer();
      timer.schedule(task, 2000); // Execute one-shot timer task after
                                  // 2-second delay.
      Thread.sleep(3000);
      System.out.println("还执行不？");

   }
}