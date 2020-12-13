package domainfactory;

import domain.terrain.cells.Cell;
import domain.terrain.cells.CellType;
import utils.ResourceReader;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;

public class CellFactory {

    public static Cell create(CellType ct, int x, int y) throws IOException {
        for (Cell c: ResourceReader.loadCells()) {
            if(c.getName().equals(ct)) {
                c.setPosition(x, y);
                return c;
            }
        }
        throw new NoSuchElementException(ct.toString() + " does not exist");
    }

    public static Cell random(int x,  int y) throws IOException {
        Random rnd = new Random();
        int i = rnd.nextInt(CellType.values().length);
        return create(CellType.values()[i], x, y);
    }

    public static Cell plain(int x,  int y) throws IOException {
        return create(CellType.PLAIN, x, y);
    }

}
