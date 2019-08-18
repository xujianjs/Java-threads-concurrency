package ch02.ID.v1;

/**
 * The type Id.
 */
public class ID {

  private int counter; // initialized to 0 by default

  /**
   * Gets id.
   *
   * @return the id
   */
  public int getID() {
    return counter++;
  }

  /**
   * Gets counter.
   *
   * @return the counter
   */
  public int getCounter() {
    return counter;
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    ID id = new ID();
    System.out.println(id.getID());
    System.out.println(id.getCounter());
  }
}