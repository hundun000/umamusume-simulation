package hundun.simulationgame.umamusume.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData.OperationBoardState;

/**
 * @author hundun
 * Created on 2023/01/03
 */
public class UmaSaveDataFactory {
    static final int SAME_GUTS = 400;
    static final int SAME_WISDOM = 200;
    static RunStrategyType[] rivalRunStrategyTypes = new RunStrategyType[] {
            RunStrategyType.FIRST, 
            RunStrategyType.FRONT, 
            RunStrategyType.BACK, 
            RunStrategyType.TAIL
            };
    
    public static RacePrototype raceTemplate(int rand) {
        RacePrototype racePrototype;
        racePrototype = new RacePrototype();
        racePrototype.setGroundType(TrackGroundType.TURF);
        racePrototype.setDefaultHorseNum(5);
        
        switch (rand) {
            default:
            case 0:
                racePrototype.setName("ShortRace");
                racePrototype.setLength(1200);
                racePrototype.setLengthType(RaceLengthType.SHORT);
                break;
            case 1:
                racePrototype.setName("MileRace");
                racePrototype.setLength(1600);
                racePrototype.setLengthType(RaceLengthType.MILE);
                break;
            case 2:
                racePrototype.setName("MediumRace");
                racePrototype.setLength(2000);
                racePrototype.setLengthType(RaceLengthType.MEDIUM);
                break;
            case 3:
                racePrototype.setName("MediumRace");
                racePrototype.setLength(2400);
                racePrototype.setLengthType(RaceLengthType.MEDIUM);
                break;
            case 4:
                racePrototype.setName("LongRace");
                racePrototype.setLength(2800);
                racePrototype.setLengthType(RaceLengthType.LONG);
                break;
        }
        return racePrototype;
    }
    
    public static TurnConfig turnConfigTemplate(int raceRand, int rivalValueAddition) {
        HorsePrototype horsePrototype;
        TurnConfig turnConfig = new TurnConfig();

        
        RacePrototype racePrototype = raceTemplate(raceRand);
        turnConfig.setRace(racePrototype);
        
        int numRival = turnConfig.getRace().getDefaultHorseNum() - 1;
        turnConfig.setRivalHorses(new ArrayList<>());
        
        for (int i = 0; i < numRival; i++) {
            horsePrototype = new HorsePrototype();
            horsePrototype.setName("rival" + i);
            horsePrototype.setBaseSpeed(500 + (int)(rivalValueAddition * (1)));
            horsePrototype.setBaseStamina(400 + (int)(rivalValueAddition * (1)));
            horsePrototype.setBasePower(400 + (int)(rivalValueAddition * (1)));
            horsePrototype.setBaseGuts(SAME_GUTS);
            horsePrototype.setBaseWisdom(SAME_WISDOM);
            horsePrototype.setDefaultRunStrategyType(rivalRunStrategyTypes[i]);
            HorsePrototypeFactory.fillDefaultFields(horsePrototype);
            horsePrototype.setCharImage("Rival" + (i + 1));
            turnConfig.getRivalHorses().add(horsePrototype);
        }
        
        int maxAward = 100 + 50 * racePrototype.getDefaultHorseNum();
        turnConfig.setRankToAwardMap(new HashMap<>());
        for (int i = 0; i< racePrototype.getDefaultHorseNum(); i++) {
            turnConfig.getRankToAwardMap().put(i, maxAward - (50 * i));
        }
        return turnConfig;
    }
    
    public static GameRuleData forNewGameRuleData() {
        GameRuleData gameRuleData = new GameRuleData();
        gameRuleData.turnConfigMap = new HashMap<>();
        {
            // first race not random
            gameRuleData.turnConfigMap.put(3, turnConfigTemplate(0, 0));
            
            int numRace = 5;
            int raceTurn = 3;
            for (int i = 1; i < numRace; i++) {
                raceTurn += 4 + (int)(Math.random() * 3);
                int raceRand = (int) (Math.random() * 5);
                int rivalValueAddition = i * 40;
                gameRuleData.turnConfigMap.put(raceTurn, turnConfigTemplate(raceRand, rivalValueAddition));
            }
        } 
        return gameRuleData;
    }
    
    public static AccountSaveData forNewAccount(String id) {

        AccountSaveData umaSaveData = new AccountSaveData();
        umaSaveData.state = OperationBoardState.TRAIN_DAY;
        umaSaveData.id = id;
        {
            HorsePrototype horsePrototype;
            horsePrototype = new HorsePrototype();
            horsePrototype.setName("playerHorse");
            horsePrototype.setBaseSpeed((int) (700));
            horsePrototype.setBaseStamina((int) (500));
            horsePrototype.setBasePower((int) (500));
            horsePrototype.setBaseGuts(SAME_GUTS);
            horsePrototype.setBaseWisdom(SAME_WISDOM);
            horsePrototype.setDefaultRunStrategyType(RunStrategyType.FRONT);
            HorsePrototypeFactory.fillDefaultFields(horsePrototype);
            horsePrototype.setCharImage("Your");
            umaSaveData.playerHorse = horsePrototype;
        }    
        umaSaveData.setOwnResoueces(new HashMap<>());
        umaSaveData.getOwnResoueces().put(GameResourceType.TURN, 1L);
        umaSaveData.getOwnResoueces().put(GameResourceType.COIN, 100L);
        
        
        return umaSaveData;
    }
}
