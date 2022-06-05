package hundun.simulationgame.umamusume.horse;

import java.util.Map;

import hundun.simulationgame.umamusume.race.RaceLengthType;
import hundun.simulationgame.umamusume.race.TrackGroundType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/09/25
 */
@Data
public class HorsePrototype {
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
