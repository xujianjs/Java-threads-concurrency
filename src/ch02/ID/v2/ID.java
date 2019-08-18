package ch02.ID.v2;

/**
 * The type Id.
 */
public class ID
{
   private int counter; // initialized to 0 by default

  /**
   * Gets id.
   *
   * @return the id
   */
  public synchronized int getID()
   {
      return counter++;
   }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args)
   {
      ID id = new ID();
      System.out.println(id.getID());
   }
}