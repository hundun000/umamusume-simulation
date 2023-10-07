package hundun.simulationgame.umamusume.game.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourceType;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainRuleConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.TurnConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;
import hundun.simulationgame.umamusume.game.nogameplay.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.record.text.TextFrameData;

/**
 * @author hundun
 * Created on 2023/01/03
 */
public class UmaSaveDataFactory {
    static final int SAME_GUTS = 400;
    static final int SAME_WISDOM = 200;

    public static final String YOUR_HORSE_NAME = "playerHorse";
    public static final String RIVAL_HORSE_NAME_START = "rival";

    public static int rivalHorseIdCount = 1;
    static RunStrategyType[] rivalRunStrategyTypes = new RunStrategyType[] {
            RunStrategyType.FIRST, 
            RunStrategyType.FRONT, 
            RunStrategyType.BACK, 
            RunStrategyType.TAIL
            };
    public static String[] RACE_NAMES = new String[] {
            "ShortRace",
            "MileRace",
            "MediumRaceA",
            "MediumRaceB",
            "LongRace"
    };


    public static RacePrototype raceTemplate(int rand) {
        RacePrototype racePrototype;
        racePrototype = new RacePrototype();
        racePrototype.setGroundType(TrackGroundType.TURF);
        racePrototype.setDefaultHorseNum(5);
        
        switch (rand) {
            default:
            case 0:
                racePrototype.setName(RACE_NAMES[0]);
                racePrototype.setLength(1200);
                racePrototype.setLengthType(RaceLengthType.SHORT);
                break;
            case 1:
                racePrototype.setName(RACE_NAMES[1]);
                racePrototype.setLength(1600);
                racePrototype.setLengthType(RaceLengthType.MILE);
                break;
            case 2:
                racePrototype.setName(RACE_NAMES[2]);
                racePrototype.setLength(2000);
                racePrototype.setLengthType(RaceLengthType.MEDIUM);
                break;
            case 3:
                racePrototype.setName(RACE_NAMES[3]);
                racePrototype.setLength(2400);
                racePrototype.setLengthType(RaceLengthType.MEDIUM);
                break;
            case 4:
                racePrototype.setName(RACE_NAMES[4]);
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
            horsePrototype.setName(RIVAL_HORSE_NAME_START + (rivalHorseIdCount++));
            horsePrototype.setBaseSpeed(500 + (int)(rivalValueAddition * (1)));
            horsePrototype.setBaseStamina(400 + (int)(rivalValueAddition * (1)));
            horsePrototype.setBasePower(400 + (int)(rivalValueAddition * (1)));
            horsePrototype.setBaseGuts(SAME_GUTS);
            horsePrototype.setBaseWisdom(SAME_WISDOM);
            horsePrototype.setDefaultRunStrategyType(rivalRunStrategyTypes[i]);
            HorsePrototypeFactory.fillDefaultFields(horsePrototype);
            //horsePrototype.setCharImage("Rival" + (i + 1));
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
            int numRace = 5;
            int raceTurnStep = 3;
            for (int i = 1; i < numRace; i++) {
                int raceTurn = i* raceTurnStep;
                int raceRand = (int) (Math.random() * 5);
                int rivalValueAddition = i * 40;
                gameRuleData.turnConfigMap.put(raceTurn, turnConfigTemplate(raceRand, rivalValueAddition));
            }
        } 
        gameRuleData.trainRuleConfigMap = new HashMap<>();
        gameRuleData.trainRuleConfigMap.put(
                TrainActionType.RUNNING_TRAIN, 
                TrainRuleConfig.builder()
                        .costList(JavaFeatureForGwt.arraysAsList(
                                new GameResourcePair(GameResourceType.COIN, 100L)
                                ))
                        .gainList(JavaFeatureForGwt.arraysAsList(
                                new GameResourcePair(GameResourceType.HORSE_SPEED, 25L),
                                new GameResourcePair(GameResourceType.HORSE_POWER, 10L)
                                ))
                        .build()
                );
        gameRuleData.trainRuleConfigMap.put(
                TrainActionType.SWIMMING_TRAIN, 
                TrainRuleConfig.builder()
                        .costList(JavaFeatureForGwt.arraysAsList(
                                new GameResourcePair(GameResourceType.COIN, 100L)
                                ))
                        .gainList(JavaFeatureForGwt.arraysAsList(
                                new GameResourcePair(GameResourceType.HORSE_STAMINA, 25L),
                                new GameResourcePair(GameResourceType.HORSE_POWER, 10L)
                                ))
                        .build()
                );
        gameRuleData.trainRuleConfigMap.put(
                TrainActionType.POWER_TRAIN, 
                TrainRuleConfig.builder()
                        .costList(JavaFeatureForGwt.arraysAsList(
                                new GameResourcePair(GameResourceType.COIN, 100L)
                                ))
                        .gainList(JavaFeatureForGwt.arraysAsList(
                                new GameResourcePair(GameResourceType.HORSE_SPEED, 5L),
                                new GameResourcePair(GameResourceType.HORSE_STAMINA, 5L),
                                new GameResourcePair(GameResourceType.HORSE_POWER, 25L)
                                ))
                        .build()
                );
        gameRuleData.trainRuleConfigMap.put(
                TrainActionType.FREE_TRAIN, 
                TrainRuleConfig.builder()
                        .costList(new ArrayList<>(0))
                        .gainList(JavaFeatureForGwt.arraysAsList(
                                new GameResourcePair(GameResourceType.HORSE_SPEED, 5L),
                                new GameResourcePair(GameResourceType.HORSE_STAMINA, 5L),
                                new GameResourcePair(GameResourceType.HORSE_POWER, 5L)
                                ))
                        .build()
                );
        return gameRuleData;
    }
    
    public static <T> AccountSaveData<T> forNewAccount(String id) {

        AccountSaveData<T> umaSaveData = new AccountSaveData<T>();
        umaSaveData.state = OperationBoardState.TRAIN_DAY;
        umaSaveData.id = id;
        {
            HorsePrototype horsePrototype;
            horsePrototype = new HorsePrototype();
            horsePrototype.setName(YOUR_HORSE_NAME);
            horsePrototype.setBaseSpeed((int) (700));
            horsePrototype.setBaseStamina((int) (500));
            horsePrototype.setBasePower((int) (500));
            horsePrototype.setBaseGuts(SAME_GUTS);
            horsePrototype.setBaseWisdom(SAME_WISDOM);
            horsePrototype.setDefaultRunStrategyType(RunStrategyType.FRONT);
            HorsePrototypeFactory.fillDefaultFields(horsePrototype);
            //horsePrototype.setCharImage("USELESS");
            umaSaveData.playerHorse = horsePrototype;
        }    
        umaSaveData.setOwnResoueces(new HashMap<>());
        umaSaveData.getOwnResoueces().put(GameResourceType.TURN, 1L);
        umaSaveData.getOwnResoueces().put(GameResourceType.COIN, 100L);
        
        
        return umaSaveData;
    }
}
