package domain.terrain.cells;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testEquals() {
        assertEquals(new Position(2,2 ), new Position(2, 2));
        HashSet<Position> objects = new HashSet<>();
        objects.add(new Position(0,1));
        objects.add(new Position(1,1));
        assertTrue(objects.contains(new Position(1,1)));
    }
}