package ch02.ID.v3;

/**
 * The type Id.
 */
public class ID
{
   private static int counter; // initialized to 0 by default

  /**
   * Gets id.
   *
   * @return the id
   */
  public static int getID()
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
      System.out.println(ID.getID());
   }
}