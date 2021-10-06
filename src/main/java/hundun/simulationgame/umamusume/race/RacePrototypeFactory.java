package hundun.simulationgame.umamusume.race;
/**
 * @author hundun
 * Created on 2021/10/08
 */
public class RacePrototypeFactory {
    public static RacePrototype OKA_SHO;
    
    
    static {
        RacePrototype prototype;
        
        prototype = new RacePrototype();
        prototype.setName("樱花赏");
        prototype.setGroundType(TrackGroundType.TURF);
        prototype.setLength(1600);
        prototype.setLengthType(RaceLengthType.MILE);
        OKA_SHO = prototype;
    }
}
