package ch02.UsePlanets;

/**
 * The type Use planets.
 */
public class UsePlanets
{

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args)
   {
      Planets planets = new Planets();
      System.out.println(planets.isPlanet("Earth"));
      System.out.println(planets.isPlanet("Vulcan"));
   }
}