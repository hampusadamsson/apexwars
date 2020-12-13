package domainfactory;

import domain.general.Pid;
import game.playerinput.Player;
import game.playerinput.PlayerAI;
import game.playerinput.PlayerCli;
import game.playerinput.PlayerType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlayerFactory {
    public static Player create(Pid pid, PlayerType pt) {
        return switch (pt) {
            case HUMAN_CLI -> new PlayerCli(pid);
            default -> new PlayerAI(pid);
        };
    }
}
