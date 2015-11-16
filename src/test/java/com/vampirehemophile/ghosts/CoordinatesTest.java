package com.vampirehemophile.ghosts;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for @{link Coordinates} class.
 *
 * All the constructors are tested to ensure coordinates validity.
 */
public class CoordinatesTest {
  @Test
  public void testCoordinatesConstructorIndex() {
    Coordinates c1 = new Coordinates(0, 1);
    assertEquals(0, c1.xMatrix());
    assertEquals(0, c1.yMatrix());
    assertEquals(0, c1.index());

    try {
      new Coordinates(2, 1);
    } catch (OutOfBoardCoordinatesException expected) {}
  }

  @Test
  public void testCoordinatesConstructorMatrix() {
    Coordinates c1 = new Coordinates(0, 0, 1);
    assertEquals(0, c1.xMatrix());
    assertEquals(0, c1.yMatrix());

    try {
      new Coordinates(2, 0, 1);
    } catch (OutOfBoardCoordinatesException expected) {}
    try {
      new Coordinates(0, 2, 1);
    } catch (OutOfBoardCoordinatesException expected) {}
  }

  @Test
  public void testCoordinatesConstructorWorld1() {
    Coordinates c = new Coordinates("a1", 1);
    assertEquals("a", c.x());
    assertEquals(1, c.y());

    try {
      new Coordinates("az~'78", 1);
    } catch (InvalidCoordinatesException expected) {}
    try {
      new Coordinates("b1", 1);
    } catch (OutOfBoardCoordinatesException expected) {}
    try {
      new Coordinates("a2", 1);
    } catch (OutOfBoardCoordinatesException expected) {}
  }

  @Test
  public void testCoordinatesConstructorWorld2() {
    Coordinates c = new Coordinates("a", 1, 1);
    assertEquals("a", c.x());
    assertEquals(1, c.y());

    try {
      new Coordinates("7", 1, 1);
    } catch (InvalidCoordinatesException expected) {}
    try {
      new Coordinates("b", 1, 1);
    } catch (OutOfBoardCoordinatesException expected) {}
    try {
      new Coordinates("a", 2, 1);
    } catch (OutOfBoardCoordinatesException expected) {}
  }

  @Test
  public void testGenXWorld() {
    assertEquals("a", (new Coordinates(0, 0, 3)).x());
    assertEquals("b", (new Coordinates(1, 1, 3)).x());
    assertEquals("c", (new Coordinates(2, 2, 3)).x());
    assertEquals("aa", (new Coordinates(26, 26, 27)).x());
  }

  @Test
  public void testGenYWorld() {
    assertEquals(1, (new Coordinates(0, 0, 3)).y());
    assertEquals(2, (new Coordinates(1, 1, 3)).y());
    assertEquals(3, (new Coordinates(2, 2, 3)).y());
    assertEquals(27, (new Coordinates(26, 26, 27)).y());
  }

  @Test
  public void testGenXMatrix() {
    assertEquals(0, (new Coordinates("a1", 3)).xMatrix());
    assertEquals(1, (new Coordinates("b2", 3)).xMatrix());
    assertEquals(2, (new Coordinates("c3", 3)).xMatrix());
    assertEquals(26, (new Coordinates("aa27", 27)).xMatrix());
  }

  @Test
  public void testGenYMatrix() {
    assertEquals(0, (new Coordinates("a1", 3)).yMatrix());
    assertEquals(1, (new Coordinates("b2", 3)).yMatrix());
    assertEquals(2, (new Coordinates("c3", 3)).yMatrix());
    assertEquals(26, (new Coordinates("aa27", 27)).yMatrix());
  }

  @Test
  public void testGenIndex() {
    assertEquals(0, (new Coordinates(0, 0, 3)).index());
    assertEquals(4, (new Coordinates(1, 1, 3)).index());
    assertEquals(8, (new Coordinates(2, 2, 3)).index());
    assertEquals(728, (new Coordinates(26, 26, 27)).index());

    assertEquals(0, (new Coordinates("a1", 3)).index());
    assertEquals(4, (new Coordinates("b2", 3)).index());
    assertEquals(8, (new Coordinates("c3", 3)).index());
    assertEquals(728, (new Coordinates("aa27", 27)).index());
  }

  public void testEquality(Coordinates c1, Coordinates c2) {
    assertEquals(c1.x(), c2.x());
    assertEquals(c1.y(), c2.y());
    assertEquals(c1.xMatrix(), c2.xMatrix());
    assertEquals(c1.yMatrix(), c2.yMatrix());
    assertEquals(c1.index(), c2.index());
    assertTrue(c1.equals(c2));
    assertTrue(c2.equals(c1));
  }

  public void testNonEquality(Coordinates c1, Coordinates c2) {
    assertTrue(!c1.x().equals(c2.x()) || c1.y() != c2.y());
    assertTrue(c1.xMatrix() != c2.xMatrix() || c1.yMatrix() != c2.yMatrix());
    assertNotEquals(c1.index(), c2.index());
    assertFalse(c1.equals(c2));
    assertFalse(c2.equals(c1));
  }

  @Ignore @Test
  public void testEquals() {
    Coordinates[] equalCoords = {
      new Coordinates(0, 1),
      new Coordinates(0, 1),
      new Coordinates(0, 2),
      new Coordinates(0, 0, 1),
      new Coordinates(0, 0, 1),
      new Coordinates(0, 0, 2),
      new Coordinates("a1", 1),
      new Coordinates("a1", 1),
      new Coordinates("a1", 2),
      new Coordinates("a", 1, 1),
      new Coordinates("a", 1, 1),
      new Coordinates("a", 1, 2)
    };

    for (int i = 0; i < equalCoords.length; i++) {
      for (int j = i; j < equalCoords.length; j++) {
        assertTrue(equalCoords[i].equals(equalCoords[j]));
        testEquality(equalCoords[i], equalCoords[j]);
      }
    }

    Coordinates[] notEqualCoords = {
      new Coordinates(0, 3),
      new Coordinates(1, 3),
      new Coordinates(2, 0, 3),
      new Coordinates(0, 1, 3),
      new Coordinates("b2", 3),
      new Coordinates("c2", 3),
      new Coordinates("a", 3, 3),
      new Coordinates("b", 3, 3)
    };

    for (int i = 0; i < notEqualCoords.length; i++) {
      for (int j = i + 1; j < notEqualCoords.length; j++) {
        assertFalse(notEqualCoords[i].equals(notEqualCoords[j]));
        testNonEquality(notEqualCoords[i], notEqualCoords[j]);
      }
    }
  }

  @Test
  public void testCopyConstructor() {
    Coordinates c1 = new Coordinates(0, 1);
    Coordinates c2 = new Coordinates(c1);
    assertTrue(c1.equals(c2));
    testEquality(c1, c2);
  }

  @Test
  public void testMoveNorth() {
    Coordinates c1 = new Coordinates(0, 0, 1);
    Coordinates c2 = new Coordinates(c1);
    Coordinates c3 = new Coordinates(0, 1, 2);
    c2.moveNorth();
    testEquality(c1, c2);
    c3.moveNorth();
    testEquality(c1, c3);
  }

  @Test
  public void testMoveSouth() {
    Coordinates c1 = new Coordinates(0, 1, 2);
    Coordinates c2 = new Coordinates(c1);
    Coordinates c3 = new Coordinates(0, 0, 2);
    c2.moveSouth();
    assertTrue(c1.equals(c2));
    c3.moveSouth();
    assertTrue(c1.equals(c3));
  }

  @Test
  public void testMoveEast() {
    Coordinates c1 = new Coordinates(1, 0, 2);
    Coordinates c2 = new Coordinates(c1);
    Coordinates c3 = new Coordinates(0, 0, 2);
    c2.moveEast();
    System.out.println("1" + c1.x() + ":" + c2.x());
    assertEquals(c1.x(), c2.x());
    assertTrue(c1.equals(c2));
    c3.moveEast();
    assertEquals(c1.x(), c3.x());
    assertTrue(c1.equals(c3));
  }

  @Test
  public void testMoveWest() {
    Coordinates c1 = new Coordinates(0, 0, 2);
    Coordinates c2 = new Coordinates(c1);
    Coordinates c3 = new Coordinates(1, 0, 2);
    c2.moveWest();
    System.out.println("2" + c1.x() + ":" + c2.x());
    assertEquals(c1.x(), c2.x());
    assertTrue(c1.equals(c2));
    c3.moveWest();
    assertEquals(c1.x(), c3.x());
    assertTrue(c1.equals(c3));
  }

  @Test
  public void testNorth() {
    Coordinates c1 = new Coordinates(0, 0, 2);
    Coordinates c2 = new Coordinates(0, 1, 2);
    assertNull(c1.north());
    assertNotNull(c2.north());
    assertTrue(c1.equals(c2.north()));
  }

  @Test
  public void testSouth() {
    Coordinates c1 = new Coordinates(0, 1, 2);
    Coordinates c2 = new Coordinates(0, 0, 2);
    assertNull(c1.south());
    assertNotNull(c2.south());
    assertTrue(c1.equals(c2.south()));
  }

  @Test
  public void testEast() {
    Coordinates c1 = new Coordinates(1, 0, 2);
    Coordinates c2 = new Coordinates(0, 0, 2);
    assertNull(c1.east());
    assertNotNull(c2.east());
    assertTrue(c1.equals(c2.east()));
  }

  @Test
  public void testWest() {
    Coordinates c1 = new Coordinates(0, 0, 2);
    Coordinates c2 = new Coordinates(1, 0, 2);
    assertNull(c1.west());
    assertNotNull(c2.west());
    assertTrue(c1.equals(c2.west()));
  }
}
