package domain.unit;

import domain.general.Pid;
import domain.terrain.cells.Position;
import domain.unit.attributes.UnitName;
import domainfactory.UnitFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Test
    void testAttackRange() throws IOException {
        Unit a = UnitFactory.create(UnitName.INFANTRY, Pid.P1);
        Unit b = UnitFactory.create(UnitName.INFANTRY, Pid.P2);
        boolean r;
        r = a.inAttackRange(new Position(4, 4), new Position(5, 4));
        assertTrue(r);
        r = a.inAttackRange(new Position(4, 4), new Position(5, 5));
        assertFalse(r);

        b.getAttack().setAttackRange(2);
        r = a.inAttackRange(new Position(4, 4), new Position(5, 5));
        assertFalse(r);

        a.getAttack().setAttackRange(2);
        r = a.inAttackRange(new Position(4, 4), new Position(5, 5));
        assertTrue(r);
    }
}