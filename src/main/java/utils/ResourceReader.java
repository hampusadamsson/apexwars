package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import domain.terrain.Map;
import domain.terrain.cells.Cell;
import domain.unit.Unit;

import java.io.File;
import java.io.IOException;

public class ResourceReader {
    static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static Cell[] loadCells() throws IOException {
        return mapper.readValue(new File("src/main/resources/cells.yaml"), Cell[].class);
    }

    public static Unit[] loadUnits() throws IOException {
        return mapper.readValue(new File("src/main/resources/units.yaml"), Unit[].class);
    }

    public static Map[] loadMaps() throws IOException {
        return mapper.readValue(new File("src/main/resources/maps.yaml"), Map[].class);
    }

}
