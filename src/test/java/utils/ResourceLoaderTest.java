package utils;

import domain.terrain.cells.Cell;
import domain.unit.Unit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

class ResourceLoaderTest {

    @Test
    void testConstructor() throws IOException {
        Cell[] read = ResourceReader.loadCells();
        Arrays.stream(read).forEach(c -> {
            System.out.println(c.toString());
        });
    }

    @Test
    void testLoadUnits() throws IOException {
        Unit[] read = ResourceReader.loadUnits();
        Arrays.stream(read).forEach(c -> {
            System.out.println(c.toString());
        });
    }
}