package hundun.simulationgame.umamusume.game.gameplay.data;

import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import lombok.Data;

/**
 * @author hundun
 * Created on 2023/01/03
 */
@Data
public class GameRuleData {
    public Map<Integer, TurnConfig> turnConfigMap;
    public Map<TrainActionType, TrainRuleConfig> trainRuleConfigMap;
}
