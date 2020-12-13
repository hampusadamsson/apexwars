package game.playerinput;

import domain.general.Pid;
import domain.terrain.Map;

public interface Player {
    void play(Map map);
    Pid getPid();
}
