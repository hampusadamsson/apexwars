package domain.terrain.cells;

import domain.general.Pid;
import domain.unit.Unit;
import domain.unit.attributes.Attack;
import domain.unit.attributes.Status;
import domain.unit.attributes.UnitAttributes;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.print.DocFlavor;

@Data
@NoArgsConstructor
@Slf4j
public class Cell {
    CellType name;
    float defence;
    int moveCost;
    Position position;
    Unit unit;

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }

    public boolean hasOwner() {
        return this.getOwner() != null;
    }

    public Pid getOwner() {
        return this.unit != null ? this.unit.getPlayer() : null;
    }

    public void attack(Cell target) { //TODO - better attack
        Attack attack = this.unit.getAttack();
        int dmg = attack.getAttackDamage();
        Unit targetUnit = target.getUnit();
        int currentHealth = targetUnit.getCurrentHealth();
        targetUnit.setCurrentHealth(currentHealth-dmg);
        log.info("Unit damaged :: " + (currentHealth - dmg));
        if (targetUnit.getCurrentHealth() <= 0) {
            log.info("Unit destroyed :: " + target.toString());
            target.setUnit(null);
        }
        this.getUnit().setStatus(Status.EXHAUSTED);
    }

    public boolean canAttack() {
        if (this.hasOwner()) {
            return this.unit.isReadyToAttack();
        }
        return false;
    }

    public boolean isReady() {
        return this.hasOwner() && this.getUnit().getStatus().equals(Status.READY);
    }
}
