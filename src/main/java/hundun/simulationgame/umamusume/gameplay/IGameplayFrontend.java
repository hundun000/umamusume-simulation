package hundun.simulationgame.umamusume.gameplay;

import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;

/**
 * @author hundun
 * Created on 2023/01/03
 */
public interface IGameplayFrontend {

    void log(String tag, String message);
    
    // ------ 需要Frontend实现业务逻辑 ------
    void saveCurrent();
    
    // ------ 业务逻辑已在Manager实现，通知Frontend以进行UI逻辑 ------
    void notifiedModifiedResourceNum(Map<GameResourceType, Long> map, boolean plus);
    void notifiedReplayRaceRecord();
    void notifiedCheckUi();
    void notifiedHorseStatusChange(HorsePrototype horsePrototype, String trainDescription, List<GameResourcePair> gainList);

    

    
    

}
