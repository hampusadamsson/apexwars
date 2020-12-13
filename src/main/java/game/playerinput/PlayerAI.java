package game.playerinput;

import domain.general.Pid;
import domain.terrain.Map;
import domain.terrain.cells.Cell;
import domain.terrain.cells.Position;
import domain.unit.Unit;
import domain.unit.attributes.Status;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Slf4j
@Data
public class PlayerAI implements Player {
    Random rnd = new Random();
    Pid pid;

    public PlayerAI(Pid pid) {
        this.pid = pid;
    }

    @Override
    public void play(Map map) {
        List<Cell> unitCells = map.getUnitCells(this.pid);
        for (Cell c : unitCells) {
            boolean attacked = this.tryToAttack(map, c);
            if (!attacked) {
                Position randomMove = this.getRandomMove(map, c);
                map.move(c.getPosition(), randomMove);
                this.tryToAttack(map, map.getCell(randomMove));
            }
        }
    }

    public boolean tryToAttack(Map map, Cell c) {
        for (Cell t : map.getEnemyUnitCells(this.pid)) {
            if (map.attack(c.getPosition(), t.getPosition())) {
                log.info(c.toString() + " attacking " + t.toString());
                return true;
            }
        }
        return false;
    }

    private Position getRandomMove(Map map, Cell c) {
        Unit u = c.getUnit();
        Set<Position> moves = u.getPossibleMoves(map, c.getPosition());
        int i = rnd.nextInt(moves.size());
        int result = 0;
        for (Position entry : moves) {
            if (result == i) {
                return entry;
            }
            result++;
        }
        throw new IndexOutOfBoundsException("Randomly selected move not found in set of possible moves");
    }
}
