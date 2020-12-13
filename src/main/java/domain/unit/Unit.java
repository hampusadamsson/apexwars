package domain.unit;

import domain.general.Pid;
import domain.terrain.Map;
import domain.terrain.cells.Position;
import domain.unit.attributes.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pathfinding.Dijkstra;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Unit {
    Pid player;
    UnitName unitName;
    UnitType unitType;
    List<UnitAttributes> unitAttributes;
    Attack attack;
    Status status;
    int cost;
    int value;
    int maxHealth;
    int currentHealth;
    int moveRange;

    public Set<Position> getPossibleMoves(Map map, Position p){
        return Dijkstra.getPossibleMoves(map, p, this);
    }

    public boolean inAttackRange(Position source, Position target) {
        int distance = Math.abs(target.x-source.x) + Math.abs(target.y-source.y);
        return  (distance <= this.attack.getAttackRange());
    }

    public boolean canAttackType(Unit target) {
        return true; //TODO - LAND vs. AIR etc.
    }

    public void refresh() {
        this.status = Status.READY;
    }

    public boolean isReadyToAttack() {
        if (this.status.equals(Status.READY)) {
            return true;
        }
        if (this.unitAttributes != null && !this.unitAttributes.contains(UnitAttributes.RANGE) && this.status.equals(Status.MOVED)) {
            return true;
        }
        return false;
    }
}
