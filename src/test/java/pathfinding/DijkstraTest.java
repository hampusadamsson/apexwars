package pathfinding;

import domain.general.Pid;
import domain.terrain.CreationStrategy;
import domain.terrain.Map;
import domain.terrain.cells.Cell;
import domain.terrain.cells.Position;
import domain.unit.Unit;
import domainfactory.UnitFactory;
import domain.unit.attributes.UnitName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    @Test
    void testDjikstra() throws IOException {
        var map = new Map(10, 10, CreationStrategy.PLAIN);
        Unit u = UnitFactory.create(UnitName.INFANTRY, Pid.P1);
        u.setMoveRange(2);
        map.placeUnit(5, 5, u);
        Set<Position> possibleMoves = Dijkstra.getPossibleMoves(map, new Position(5, 5), u);
        possibleMoves.forEach(p -> {
            Cell tc = map.getCell(p.x, p.y);
            tc.setMoveCost(tc.getMoveCost()*-1);
        });
        map.print();
        assertEquals(4, possibleMoves.size());
    }

    @Test
    void testDjikstraBlock() throws IOException {
        var map = new Map(10, 10, CreationStrategy.PLAIN);
        Unit u = UnitFactory.create(UnitName.INFANTRY, Pid.P1);
        u.setMoveRange(4);
        map.placeUnit(5, 5, u);
        map.placeUnit(5, 4, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        map.placeUnit(5, 6, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        Set<Position> possibleMoves = Dijkstra.getPossibleMoves(map, new Position(5, 5), u);
        possibleMoves.forEach(p -> {
            Cell tc = map.getCell(p.x, p.y);
            tc.setMoveCost(tc.getMoveCost()*-1);
        });
        assertEquals(18, possibleMoves.size());
    }

    @Test
    void testDjikstraBlockFull() throws IOException {
        var map = new Map(10, 10, CreationStrategy.PLAIN);
        Unit u = UnitFactory.create(UnitName.INFANTRY, Pid.P1);
        u.setMoveRange(4);
        map.placeUnit(5, 5, u);
        map.placeUnit(5, 4, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        map.placeUnit(5, 6, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        map.placeUnit(4, 5, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        map.placeUnit(6, 5, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        Set<Position> possibleMoves = Dijkstra.getPossibleMoves(map, new Position(5, 5), u);
        possibleMoves.forEach(p -> {
            Cell tc = map.getCell(p.x, p.y);
            tc.setMoveCost(tc.getMoveCost()*-1);
        });
        assertEquals(0, possibleMoves.size());
    }

    @Test
    void testDjikstraBlockFullButOneFriendly() throws IOException {
        var map = new Map(10, 10, CreationStrategy.PLAIN);
        Unit u = UnitFactory.create(UnitName.INFANTRY, Pid.P1);
        u.setMoveRange(4);
        map.placeUnit(5, 5, u);
        map.placeUnit(5, 4, UnitFactory.create(UnitName.INFANTRY, Pid.P1));
        map.placeUnit(5, 6, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        map.placeUnit(4, 5, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        map.placeUnit(6, 5, UnitFactory.create(UnitName.INFANTRY, Pid.P2));
        Set<Position> possibleMoves = Dijkstra.getPossibleMoves(map, new Position(5, 5), u);
        possibleMoves.forEach(p -> {
            Cell tc = map.getCell(p.x, p.y);
            tc.setMoveCost(tc.getMoveCost()*-1);
        });
        assertEquals(8, possibleMoves.size());
    }

    @Test
    void testDjikstraUnitAndCell() throws IOException {
        var map = new Map(10, 10, CreationStrategy.PLAIN);
        Unit u = UnitFactory.create(UnitName.INFANTRY, Pid.P1);
        u.setMoveRange(3);
        map.placeUnit(5, 5, u);
        Set<Position> possibleMoves = Dijkstra.getPossibleMoves(map, new Position(5, 5), u);
        Set<Position> moves = u.getPossibleMoves(map, new Position(5, 5));
        assertEquals(moves, possibleMoves);
        assertEquals(12, moves.size());
    }
}