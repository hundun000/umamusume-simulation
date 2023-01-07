package hundun.simulationgame.umamusume.game.gameplay.data;

import java.util.Arrays;
import java.util.List;

/**
 * @author hundun
 * Created on 2023/01/03
 */
public enum GameResourceType {
    TURN,
    COIN,
    HORSE_SPEED,
    HORSE_STAMINA,
    HORSE_POWER,
    ;
    
    public static final List<GameResourceType> VALUES_FOR_SHOW_ORDER = Arrays.asList(TURN, COIN);
}
