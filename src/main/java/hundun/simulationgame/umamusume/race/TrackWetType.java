package hundun.simulationgame.umamusume.race;

import java.util.Map;

import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/10/08
 */
public enum TrackWetType {
    GOOD(0, 0, 1.0, 0, -100, 1.0),
    SLIGHTLY_HEAVY(0, -50, 1.0, 0, -50, 1.0),
    HEAVY(0, -50, 1.02, 0, -100, 1.01),
    BAD(-50, -50, 1.02, 0, -100, 1.02),
    ;
    @Getter
    final Map<TrackGroundType, Integer> speedOffSetMap;
    @Getter
    final Map<TrackGroundType, Integer> powerOffSetMap;
    @Getter
    final Map<TrackGroundType, Double> hpRateMap;

    
    private TrackWetType(int turfOffSet1, int turfOffSet2, double turfRate, int dirtOffSet1,
            int dirtOffSet2, double dirtRate) {
        
        this.speedOffSetMap = Map.of(TrackGroundType.TURF, turfOffSet1, TrackGroundType.DIRT, dirtOffSet1);
        this.powerOffSetMap = Map.of(TrackGroundType.TURF, turfOffSet2, TrackGroundType.DIRT, dirtOffSet2);
        this.hpRateMap = Map.of(TrackGroundType.TURF, turfRate, TrackGroundType.DIRT, dirtRate);
    }
    

    
    
}
