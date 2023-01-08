package hundun.simulationgame.umamusume.game.gameplay.data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2022/12/30
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountSaveData<T_FRAME_DATA> {
    public String id;
    
    public OperationBoardState state;
    public HorsePrototype playerHorse;
    Map<GameResourceType, Long> ownResoueces;
    
    public List<RecordNode<T_FRAME_DATA>> currentRaceRecordNodes;
    public List<EndRecordHorseInfo> sortedRaceEndRecordNode;
    
    /**
     * 决定了允许哪些操作类型
     */
    public static enum OperationBoardState {
        /**
         * 允许训练类操作
         */
        TRAIN_DAY,
        /**
         * 允许开始比赛/放弃比赛
         */
        RACE_DAY_RACE_READY,
        /**
         * 允许重放/结束今日
         */
        RACE_DAY_RACE_HAS_RESULT_RECORD
        ;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResourcePair {
        String type;
        Long amount;
    }
    
}
