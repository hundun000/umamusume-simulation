package hundun.simulationgame.umamusume.core.race;
/**
 * @author hundun
 * Created on 2021/10/08
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;

public class RacePrototypeFactory {
    public static RacePrototype OKA_SHO;
    
    private Random rand = new Random();
    Map<String, RacePrototype> racePrototypes = new HashMap<>();
    
    static {
        RacePrototype prototype;
        
        prototype = new RacePrototype();
        prototype.setName("樱花赏");
        prototype.setGroundType(TrackGroundType.TURF);
        prototype.setLength(1600);
        prototype.setLengthType(RaceLengthType.MILE);
        prototype.setDefaultHorseNum(4);
        OKA_SHO = prototype;
    }
    
    public void registerAllDefault() {
        register(OKA_SHO);
    }
    
    public void register(RacePrototype racePrototype) {
        racePrototypes.put(racePrototype.getName(), racePrototype);
    }
    
    public RacePrototype get(String name) {
        return racePrototypes.get(name);
    }
    
    public RacePrototype getRandom() {
        List<RacePrototype> list = new ArrayList<>(racePrototypes.values());
        return list.isEmpty() ? null : list.get(rand.nextInt(list.size()));
    }
}
