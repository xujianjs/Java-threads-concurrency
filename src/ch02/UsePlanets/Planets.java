package ch02.UsePlanets;

import java.util.Set;
import java.util.TreeSet;

/**
 * The type Planets.
 */
public final class Planets
{
   private final Set<String> planets = new TreeSet<>();

  /**
   * Instantiates a new Planets.
   */
  public Planets()
   {
      planets.add("Mercury");
      planets.add("Venus");
      planets.add("Earth");
      planets.add("Mars");
      planets.add("Jupiter");
      planets.add("Saturn");
      planets.add("Uranus");
      planets.add("Neptune");
   }

  /**
   * Is planet boolean.
   *
   * @param planetName the planet name
   * @return the boolean
   */
  public boolean isPlanet(String planetName)
   {
      return planets.contains(planetName);
   }
}