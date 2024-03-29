package hundun.simulationgame.umamusume.core.horse;

import java.util.Map;

import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/09/25
 */
@Data
public class HorsePrototype {
    /**
     * 具体项目可约定name不重复，则name可作为id。
     */
    private String name;
    private int baseSpeed;
    private int baseStamina;
    private int basePower;
    private int baseGuts;
    private int baseWisdom;
    private int score;
    private RunStrategyType defaultRunStrategyType;
    private Map<RaceLengthType, DistanceAptitudeType> distanceAptitudes;
    private Map<RunStrategyType, RunStrategyAptitudeType> runStrategyAptitudes;
    private Map<TrackGroundType, TrackGroundAptitudeType> trackGroundAptitudes;
    
}
