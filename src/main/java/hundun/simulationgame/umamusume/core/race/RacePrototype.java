package hundun.simulationgame.umamusume.core.race;

import lombok.Data;

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
    private int defaultHorseNum;
}
