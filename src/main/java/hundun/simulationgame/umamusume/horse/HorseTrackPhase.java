package hundun.simulationgame.umamusume.horse;

import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public enum HorseTrackPhase {
    START_GATE("出闸"),
    START_CRUISE("初期巡航"),
    MIDDLE_CRUISE("中期巡航"),
    LAST_CRUISE("末期巡航"),
    LAST_SPRINT("末期冲刺"),
    REACHED("冲线"),
    ;
    @Getter
    String chinese;
    HorseTrackPhase(String chinese) {
        this.chinese = chinese;
    }
}
