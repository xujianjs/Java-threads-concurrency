package ch06.ExchangerDemo;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Exchanger;

/**
 * The type Exchanger demo.
 *
 * 默认主线程通过静态属性初始化创建了 1个交换器以及1对缓冲区。之后，它初始化本地的类 Emptyingloop Fillingloop ，并将这
 * runnable 传递到新的线程实例当中，这些实例随后会被启动 。（ 也可以使用
 * executors ）每个 runnables run （） 方法进入 个无限循环，反复地往它的缓冲中添加或者删除。
 * 当缓冲区满了或者空了，这个交换器会用来交换这些缓冲，持续地添加或清空。
 *
 * exchange(V x)用来和传入对象交换数据  把结果返回
 */
public class ExchangerDemo
{

   /**
    * The constant exchanger.
    */
   final static Exchanger<DataBuffer> exchanger =
      new Exchanger<DataBuffer>();

   /**
    * The constant initialEmptyBuffer.
    */
   final static DataBuffer initialEmptyBuffer = new DataBuffer();
   /**
    * The constant initialFullBuffer.
    */
   final static DataBuffer initialFullBuffer = new DataBuffer("I");

   /**
    * The entry point of application.
    *
    * @param args the input arguments
    */
   public static void main(String[] args)
   {
      class FillingLoop implements Runnable 
      {
         int count = 0;

         @Override
         public void run() 
         {
            DataBuffer currentBuffer = initialEmptyBuffer;
            try 
            {
               while (true) 
               {
                  addToBuffer(currentBuffer);
                  //缓冲区满了  通过exchanger和initialEmptyBuffer进行数据交换
                  if (currentBuffer.isFull())
                  {
                     System.out.println("filling thread wants to exchange");
                     currentBuffer = exchanger.exchange(currentBuffer);
                     System.out.println("filling thread receives exchange");
                  }
               }
            } 
            catch (InterruptedException ie) 
            { 
               System.out.println("filling thread interrupted");
            }
         }

         void addToBuffer(DataBuffer buffer)
         {
            String item = "NI" + count++;
            System.out.println("Adding: " + item);
            buffer.add(item);
         }
      }

      class EmptyingLoop implements Runnable 
      {
         @Override
         public void run() 
         {
            DataBuffer currentBuffer = initialFullBuffer;
            try 
            {
               while (true) 
               {
                  takeFromBuffer(currentBuffer);
                  //缓冲区空了  通过exchanger和initialFullBuffer进行数据交换
                  if (currentBuffer.isEmpty())
                  {
                     System.out.println("emptying thread wants to " +
                                        "exchange");
                     currentBuffer = exchanger.exchange(currentBuffer);
                     System.out.println("emptying thread receives " +
                                        "exchange");
                  }
               }
            } 
            catch (InterruptedException ie) 
            { 
               System.out.println("emptying thread interrupted");
            }
         }

         void takeFromBuffer(DataBuffer buffer)
         {
            System.out.println("taking: " + buffer.remove());
         }
      }
      new Thread(new EmptyingLoop()).start();
      new Thread(new FillingLoop()).start();
   }
}

/**
 * The type Data buffer.
 */
class DataBuffer
{
   private final static int MAXITEMS = 10;

   private final List<String> items = new ArrayList<>();

   /**
    * Instantiates a new Data buffer.
    */
   DataBuffer()
   {
   }

   /**
    * Instantiates a new Data buffer.
    *
    * @param prefix the prefix
    */
   DataBuffer(String prefix)
   {
      for (int i = 0; i < MAXITEMS; i++)
      {
         String item = prefix + i;
         System.out.printf("Adding %s%n", item);
         items.add(item);
      }
   }

   /**
    * Add.
    *
    * @param s the s
    */
   synchronized void add(String s)
   {
      if (!isFull())
         items.add(s);
   }

   /**
    * Is empty boolean.
    *
    * @return the boolean
    */
   synchronized boolean isEmpty()
   {
      return items.size() == 0;
   }

   /**
    * Is full boolean.
    *
    * @return the boolean
    */
   synchronized boolean isFull()
   {
      return items.size() == MAXITEMS;
   }

   /**
    * Remove string.
    *
    * @return the string
    */
   synchronized String remove()
   {
      if (!isEmpty())
         return items.remove(0);
      return null;
   }
}