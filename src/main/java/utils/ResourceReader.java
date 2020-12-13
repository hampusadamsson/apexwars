package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import domain.terrain.cells.Cell;
import domain.unit.Unit;

import java.io.File;
import java.io.IOException;

public class ResourceReader {

    public static Cell[] loadCells() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File("src/main/resources/cells.yaml"), Cell[].class);
    }

    public static Unit[] loadUnits() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File("src/main/resources/units.yaml"), Unit[].class);
    }

}
