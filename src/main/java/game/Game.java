package game;

import domain.general.Pid;
import domain.terrain.Map;
import domain.terrain.cells.Cell;
import domain.unit.Unit;
import game.playerinput.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Game {
    Map map;
    PlayerPool playerPool;
    boolean gameOver;

    public Game(Map map, PlayerPool playerPool) {
        this.map = map;
        this.gameOver = false;
        this.playerPool = playerPool;
    }

    public void run() {
        assert playerPool.validPlayerPool();
        int i=0;
        while (!this.gameOver) {
            i++;
            log.info(i + " :: " + this.playerPool.getCurrentPlayer().toString());
            this.map.print();
            Pid current = this.playerPool.getCurrentPlayer();
            this.refreshPlayerUnits(current);
            this.play(current);
            this.playerPool.changeTurn();
            this.gameOver = this.isGameOver();
        }
    }

    public void play(Pid p){
        this.playerPool.get(p).play(this.map);
    }

    public boolean isGameOver(){
        for (Player p : this.playerPool.allPlayers()) {
            List<Cell> allUnitCells = this.map.getUnitCells(p.getPid());
            if (allUnitCells.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void refreshPlayerUnits(Pid p) {
        this.map.getUnitCells(p).stream()
                .map(Cell::getUnit)
                .forEach(Unit::refresh);
    }
}
