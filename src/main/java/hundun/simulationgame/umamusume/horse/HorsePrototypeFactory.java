package hundun.simulationgame.umamusume.horse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import hundun.simulationgame.umamusume.race.DistanceAptitudeType;
import hundun.simulationgame.umamusume.race.RaceLengthType;

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
    
    private static Map<RaceLengthType, DistanceAptitudeType> defaultDistanceAptitudes() {
        Map<RaceLengthType, DistanceAptitudeType> map = new HashMap<>();
        for (RaceLengthType lengthType : RaceLengthType.values()) {
            map.put(lengthType, DistanceAptitudeType.A);
        }
        return map;
    }
    
    static {
        SILENCE_SUZUKA_S = new HorsePrototype();
        SILENCE_SUZUKA_S.setName("无声铃鹿S");
        SILENCE_SUZUKA_S.setBaseSpeed(1200);
        SILENCE_SUZUKA_S.setBaseStamina(600);
        SILENCE_SUZUKA_S.setBasePower(800);
        SILENCE_SUZUKA_S.setBaseGuts(800);
        SILENCE_SUZUKA_S.setBaseWisdom(300);
        SILENCE_SUZUKA_S.setDistanceAptitudes(defaultDistanceAptitudes());
        
        SILENCE_SUZUKA_A = new HorsePrototype();
        SILENCE_SUZUKA_A.setName("无声铃鹿A");
        SILENCE_SUZUKA_A.setBaseSpeed(800);
        SILENCE_SUZUKA_A.setBaseStamina(400);
        SILENCE_SUZUKA_A.setBasePower(600);
        SILENCE_SUZUKA_A.setBaseGuts(600);
        SILENCE_SUZUKA_A.setBaseWisdom(200);
        SILENCE_SUZUKA_A.setDistanceAptitudes(defaultDistanceAptitudes());
        
        SILENCE_SUZUKA_B = new HorsePrototype();
        SILENCE_SUZUKA_B.setName("无声铃鹿B");
        SILENCE_SUZUKA_B.setBaseSpeed(600);
        SILENCE_SUZUKA_B.setBaseStamina(100);
        SILENCE_SUZUKA_B.setBasePower(300);
        SILENCE_SUZUKA_B.setBaseGuts(300);
        SILENCE_SUZUKA_B.setBaseWisdom(100);
        SILENCE_SUZUKA_B.setDistanceAptitudes(defaultDistanceAptitudes());
        
        SPECIAL_WEEK_A = new HorsePrototype();
        SPECIAL_WEEK_A.setName("特别周A");
        SPECIAL_WEEK_A.setBaseSpeed(800);
        SPECIAL_WEEK_A.setBaseStamina(400);
        SPECIAL_WEEK_A.setBasePower(600);
        SPECIAL_WEEK_A.setBaseGuts(600);
        SPECIAL_WEEK_A.setBaseWisdom(200);
        
        GRASS_WONDER_A = new HorsePrototype();
        GRASS_WONDER_A.setName("草上飞A");
        GRASS_WONDER_A.setBaseSpeed(800);
        GRASS_WONDER_A.setBaseStamina(400);
        GRASS_WONDER_A.setBasePower(600);
        GRASS_WONDER_A.setBaseGuts(600);
        GRASS_WONDER_A.setBaseWisdom(200);
    }

}
