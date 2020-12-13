package domain.terrain;

import domain.general.Pid;
import domain.terrain.cells.Position;
import domain.unit.Unit;
import domain.unit.attributes.Status;
import domainfactory.UnitFactory;
import game.playerinput.PlayerAI;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void testMap() throws IOException {
        var m = new Map(5, 4);
        m.placeUnit(2,3, UnitFactory.random(Pid.P1));
    }

    @Test
    void testFlatMap() throws IOException {
        var m = new Map(5, 4);
        m.placeUnit(2,3, UnitFactory.random(Pid.P1));
        m.placeUnit(3,1, UnitFactory.random(Pid.P1));
        m.placeUnit(3,2, UnitFactory.random(Pid.P2));
        assertEquals(m.getUnitCells(Pid.P1).size(), 2);
        assertEquals(m.getUnitCells(Pid.P2).size(), 1);
        m.placeUnit(3,2, UnitFactory.random(Pid.P1));
        assertEquals(m.getUnitCells(Pid.P2).size(), 0);
    }

    @Test
    void testAdjacent() throws IOException {
        var m = new Map(3, 4);
        List<Position> adjacentCells;

        adjacentCells = m.getAdjacentCells(new Position(0, 0));
        assertEquals(2, adjacentCells.size());

        adjacentCells = m.getAdjacentCells(new Position(1, 1));
        assertEquals(4, adjacentCells.size());

        adjacentCells = m.getAdjacentCells(new Position(1, 3));
        assertEquals(3, adjacentCells.size());

        adjacentCells = m.getAdjacentCells(new Position(2, 3));
        assertEquals(2, adjacentCells.size());
    }

    @Test
    void moveUnit() throws IOException {
        var m = new Map(5, 5);
        assertNull(m.getCell(new Position(2, 2)).getOwner());
        m.placeUnit(2, 2, UnitFactory.random(Pid.P1));
        assertNotNull(m.getCell(new Position(2, 2)).getOwner());
        m.move(new Position(2,2), new Position(4,4));
        assertNull(m.getCell(new Position(2, 2)).getOwner());
        assertNotNull(m.getCell(new Position(4, 4)).getOwner());
    }

    @Test
    void moveRandomUnit() throws IOException {
        var m = new Map(5, 5);
        Unit u = UnitFactory.random(Pid.P1);
        u.setStatus(Status.READY);
        m.placeUnit(2, 2, u);
        PlayerAI a = new PlayerAI(Pid.P1);
        a.play(m);
        assertNull(m.getCell(new Position(2, 2)).getOwner());
    }

}