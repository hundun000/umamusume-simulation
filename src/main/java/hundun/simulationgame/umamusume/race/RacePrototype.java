package hundun.simulationgame.umamusume.race;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/10/08
 */
@Data
public class RacePrototype {
    private String name;
    private RaceLengthType lengthType;
    private TrackGroundType groundType;
    private int length;
}
