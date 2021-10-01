package hundun.simulationgame.umamusume.horse;

import java.util.Map;

import hundun.simulationgame.umamusume.race.DistanceAptitudeType;
import hundun.simulationgame.umamusume.race.RaceLengthType;
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
    private Map<RaceLengthType, DistanceAptitudeType> distanceAptitudes;
}
