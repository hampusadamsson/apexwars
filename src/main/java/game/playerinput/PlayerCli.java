package game.playerinput;

import domain.general.Pid;
import domain.terrain.Map;
import domain.terrain.cells.Cell;
import domain.terrain.cells.Position;
import lombok.Data;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PlayerCli implements Player {
    Pid pid;
    static final String EXIT = "e";
    static final String PRINT = "p";
    static final String GET_UNITS = "g";
    static final String MOVE = "m";
    static final String ATTACK  = "a";
    static final String BACK  = "b";

    public PlayerCli(Pid pid) {
        this.pid = pid;
    }

    @Override
    public void play(Map map) {
        String option = "";
        while (!option.equals(EXIT)) {
            option = this.selectOption(map);
        }
    }

    public String selectOption(Map map) {
        Scanner inp = new Scanner(System.in);
        System.out.println("(e)nd turn");
        System.out.println("(p)rint map");
        System.out.println("(g)et units");
        String option = inp.nextLine();
        switch (option) {
            case PRINT -> map.print();
            case EXIT -> System.out.println("Ending your turn");
            case GET_UNITS -> {
                Cell cell = this.selectUnit(map);
                this.actOnCell(map, cell);
            }
            default -> System.out.println("Illegal character");
        }
        return option;
    }

    private void actOnCell(Map map, Cell cell) {
        Scanner inp = new Scanner(System.in);
        System.out.println("(p)rint");
        System.out.println("(m)ove");
        System.out.println("(a)ttack");
        String option = inp.nextLine();
        switch (option) {
            case PRINT -> map.print();
            case MOVE -> this.move(map, cell);
            case ATTACK -> this.attack(map, cell);
            default -> System.out.println("Illegal character");
        }
    }

    public void move(Map map, Cell cell){
        Scanner inp = new Scanner(System.in);
        Set<Position> possibleMoves = cell.getUnit().getPossibleMoves(map, cell.getPosition());
        System.out.println(possibleMoves.toString());
        System.out.print("x:");
        int x = inp.nextInt();
        System.out.print("y:");
        int y = inp.nextInt();
        boolean move = map.move(cell.getPosition(), new Position(x, y));
        if (!move) {
            System.out.println("Illegal move");
        }
    }

    public void attack(Map map, Cell cell){
        Scanner inp = new Scanner(System.in);
        List<Cell> enemyUnitCells = map.getEnemyUnitCells(this.pid);
        List<Position> possibleTargets = enemyUnitCells.stream()
                .map(Cell::getPosition)
                .filter(p -> map.canAttack(cell.getPosition(), p))
                .collect(Collectors.toList());
        System.out.println(possibleTargets.toString());
        System.out.print("x:");
        int x = inp.nextInt();
        System.out.print("y:");
        int y = inp.nextInt();
        Position targetPos = new Position(x, y);
        boolean attack = map.attack(cell.getPosition(), targetPos);
        if (!attack) {
            System.out.println("Can't attack " + targetPos.toString());
        }
    }


    private Cell selectUnit(Map map) {
        int i = 0;
        System.out.println("ID :: Unit :: Position");
        for (Cell c : map.getUnitCells(this.pid)) {
            System.out.println(MessageFormat.format("({0}) :: {1} :: {2}", Integer.toString(i), c.getUnit().getUnitName().toString().substring(0, 1), c.getPosition()));
            i++;
        }
        System.out.print("Select a unit (ID): ");
        Scanner inp = new Scanner(System.in);
        int unitId = inp.nextInt();

        try {
            Cell selected = map.getUnitCells(this.pid).get(unitId);
            System.out.println("You selected " + unitId + " :: " + selected.getUnit().getUnitName());
            System.out.println("");
            return selected;
        }catch (Exception e){
            System.out.println("Can't get ID: " + unitId);
            return null;
        }
    }


}
