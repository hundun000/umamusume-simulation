package hundun.simulationgame.umamusume.race;

/**
 * @author hundun
 * Created on 2021/10/08
 */

public class RacePrototype {
    private String name;
    private RaceLengthType lengthType;
    private TrackGroundType groundType;
    private int length;
    private int defaultHorseNum;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public RaceLengthType getLengthType() {
        return lengthType;
    }
    public void setLengthType(RaceLengthType lengthType) {
        this.lengthType = lengthType;
    }
    public TrackGroundType getGroundType() {
        return groundType;
    }
    public void setGroundType(TrackGroundType groundType) {
        this.groundType = groundType;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getDefaultHorseNum() {
        return defaultHorseNum;
    }
    public void setDefaultHorseNum(int defaultHorseNum) {
        this.defaultHorseNum = defaultHorseNum;
    }
    
    
}
