package ch04.TimerDemo.v2;

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
    */
   public static void main(String[] args)
   {
      TimerTask task = new TimerTask()
                       {
                          @Override
                          public void run()
                          {
                             System.out.println(System.currentTimeMillis());
                          }
                       };
      Timer timer = new Timer();
      timer.schedule(task, 0, 1000);
   }
}