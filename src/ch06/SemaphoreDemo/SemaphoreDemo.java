package ch06.SemaphoreDemo;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * The type Semaphore demo.
 *
 *
 * 信号量维护了 组许可证（permit），以约束访问被限制资源的线程数。
 * 当没有可用的许可证时，线程的获取尝试会一直阻塞，直到其他的线程释放一个许可证
 *
 */
public class SemaphoreDemo
{

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args)
   {
      final Pool pool = new Pool();
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         String name = Thread.currentThread().getName();
                         try
                         {
                            while (true)
                            {
                               String item;
                               System.out.println(name + " acquiring " + 
                                                  (item = pool.getItem()));
                               Thread.sleep(200 + 
                                            (int) (Math.random() * 100));
                               System.out.println(name + " putting back " + 
                                                  item);
                               pool.putItem(item);
                            }
                         }
                         catch (InterruptedException ie)
                         {
                            System.out.println(name + "interrupted");
                         }
                      }
                   };
      ExecutorService[] executors = 
         new ExecutorService[Pool.MAX_AVAILABLE + 1];
      for (int i = 0; i < executors.length; i++)
      {
         executors[i] = Executors.newSingleThreadExecutor();
         executors[i].execute(r);
      }
   }
}

/**
 * The type Pool.
 */
final class Pool
{

  /**
   * The constant MAX_AVAILABLE.
   */
  public static final int MAX_AVAILABLE = 10;

   private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

   private final String[] items;

   private final boolean[] used = new boolean[MAX_AVAILABLE];

  /**
   * Instantiates a new Pool.
   */
  Pool()
   {
      items = new String[MAX_AVAILABLE];
      for (int i = 0; i < items.length; i++)
         items[i] = "I" + i;
   }

  /**
   * Gets item.
   *
   * @return the item
   * @throws InterruptedException the interrupted exception
   */
  String getItem() throws InterruptedException
   {
      available.acquire();
      return getNextAvailableItem();
   }

  /**
   * Put item.
   *
   * @param item the item
   */
  void putItem(String item)
   {
      if (markAsUnused(item))
         available.release();
   }

   private synchronized String getNextAvailableItem() 
   {
      for (int i = 0; i < MAX_AVAILABLE; ++i) 
      {
         if (!used[i]) 
         {
            used[i] = true;
            return items[i];
         }
      }
      return null; // not reached
   }

   private synchronized boolean markAsUnused(String item) 
   {
      for (int i = 0; i < MAX_AVAILABLE; ++i) 
      {
         if (item == items[i]) 
         {
            if (used[i]) 
            {
               used[i] = false;
               return true;
            } 
            else
               return false;
         }
      }
      return false;
   }
}