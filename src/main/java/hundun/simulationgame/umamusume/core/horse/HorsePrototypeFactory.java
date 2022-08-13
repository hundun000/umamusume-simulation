package hundun.simulationgame.umamusume.core.horse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class HorsePrototypeFactory {
    
    public static HorsePrototype SILENCE_SUZUKA_S;
    public static HorsePrototype SILENCE_SUZUKA_A;
    public static HorsePrototype SILENCE_SUZUKA_B;
    public static HorsePrototype SPECIAL_WEEK_A;
    public static HorsePrototype GRASS_WONDER_A;
    public static HorsePrototype GOLDEN_SHIP_A;
    
    private Random rand = new Random();
    private List<HorsePrototype> horsePrototypes = new ArrayList<>();
    
    
    private static Map<RaceLengthType, DistanceAptitudeType> defaultDistanceAptitudes() {
        Map<RaceLengthType, DistanceAptitudeType> map = new HashMap<>();
        for (RaceLengthType type : RaceLengthType.values()) {
            map.put(type, DistanceAptitudeType.A);
        }
        return map;
    }
    
    private static Map<RunStrategyType, RunStrategyAptitudeType> defaultRunStrategyAptitudes() {
        Map<RunStrategyType, RunStrategyAptitudeType> map = new HashMap<>();
        for (RunStrategyType type : RunStrategyType.values()) {
            map.put(type, RunStrategyAptitudeType.A);
        }
        return map;
    }
    
    private static Map<TrackGroundType, TrackGroundAptitudeType> defaultTrackGroundAptitudes() {
        Map<TrackGroundType, TrackGroundAptitudeType> map = new HashMap<>();
        for (TrackGroundType type : TrackGroundType.values()) {
            map.put(type, TrackGroundAptitudeType.A);
        }
        return map;
    }
    
    public void registerAllDefault() {
        register(SILENCE_SUZUKA_S);
        register(SILENCE_SUZUKA_A);
        register(SILENCE_SUZUKA_B);
        register(SPECIAL_WEEK_A);
        register(GRASS_WONDER_A);
        register(GOLDEN_SHIP_A);
    }
    
    public void register(HorsePrototype horsePrototype) {
        horsePrototypes.add(horsePrototype);
    }
    

    public List<HorsePrototype> getRandomRivals(int num, HorsePrototype base, double offsetRate) {
        if (horsePrototypes.size() < num) {
            throw new UnsupportedOperationException("getRandomRivals 不应大于  size = " + horsePrototypes.size());
        }
        List<HorsePrototype> result = new ArrayList<>();
        while (result.size() < num) {
            HorsePrototype nearScoreResult = getRandomNearScore(base.getScore(), offsetRate, result, base);
            if (nearScoreResult != null) {
                result.add(nearScoreResult);
            } else {
                HorsePrototype noLimitScoreResult = getRandomNearScore(null, null, result, base);
                result.add(noLimitScoreResult);
            }
        }
        return result;
        
    }
    
    public HorsePrototype getRandomNearScore(Integer targetScore, Double offsetRate, List<HorsePrototype> excludes, HorsePrototype moreExclude) { 
        List<HorsePrototype> list = horsePrototypes.stream()
                .filter(entry -> {
                    if (targetScore == null) {
                        return true;
                    }
                    double floor = targetScore * (1 - offsetRate);
                    double ceil = targetScore * (1 + offsetRate);
                    return entry.getScore() > floor && entry.getScore() < ceil;
                })
                .filter(entry -> (excludes != null && !excludes.contains(entry)) && moreExclude != entry)
                .collect(Collectors.toList())
                ;
        return list.isEmpty() ? null : list.get(rand.nextInt(list.size()));
    }
    
    public static int calculateScore(HorsePrototype horsePrototype) {
        return horsePrototype.getBaseSpeed() 
                + horsePrototype.getBasePower()
                + horsePrototype.getBaseStamina()
                + horsePrototype.getBaseGuts()
                + horsePrototype.getBaseWisdom();
    }
    
    public static void fillDefaultFields(HorsePrototype horsePrototype) {
        horsePrototype.setCharImage("馬" + horsePrototype.getName());
        horsePrototype.setScore(calculateScore(horsePrototype));
        horsePrototype.setDistanceAptitudes(defaultDistanceAptitudes());
        horsePrototype.setDistanceAptitudes(defaultDistanceAptitudes());
        horsePrototype.setRunStrategyAptitudes(defaultRunStrategyAptitudes());
        horsePrototype.setTrackGroundAptitudes(defaultTrackGroundAptitudes());
    }
    
    static {
        SILENCE_SUZUKA_S = new HorsePrototype();
        SILENCE_SUZUKA_S.setName("无声铃鹿S");
        SILENCE_SUZUKA_S.setBaseSpeed(1200);
        SILENCE_SUZUKA_S.setBaseStamina(600);
        SILENCE_SUZUKA_S.setBasePower(800);
        SILENCE_SUZUKA_S.setBaseGuts(800);
        SILENCE_SUZUKA_S.setBaseWisdom(300);
        SILENCE_SUZUKA_S.setDefaultRunStrategyType(RunStrategyType.FIRST);
        fillDefaultFields(SILENCE_SUZUKA_S);
        
        SILENCE_SUZUKA_A = new HorsePrototype();
        SILENCE_SUZUKA_A.setName("无声铃鹿A");
        SILENCE_SUZUKA_A.setBaseSpeed(800);
        SILENCE_SUZUKA_A.setBaseStamina(400);
        SILENCE_SUZUKA_A.setBasePower(600);
        SILENCE_SUZUKA_A.setBaseGuts(600);
        SILENCE_SUZUKA_A.setBaseWisdom(200);
        SILENCE_SUZUKA_A.setDefaultRunStrategyType(RunStrategyType.FIRST);
        fillDefaultFields(SILENCE_SUZUKA_A);
        
        SILENCE_SUZUKA_B = new HorsePrototype();
        SILENCE_SUZUKA_B.setName("无声铃鹿B");
        SILENCE_SUZUKA_B.setBaseSpeed(600);
        SILENCE_SUZUKA_B.setBaseStamina(100);
        SILENCE_SUZUKA_B.setBasePower(300);
        SILENCE_SUZUKA_B.setBaseGuts(300);
        SILENCE_SUZUKA_B.setBaseWisdom(100);
        SILENCE_SUZUKA_B.setDefaultRunStrategyType(RunStrategyType.FIRST);
        fillDefaultFields(SILENCE_SUZUKA_B);
        
        SPECIAL_WEEK_A = new HorsePrototype();
        SPECIAL_WEEK_A.setName("特别周A");
        SPECIAL_WEEK_A.setBaseSpeed(800);
        SPECIAL_WEEK_A.setBaseStamina(400);
        SPECIAL_WEEK_A.setBasePower(600);
        SPECIAL_WEEK_A.setBaseGuts(600);
        SPECIAL_WEEK_A.setBaseWisdom(200);
        SPECIAL_WEEK_A.setDefaultRunStrategyType(RunStrategyType.FRONT);
        fillDefaultFields(SPECIAL_WEEK_A);
        
        GRASS_WONDER_A = new HorsePrototype();
        GRASS_WONDER_A.setName("草上飞A");
        GRASS_WONDER_A.setBaseSpeed(800);
        GRASS_WONDER_A.setBaseStamina(400);
        GRASS_WONDER_A.setBasePower(600);
        GRASS_WONDER_A.setBaseGuts(600);
        GRASS_WONDER_A.setBaseWisdom(200);
        GRASS_WONDER_A.setDefaultRunStrategyType(RunStrategyType.BACK);
        fillDefaultFields(GRASS_WONDER_A);
        
        GOLDEN_SHIP_A = new HorsePrototype();
        GOLDEN_SHIP_A.setName("黄金船A");
        GOLDEN_SHIP_A.setBaseSpeed(800);
        GOLDEN_SHIP_A.setBaseStamina(400);
        GOLDEN_SHIP_A.setBasePower(600);
        GOLDEN_SHIP_A.setBaseGuts(600);
        GOLDEN_SHIP_A.setBaseWisdom(200);
        GOLDEN_SHIP_A.setDefaultRunStrategyType(RunStrategyType.TAIL);
        fillDefaultFields(GOLDEN_SHIP_A);
    }

}
