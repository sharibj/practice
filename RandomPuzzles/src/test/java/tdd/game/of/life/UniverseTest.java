package tdd.game.of.life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.Test;

/**
 * @author sjafari
 *
 */
public class UniverseTest {
	@Test
	public void whenNoCellIsAlive_thenCellsRemainDead() {
		Universe universe = new Universe();
		Universe nextUniverse = universe.getNextGeneration();
		assertEquals(0, nextUniverse.liveCellCount());
	}

	@Test
	public void testliveCellCount() {
		Universe universe = new Universe();
		assertTrue(universe.addLiveCell(new Point(0, 0)));
		assertTrue(universe.addLiveCell(new Point(0, 5)));
		assertFalse(universe.addLiveCell(new Point(0, 0)));
		assertEquals(2, universe.liveCellCount());
		assertEquals(0, universe.getNextGeneration().liveCellCount());
	}

	@Test
	// Any live cell with fewer than two live neighbours dies, as if by
	// underpopulation.
	public void testUnderPopulation() {
		Universe universe = new Universe();
		universe.addLiveCell(new Point(0, 0));
		universe.addLiveCell(new Point(1, 1));
		universe.addLiveCell(new Point(2, 2));
		Universe nextUniverse = universe.getNextGeneration();
		assertEquals(1, nextUniverse.liveCellCount());
	}

	@Test
	// Any live cell with more than three live neighbours dies, as if by
	// overpopulation.
	public void testOverPopulation() {
		Universe universe = new Universe();
		universe.addLiveCell(new Point(0, 0));
		universe.addLiveCell(new Point(0, 2));
		universe.addLiveCell(new Point(1, 1));
		universe.addLiveCell(new Point(2, 0));
		universe.addLiveCell(new Point(2, 2));
		Universe nextUniverse = universe.getNextGeneration();
		assertEquals(4, nextUniverse.liveCellCount());
	}

	@Test
	// Any live cell with two or three live neighbours lives on to the next
	// generation.
	public void testSurvival() {
		Universe blockUniverse = new Universe();
		blockUniverse.addLiveCell(new Point(0, 0));
		blockUniverse.addLiveCell(new Point(0, 1));
		blockUniverse.addLiveCell(new Point(1, 0));
		blockUniverse.addLiveCell(new Point(1, 1));
		Universe nextUniverse = blockUniverse.getNextGeneration();
		assertEquals(4, nextUniverse.liveCellCount());
	}

	@Test
	// Any dead cell with exactly three live neighbours becomes a live cell, as if
	// by reproduction.
	public void testReproduction() {
		Universe universe = new Universe();
		universe.addLiveCell(new Point(1, 0));
		universe.addLiveCell(new Point(1, 1));
		universe.addLiveCell(new Point(1, 2));
		Universe nextUniverse = universe.getNextGeneration();
		assertEquals(3, nextUniverse.liveCellCount());

	}
}
