package ch06.CyclicBarrierDemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * The type Cyclic barrier demo.
 */
public class CyclicBarrierDemo
{

   /**
    * The entry point of application.
    *
    * @param args the input arguments
    */
   public static void main(String[] args)
   {
      float[][] matrix = new float[3][3];
      int counter = 0;
      for (int row = 0; row < matrix.length; row++)
         for (int col = 0; col < matrix[0].length; col++)
         matrix[row][col] = counter++;
      dump(matrix);//导出到输出流中
      System.out.println();
      Solver solver = new Solver(matrix);
      System.out.println();
      dump(matrix);
   }

   /**
    * Dump.
    *
    * @param matrix the matrix
    */
   static void dump(float[][] matrix)
   {
      for (int row = 0; row < matrix.length; row++)
      {
         for (int col = 0; col < matrix[0].length; col++)
            System.out.print(matrix[row][col] + " ");
         System.out.println();
      }
   }
}

/**
 * The type Solver.
 */
class Solver
{

   /**
    * The N.
    */
   final int N;
   /**
    * The Data.
    */
   final float[][] data;
   /**
    * The Barrier.
    */
   final CyclicBarrier barrier;

   /**
    * The type Worker.
    */
   class Worker implements Runnable
   {

      /**
       * The My row.
       */
      int myRow;
      /**
       * The Done.
       */
      boolean done = false;

      /**
       * Instantiates a new Worker.
       *
       * @param row the row
       */
      Worker(int row)
      { 
         myRow = row; 
      }

      /**
       * Done boolean.
       *
       * @return the boolean
       */
      boolean done()
      {
         return done;
      }

      /**
       * Process row.
       *
       * @param myRow the my row
       */
      void processRow(int myRow)
      {
         System.out.println("Processing row: " + myRow);
         for (int i = 0; i < N; i++)
            data[myRow][i] *= 10;
         done = true;
      }

      @Override
      public void run() 
      {
         while (!done()) 
         {
            processRow(myRow);

            try 
            {
               barrier.await();
            } 
            catch (InterruptedException ie) 
            {
               return;
            } 
            catch (BrokenBarrierException bbe) 
            {
               return;
            }
         }
      }
   }

   /**
    * Instantiates a new Solver.
    *
    * @param matrix the matrix
    */
   public Solver(float[][] matrix)
   {
      data = matrix;
      N = matrix.length;
      barrier = new CyclicBarrier(N,
                                  new Runnable() 
                                  {
                                     @Override
                                     public void run() 
                                     {
                                        mergeRows();
                                     }
                                  });
      for (int i = 0; i < N; ++i)
         new Thread(new Worker(i)).start();

      waitUntilDone();
   }

   /**
    * Merge rows.
    */
   void mergeRows()
   {
      System.out.println("merging");
      synchronized("abc")
      {
         "abc".notify();
      }
   }

   /**
    * Wait until done.
    */
   void waitUntilDone()
   {
      synchronized("abc")
      {
         try
         {
            System.out.println("main thread waiting");
            "abc".wait();
            System.out.println("main thread notified");
         }
         catch (InterruptedException ie)
         {
            System.out.println("main thread interrupted");
         }
      }
   }
}