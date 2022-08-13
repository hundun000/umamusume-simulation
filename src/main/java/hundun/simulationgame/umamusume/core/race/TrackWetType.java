package hundun.simulationgame.umamusume.core.race;

import java.util.Map;

import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;


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

    final Map<TrackGroundType, Integer> speedOffSetMap;

    final Map<TrackGroundType, Integer> powerOffSetMap;

    final Map<TrackGroundType, Double> hpRateMap;

    
    private TrackWetType(int turfOffSet1, int turfOffSet2, double turfRate, int dirtOffSet1,
            int dirtOffSet2, double dirtRate) {
        
        this.speedOffSetMap = JavaFeatureForGwt.mapOf(TrackGroundType.TURF, turfOffSet1, TrackGroundType.DIRT, dirtOffSet1);
        this.powerOffSetMap = JavaFeatureForGwt.mapOf(TrackGroundType.TURF, turfOffSet2, TrackGroundType.DIRT, dirtOffSet2);
        this.hpRateMap = JavaFeatureForGwt.mapOf(TrackGroundType.TURF, turfRate, TrackGroundType.DIRT, dirtRate);
    }


    public Map<TrackGroundType, Integer> getSpeedOffSetMap() {
        return speedOffSetMap;
    }


    public Map<TrackGroundType, Integer> getPowerOffSetMap() {
        return powerOffSetMap;
    }


    public Map<TrackGroundType, Double> getHpRateMap() {
        return hpRateMap;
    }
    

    
    
}
