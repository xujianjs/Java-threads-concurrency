package ch03.PC.v1;

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
   private volatile boolean writeable = true;

   /**
    * Sets shared char.
    *
    * @param c the c
    */
   synchronized void setSharedChar(char c)
   {
      while (!writeable)
         try
         {
            wait();
         }
         catch (InterruptedException ie) 
         {
         }
      this.c = c;
      writeable = false;
      notify();
   }

   /**
    * Gets shared char.
    *
    * @return the shared char
    */
   synchronized char getSharedChar()
   {
      while (writeable)
         try
         {
            wait();
         }
         catch (InterruptedException ie) 
         {
         }
      writeable = true;
      notify();
      return c;
   }
}

/**
 * The type Producer.
 */
class Producer extends Thread
{
   private final Shared s;

   /**
    * Instantiates a new Producer.
    *
    * @param s the s
    */
   Producer(Shared s)
   {
      this.s = s;
   }

   @Override
   public void run()
   {
      for (char ch = 'A'; ch <= 'Z'; ch++)
      {
         s.setSharedChar(ch);
         System.out.println(ch + " produced by producer.");
      }
   }
}

/**
 * The type Consumer.
 */
class Consumer extends Thread
{
   private final Shared s;

   /**
    * Instantiates a new Consumer.
    *
    * @param s the s
    */
   Consumer(Shared s)
   {
      this.s = s;
   }

   @Override
   public void run()
   {
      char ch;
      do
      {
         ch = s.getSharedChar();
         System.out.println(ch + " consumed by consumer.");
      }
      while (ch != 'Z');
   }
}