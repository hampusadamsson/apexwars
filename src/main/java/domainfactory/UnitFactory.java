package domainfactory;

import domain.general.Pid;
import domain.unit.Unit;
import domain.unit.attributes.UnitName;
import utils.ResourceReader;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;

public class UnitFactory {

    public static Unit create(UnitName un, Pid p) throws IOException {
        for (Unit u: ResourceReader.loadUnits()) {
            if(u.getUnitName().equals(un)) {
                u.setPlayer(p);
                u.setCurrentHealth(u.getMaxHealth());
                return u;
            }
        }
        throw new NoSuchElementException(un.toString() + " does not exist");
    }

    public static Unit random(Pid p) throws IOException {
        Random rnd = new Random();
        int i = rnd.nextInt(UnitName.values().length);
        return create(UnitName.values()[i], p);
    }

}
