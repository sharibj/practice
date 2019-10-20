package tdd.game.of.life;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sjafari
 *
 */
public class Universe {
	private Set<Point> liveCells = new HashSet<>();

	public Integer liveCellCount() {
		return liveCells.size();
	}

	public boolean addLiveCell(Point point) {
		return liveCells.add(point);
	}

	public Universe getNextGeneration() {
		Universe nextGenerationUniverse = new Universe();
		getLiveCellsForNextGeneration().parallelStream().forEach(nextGenerationUniverse::addLiveCell);
		return nextGenerationUniverse;
	}

	private Set<Point> getLiveCellsForNextGeneration() {
		Set<Point> nextGenerationLiveCells = liveCells.parallelStream().filter(this::isSurvivable)
				.collect(Collectors.toSet());
		nextGenerationLiveCells.addAll(getReproduceableCells());
		return nextGenerationLiveCells;
	}

	private boolean isSurvivable(Point point) {
		int liveNeighboursCount = getLiveNeighboursCount(point);
		return !isUnderPopulated(liveNeighboursCount) && !isOverPopulated(liveNeighboursCount);
	}

	private boolean isUnderPopulated(int liveNeighboursCount) {
		return liveNeighboursCount < 2;
	}

	private boolean isOverPopulated(int liveNeighboursCount) {
		return liveNeighboursCount > 3;
	}

	private int getLiveNeighboursCount(Point point) {
		Set<Point> neighbours = getNeighbours(point);
		return (int) neighbours.parallelStream().filter(liveCells::contains).count();
	}

	private Set<Point> getNeighbours(Point point) {
		Set<Point> neighbours = get3by3gridWithPointAsCentre(point);
		neighbours.remove(point);
		return neighbours;
	}

	private Set<Point> get3by3gridWithPointAsCentre(Point point) {
		Set<Point> grid = new HashSet<>();
		for (int x : getPreviousCurrentAndNextNumebers(point.x))
			for (int y : getPreviousCurrentAndNextNumebers(point.y))
				grid.add(new Point(x, y));
		return grid;
	}

	private int[] getPreviousCurrentAndNextNumebers(int num) {
		return new int[] { num - 1, num, num + 1 };
	}

	private Set<Point> getReproduceableCells() {
		Set<Point> reproducedCells = new HashSet<>();
		getNeighboursOfEveryAliveCell().parallelStream().filter(this::isReproduceable).forEach(reproducedCells::add);
		return reproducedCells;
	}

	private Set<Point> getNeighboursOfEveryAliveCell() {
		return liveCells.parallelStream().map(this::getNeighbours).reduce(new HashSet<Point>(), this::mergeSets);
	}

	private Set<Point> mergeSets(Set<Point> set1, Set<Point> set2) {
		Set<Point> mergedSet = new HashSet<>(set1);
		mergedSet.addAll(set2);
		return mergedSet;
	}

	private boolean isReproduceable(Point point) {
		return getLiveNeighboursCount(point) == 3;
	}
}