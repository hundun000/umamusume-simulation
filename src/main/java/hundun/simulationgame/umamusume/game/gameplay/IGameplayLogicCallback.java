package hundun.simulationgame.umamusume.game.gameplay;

import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourceType;

/**
 * @author hundun
 * Created on 2023/01/03
 */
public interface IGameplayLogicCallback {

    void log(String tag, String message);
    
    // ------ 业务逻辑已在Manager实现，Callback以进行UI逻辑及项目自定义更多业务逻辑（例如notifiedChangeOperationBoardState时saveGame） ------
    void notifiedModifiedResourceNum(Map<GameResourceType, Long> map, boolean plus);
    void notifiedChangeOperationBoardState();
    void notifiedHorseStatusChange(HorsePrototype horsePrototype, List<GameResourcePair> gainList);

    

    
    

}
