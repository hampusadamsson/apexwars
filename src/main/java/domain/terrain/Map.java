package domain.terrain;

import domain.general.Pid;
import domain.terrain.cells.Cell;
import domain.unit.attributes.Status;
import domainfactory.CellFactory;
import domain.terrain.cells.Position;
import domain.unit.Unit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
public class Map {
    int width;
    int height;
    ArrayList<ArrayList<Cell>> cells;

    public Map(int width, int height, CreationStrategy cs) throws IOException {
        this.width = width;
        this.height = height;
        this.cells = this.createMap(cs);
    }

    public Map(int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        this.cells = this.createMap(CreationStrategy.RANDOM);
    }

    public ArrayList<ArrayList<Cell>> createMap(CreationStrategy cs) throws IOException {
        ArrayList<ArrayList<Cell>> cells = new ArrayList<>(this.height);
        for(int i=0; i < this.height; i++) {
            ArrayList<Cell> row = new ArrayList<>(this.width);
            for(int j=0; j < this.width; j++) {
                switch (cs) {
                    case PLAIN -> row.add(CellFactory.plain(i, j));
                    default -> row.add(CellFactory.random(i, j));
                }
            }
            cells.add(row);
        }
        return cells;
    }

    public List<Cell> getUnitCells(Pid p) {
        return this.getAllCells().stream()
                .filter(c -> c.getUnit() != null && c.getOwner().equals(p))
                .collect(Collectors.toList());
    }

    public List<Cell> getEnemyUnitCells(Pid p) {
        return this.getAllCells().stream()
                .filter(c -> c.getUnit() != null && !c.getOwner().equals(p))
                .collect(Collectors.toList());
    }

    private List<Cell> getAllCells() {
        return this.cells.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void placeUnit(int x, int y, Unit u) {
        this.getCell(x, y).setUnit(u);
    }

    public void setCell(int x, int y, Cell c) {
        this.cells.get(y).set(x, c);
    }

    public Cell getCell(int x, int y) {
        return this.cells.get(y).get(x);
    }

    public Cell getCell(Position p) {
        return this.cells.get(p.x).get(p.y);
    }

    public boolean canAttack(Position source, Position target) { //TODO - can only play in your turn
        Cell s = this.getCell(source);
        Cell t = this.getCell(target);
        if (!s.getOwner().equals(t.getOwner()) && s.hasOwner() && t.hasOwner() && s.getUnit().isReadyToAttack()) {
            Unit su = s.getUnit();
            Unit tu = t.getUnit();
            boolean inAttackRange = su.inAttackRange(source, target);
            boolean canAttackType = su.canAttackType(tu);
            return inAttackRange & canAttackType;
        }
        return false;
    }

    public boolean attack(Position source, Position target) {
        if (this.canAttack(source, target)) {
            Cell s = this.getCell(source);
            Cell t = this.getCell(target);
            s.attack(t);
            return true;
        }
        return false;
    }

    public Stack<Position> getAdjacentCells(Position p){
        var r = new Stack<Position>();
        if (p.x-1 >= 0) {
            r.push(new Position(p.x-1, p.y));
        }
        if (p.x+1 < this.width) {
            r.push(new Position(p.x+1, p.y));
        }
        if (p.y-1 >= 0) {
            r.push(new Position(p.x, p.y-1));
        }
        if (p.y+1 < this.height) {
            r.push(new Position(p.x, p.y+1));
        }
        return r;
    }

    public void print() {
        for (ArrayList<Cell> row : this.cells) {
            for (Cell c : row) {
                String symbol = c.getUnit() != null ? c.getUnit().getUnitName().toString().substring(0, 1) : " "; //String.valueOf(c.getMoveCost());
                System.out.printf(" %1$3s", symbol);
            }
            System.out.println("");
        }
    }

    public boolean move(Position source, Position target) {
        Cell s = this.getCell(source);
        Cell t = this.getCell(target);
        if (!t.hasOwner() && s.isReady()) {
            Set<Position> possibleMoves = s.getUnit().getPossibleMoves(this, source);
            if (possibleMoves.contains(target)) {
                Unit u = s.getUnit();
                log.info(source.toString() + "->" + target.toString());
                assert u != null;
                t.setUnit(u);
                s.setUnit(null);
                u.setStatus(Status.MOVED);
                return true;
            }else{
                log.info("Unit can't move there");
            }
        }else{
            log.info("Unit status is: " + s.getUnit().getStatus());
        }
        return false;
    }
}