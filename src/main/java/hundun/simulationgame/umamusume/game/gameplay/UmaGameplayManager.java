package hundun.simulationgame.umamusume.game.gameplay;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.core.race.TrackWetType;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourceType;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainRuleConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.TurnConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import hundun.simulationgame.umamusume.record.text.Translator;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2022/08/02
 */
public class UmaGameplayManager {
    @Getter
    Map<String, AccountSaveData> accountSaveDataMap;
    @Getter
    GameRuleData gameRuleData;
    IGameplayFrontend frontend;
    
    //TurnConfig currentTurnConfig;
    private final IRaceRecorder<TextFrameData> raceRecorder;
    @Getter
    private final Translator translator;

    

    
    public UmaGameplayManager(Translator translator, IRaceRecorder<TextFrameData> raceRecorder, IGameplayFrontend frontend) {
        this.frontend = frontend;
        this.accountSaveDataMap = new HashMap<>();
        
        this.translator = translator;
                
        
        this.raceRecorder = raceRecorder;
    }
    
    

    
    public void applySaveData(Map<String, AccountSaveData> accountSaveDataMap, GameRuleData gameRuleData) {
        this.accountSaveDataMap = accountSaveDataMap;
        this.gameRuleData = gameRuleData;
    }

    
    public AccountSaveData getAccountSaveData(String id) {
        return accountSaveDataMap.get(id);
    }
    public OperationBoardState getOperationBoardState(AccountSaveData accountSaveData) {
        return accountSaveData.state;
    }
    private void setStateAndLog(AccountSaveData accountSaveData, OperationBoardState state) {
        accountSaveData.state = state;
        frontend.log(this.getClass().getSimpleName(), "state change to: " + state);
    }
    

    public void raceStart(AccountSaveData accountSaveData) {
        TurnConfig currentTurnConfig = getCurrentTurnConfig(accountSaveData);
        RaceSituation currentRaceSituation = new RaceSituation(raceRecorder, currentTurnConfig.getRace(), TrackWetType.GOOD);
        HorsePrototype base = accountSaveData.playerHorse;
        
        List<HorsePrototype> randomRivals = currentTurnConfig.getRivalHorses();
        randomRivals.forEach(item -> {
            currentRaceSituation.addHorse(item, item.getDefaultRunStrategyType());
        });
        currentRaceSituation.addHorse(base, base.getDefaultRunStrategyType());
        
        currentRaceSituation.calculateResult();
        frontend.log(this.getClass().getSimpleName(), "Race start and calculateResult done");
        //displayer.printRecordPackage();
        
        setStateAndLog(accountSaveData, OperationBoardState.RACE_DAY_RACE_HAS_RESULT_RECORD);
        accountSaveData.currentRaceRecordNodes = raceRecorder.getRecordPackage().getNodes();
        accountSaveData.sortedRaceEndRecordNode = raceRecorder.getRecordPackage().getEndNode().getHorseInfos().stream()
                .sorted(Comparator.comparing(EndRecordHorseInfo::getReachTick))
                .collect(Collectors.toList())
                ;
        
        frontend.notifiedReplayRaceRecord();
    }
    
    public TurnConfig getCurrentTurnConfig(AccountSaveData accountSaveData) {
        int currentTurn = (int) this.getResourceNumOrZero(accountSaveData, GameResourceType.TURN);
        return gameRuleData.turnConfigMap.get(currentTurn);
    }

    




    public Integer getNextRaceTurnTimeDiff(AccountSaveData accountSaveData) {
        int currentTurn = (int) this.getResourceNumOrZero(accountSaveData, GameResourceType.TURN);
        Integer nextRaceTurn = gameRuleData.turnConfigMap.entrySet().stream()
            .filter(entry -> entry.getKey() > currentTurn)
            .sorted(Comparator.comparing(Entry<Integer, TurnConfig>::getKey))
            .map(entry -> entry.getKey())
            .findFirst()
            .orElseGet(() -> null)
            ;
        return nextRaceTurn != null ? (nextRaceTurn - currentTurn) : null;
    }
    
    private void nextDay(AccountSaveData accountSaveData) {
        this.modifyAllResourceNum(
                accountSaveData,
                JavaFeatureForGwt.mapOf(GameResourceType.TURN, 1L), 
                true
                );
        TurnConfig currentTurnConfig = getCurrentTurnConfig(accountSaveData);
        if (currentTurnConfig == null) {
            setStateAndLog(accountSaveData, OperationBoardState.TRAIN_DAY);
        } else {
            setStateAndLog(accountSaveData, OperationBoardState.RACE_DAY_RACE_READY);
        }
        frontend.notifiedChangeOperationBoardState();
        
    }

    private void trainAndNextDay(AccountSaveData accountSaveData, List<GameResourcePair> costList, List<GameResourcePair> gainList) {
        HorsePrototype horsePrototype = accountSaveData.playerHorse;
        frontend.notifiedHorseStatusChange(horsePrototype, gainList);
        gainList.forEach(resourcePair -> {
            switch (resourcePair.getType()) {
                case HORSE_SPEED:
                    horsePrototype.setBaseSpeed(horsePrototype.getBaseSpeed() + resourcePair.getAmount().intValue());
                    break;
                case HORSE_STAMINA:
                    horsePrototype.setBaseStamina(horsePrototype.getBaseStamina() + resourcePair.getAmount().intValue());
                    break;
                case HORSE_POWER:
                    horsePrototype.setBasePower(horsePrototype.getBasePower() + resourcePair.getAmount().intValue());
                    break;
                default:
                    break;
            }
        });
        if (costList != null) {
            this.modifyAllResourceNum(accountSaveData, costList, false);
        }
        
        
        frontend.log(this.getClass().getSimpleName(), "train done, gain = " + gainList.toString());
        nextDay(accountSaveData);
    }
    
    
    
    
    
    




    public void endRaceRecord(AccountSaveData accountSaveData) {
        EndRecordHorseInfo playerHorseInfo = accountSaveData.sortedRaceEndRecordNode.stream()
                .filter(horseInfo -> horseInfo.getHorseName().equals(accountSaveData.playerHorse.getCharImage()))
                .findAny()
                .get()
                ;
        int rank = accountSaveData.sortedRaceEndRecordNode.indexOf(playerHorseInfo);
        long award = getCurrentTurnConfig(accountSaveData).getRankToAwardMap().get(rank);
        this.modifyAllResourceNum(
                accountSaveData,
                JavaFeatureForGwt.mapOf(GameResourceType.COIN, award), true);
        accountSaveData.currentRaceRecordNodes = null;
        accountSaveData.sortedRaceEndRecordNode = null;
        nextDay(accountSaveData);
    }


    private long getResourceNumOrZero(AccountSaveData accountSaveData, GameResourceType type) {
        return accountSaveData.getOwnResoueces().getOrDefault(type, 0L);
    }

    private void modifyAllResourceNum(AccountSaveData accountSaveData, Map<GameResourceType, Long> map, boolean plus) {
        for (Entry<GameResourceType, Long> entry : map.entrySet()) {
            accountSaveData.getOwnResoueces().merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
        }
        frontend.notifiedModifiedResourceNum(map, plus);
    }


    private void modifyAllResourceNum(AccountSaveData accountSaveData, List<GameResourcePair> packs, boolean plus) {
        Map<GameResourceType, Long> map = packs.stream().collect(Collectors.toMap(
                it -> it.getType(), 
                it -> it.getAmount()
                ));
        modifyAllResourceNum(accountSaveData, map, plus);
    }




    public void trainAndNextDay(AccountSaveData accountSaveData, TrainActionType type) {
        TrainRuleConfig trainRuleConfig = gameRuleData.getTrainRuleConfigMap().get(type);
        trainAndNextDay(accountSaveData, trainRuleConfig.getCostList(), trainRuleConfig.getGainList());
    }
    

}
