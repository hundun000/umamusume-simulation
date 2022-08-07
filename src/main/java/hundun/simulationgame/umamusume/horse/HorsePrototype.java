package hundun.simulationgame.umamusume.horse;

import java.util.Map;

import hundun.simulationgame.umamusume.race.RaceLengthType;
import hundun.simulationgame.umamusume.race.TrackGroundType;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class HorsePrototype {
    /**
     * 具体项目可约定name不重复，则name可作为id。
     */
    private String name;
    /**
     * 作为字符画时的形象（可用emoji？）
     */
    private String charImage;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCharImage() {
        return charImage;
    }
    public void setCharImage(String charImage) {
        this.charImage = charImage;
    }
    public int getBaseSpeed() {
        return baseSpeed;
    }
    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }
    public int getBaseStamina() {
        return baseStamina;
    }
    public void setBaseStamina(int baseStamina) {
        this.baseStamina = baseStamina;
    }
    public int getBasePower() {
        return basePower;
    }
    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }
    public int getBaseGuts() {
        return baseGuts;
    }
    public void setBaseGuts(int baseGuts) {
        this.baseGuts = baseGuts;
    }
    public int getBaseWisdom() {
        return baseWisdom;
    }
    public void setBaseWisdom(int baseWisdom) {
        this.baseWisdom = baseWisdom;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public RunStrategyType getDefaultRunStrategyType() {
        return defaultRunStrategyType;
    }
    public void setDefaultRunStrategyType(RunStrategyType defaultRunStrategyType) {
        this.defaultRunStrategyType = defaultRunStrategyType;
    }
    public Map<RaceLengthType, DistanceAptitudeType> getDistanceAptitudes() {
        return distanceAptitudes;
    }
    public void setDistanceAptitudes(Map<RaceLengthType, DistanceAptitudeType> distanceAptitudes) {
        this.distanceAptitudes = distanceAptitudes;
    }
    public Map<RunStrategyType, RunStrategyAptitudeType> getRunStrategyAptitudes() {
        return runStrategyAptitudes;
    }
    public void setRunStrategyAptitudes(Map<RunStrategyType, RunStrategyAptitudeType> runStrategyAptitudes) {
        this.runStrategyAptitudes = runStrategyAptitudes;
    }
    public Map<TrackGroundType, TrackGroundAptitudeType> getTrackGroundAptitudes() {
        return trackGroundAptitudes;
    }
    public void setTrackGroundAptitudes(Map<TrackGroundType, TrackGroundAptitudeType> trackGroundAptitudes) {
        this.trackGroundAptitudes = trackGroundAptitudes;
    }
    
    
}
