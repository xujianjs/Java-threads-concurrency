package ch07.PC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Pc.
 */
public class PC
{

   /**
    * The entry point of application.
    *
    * @param args the input arguments
    */
   public static void main(String[] args)
   {
      Shared s = new Shared();
      new Producer(s).start();
      new Consumer(s).start();
   }
}

/**
 * The type Shared.
 */
class Shared
{
   private char c;

   // volatile 主存可见性
   private volatile boolean available;

   private final Lock lock;

   private final Condition condition;

   /**
    * Instantiates a new Shared.
    */
   Shared()
   {
      available = false;
      lock = new ReentrantLock();
      condition = lock.newCondition();
   }

   /**
    * Gets lock.
    *
    * @return the lock
    */
   Lock getLock()
   {
      return lock;
   }

   /**
    * Gets shared char.
    *
    * @return the shared char
    */
   char getSharedChar()
   {
      lock.lock();
      try
      {
         while (!available)
            try
            {
               condition.await();
            }
            catch (InterruptedException ie)
            {
               ie.printStackTrace();
            }
         available = false;
         condition.signal();
      }
      finally
      {
         lock.unlock();
         return c;
      }
   }

   /**
    * Sets shared char.
    *
    * @param c the c
    */
   void setSharedChar(char c)
   {
      lock.lock();
      try
      {
         while (available)
            try
            {
               condition.await();
            }
            catch (InterruptedException ie)
            {
               ie.printStackTrace();
            }
         this.c = c;
         available = true;
         condition.signal();
      }
      finally
      {
         lock.unlock();
      }
   }
}

/**
 * The type Producer.
 */
class Producer extends Thread
{
   private final Lock l;

   private final Shared s;

   /**
    * Instantiates a new Producer.
    *
    * @param s the s
    */
   Producer(Shared s)
   {
      this.s = s;
      l = s.getLock();
   }

   @Override
   public void run()
   {
      for (char ch = 'A'; ch <= 'Z'; ch++)
      {
         l.lock();
         s.setSharedChar(ch);
         System.out.println(ch + " produced by producer.");
         l.unlock();
      }
   }
}

/**
 * The type Consumer.
 */
class Consumer extends Thread
{
   private final Lock l;

   private final Shared s;

   /**
    * Instantiates a new Consumer.
    *
    * @param s the s
    */
   Consumer(Shared s)
   {
      this.s = s;
      l = s.getLock();
   }

   @Override
   public void run()
   {
      char ch;
      do
      {
         l.lock();
         ch = s.getSharedChar();
         System.out.println(ch + " consumed by consumer.");
         l.unlock();
      }
      while (ch != 'Z');
   }
}