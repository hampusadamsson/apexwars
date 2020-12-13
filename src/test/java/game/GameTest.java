package game;

import domain.general.Pid;
import domain.terrain.CreationStrategy;
import domain.terrain.Map;
import domain.unit.attributes.UnitName;
import domainfactory.UnitFactory;
import game.playerinput.PlayerType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class GameTest {

    @Test
    void aiFightSimulation() throws IOException {
        var m = new Map(3,3, CreationStrategy.PLAIN);
        m.placeUnit(0, 0, UnitFactory.create(UnitName.TANK, Pid.P1));
        m.placeUnit(2, 2, UnitFactory.random(Pid.P2));
        PlayerPool pp = PlayerPool.builder()
                .addPlayer(Pid.P1, PlayerType.AI)
                .addPlayer(Pid.P2, PlayerType.AI);
        Game game = new Game(m, pp);
        game.run();
        game.map.print();
    }

    @Test
    void aiFightSimulationBigger() throws IOException {
        var m = new Map(10,10, CreationStrategy.PLAIN);
        m.placeUnit(0, 0, UnitFactory.random(Pid.P1));
        m.placeUnit(1, 0, UnitFactory.random(Pid.P1));
        m.placeUnit(0, 1, UnitFactory.random(Pid.P1));
        m.placeUnit(1, 1, UnitFactory.random(Pid.P1));

        m.placeUnit(9, 9, UnitFactory.random(Pid.P2));
        m.placeUnit(9, 8, UnitFactory.random(Pid.P2));
        m.placeUnit(8, 9, UnitFactory.random(Pid.P2));
        m.placeUnit(8, 8, UnitFactory.random(Pid.P2));
        PlayerPool pp = PlayerPool.builder()
                .addPlayer(Pid.P1, PlayerType.AI)
                .addPlayer(Pid.P2, PlayerType.AI);
        Game game = new Game(m, pp);
        game.run();
        game.map.print();
    }

}