package hundun.simulationgame.umamusume.horse;

import hundun.simulationgame.umamusume.record.IChineseNameEnum;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public enum HorseTrackPhase implements IChineseNameEnum {
    START_GATE("出闸"),
    START_CRUISE("初期巡航"),
    MIDDLE_CRUISE("中期巡航"),
    MIDDLE_CRUISE_HALF("中期巡航(过半)"),
    LAST_CRUISE("末期巡航"),
    LAST_SPRINT("末期冲刺"),
    REACHED("冲线"),
    ;
    
    String chinese;
    HorseTrackPhase(String chinese) {
        this.chinese = chinese;
    }
    
    @Override
    public String getChinese() {
        return chinese;
    }
}
