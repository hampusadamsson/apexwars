package pathfinding;

import domain.general.Pid;
import domain.terrain.Map;
import domain.terrain.cells.Cell;
import domain.terrain.cells.Position;
import domain.unit.Unit;

import java.util.*;

public class Dijkstra {

    public static Set<Position> getPossibleMoves(Map map, Position source, Unit u) {
        HashSet<Position> paths = new HashSet<>();
        next(u.getPlayer(), paths, map, u.getMoveRange(), source);
        return paths;
    }

    private static void next(Pid owner, Set<Position> paths, Map map, int remainingMoves, Position current) {
        Cell c = map.getCell(current);
        int moveCost = c.getMoveCost();
        if (c.getOwner() != null & c.getOwner() != owner) {
            moveCost = Integer.MAX_VALUE;
        }
        remainingMoves -= moveCost;
        if (remainingMoves >= 0) {
            if (c.getOwner() == null) {
                paths.add(current);
            }
            int finalRemainingMoves = remainingMoves;
            map.getAdjacentCells(current).forEach(p -> {
                next(owner, paths, map, finalRemainingMoves, p);
            });
        }
    }
}
