package main;

import domain.general.Pid;
import domain.terrain.CreationStrategy;
import domain.terrain.Map;
import domainfactory.UnitFactory;
import game.Game;
import game.PlayerPool;
import game.playerinput.PlayerType;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        var m = new Map(5,5, CreationStrategy.PLAIN);
        m.placeUnit(0, 0, UnitFactory.random(Pid.P1));
        m.placeUnit(4, 4, UnitFactory.random(Pid.P2));
        PlayerPool pp = PlayerPool.builder()
                .addPlayer(Pid.P1, PlayerType.HUMAN_CLI)
                .addPlayer(Pid.P2, PlayerType.AI);
        Game game = new Game(m, pp);
        game.run();
    }
}
