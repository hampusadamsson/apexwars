package game;

import domain.general.Pid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void changeTurn() {
        var t = new PlayerPool();
        assertEquals(t.getCurrentPlayer(), Pid.P1);
        t.changeTurn();
        assertEquals(t.getCurrentPlayer(), Pid.P2);
        t.changeTurn();
        assertEquals(t.getCurrentPlayer(), Pid.P1);
    }
}