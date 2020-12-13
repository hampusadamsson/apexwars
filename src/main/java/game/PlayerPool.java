package game;

import domain.general.Pid;
import domainfactory.PlayerFactory;
import game.playerinput.Player;
import game.playerinput.PlayerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayerPool {
    Pid currentPlayer = Pid.P1;
    HashMap<Pid, Player> playerPool = new HashMap<>();

    public static PlayerPool builder() {
        return new PlayerPool();
    }

    public void changeTurn(){
        Pid current = this.currentPlayer;
        List<Pid> playerPool = Arrays.asList(Pid.values());
        int currentId = playerPool.indexOf(current);
        int nextId = (currentId + 1) % playerPool.size();
        this.currentPlayer = playerPool.get(nextId);
    }

    public PlayerPool addPlayer(Pid pid, PlayerType pt){
        this.playerPool.put(pid, PlayerFactory.create(pid, pt));
        return this;
    }

    public Player get(Pid p) {
        return this.playerPool.get(p);
    }

    public Collection<Player> allPlayers() {
        return this.playerPool.values();
    }

    public boolean validPlayerPool() {
        return this.playerPool.values().size() > 1;
    };
}
