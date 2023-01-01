package hundun.simulationgame.umamusume.gameplay.demo;

import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData;
import hundun.simulationgame.umamusume.gameplay.GameResourcePair;
import hundun.simulationgame.umamusume.gameplay.GameResourceType;
import hundun.simulationgame.umamusume.gameplay.GameRuleData;
import hundun.simulationgame.umamusume.gameplay.IGameplayFrontend;
import hundun.simulationgame.umamusume.gameplay.TrainActionType;
import hundun.simulationgame.umamusume.gameplay.UmaGameplayManager;
import hundun.simulationgame.umamusume.gameplay.UmaSaveDataFactory;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import hundun.simulationgame.umamusume.record.text.Translator;
import hundun.simulationgame.umamusume.record.text.Translator.StrategyPackage;

/**
 * @author hundun
 * Created on 2023/01/04
 */
public class DemoGameplayFrontend implements IGameplayFrontend {

    UmaGameplayManager manager;
    CharImageRecorder charImageRecorder;
    public static final String SINGLETON_ID = "DEMO_GAMEPLAY_FRONTEND";
    
    public DemoGameplayFrontend() {
        Translator translator = Translator.Factory.emptyAsChinese();
        StrategyPackage strategyPackage = new StrategyPackage();
        this.charImageRecorder = new CharImageRecorder(translator, strategyPackage);
        
        this.manager = new UmaGameplayManager(translator, charImageRecorder, this);
    }
    
    public void demoRun() {
        AccountSaveData accountSaveData = UmaSaveDataFactory.forNewAccount(DemoGameplayFrontend.SINGLETON_ID);
        Map<String, AccountSaveData> accountSaveDataMap = JavaFeatureForGwt.mapOf(
                DemoGameplayFrontend.SINGLETON_ID, 
                accountSaveData);
        GameRuleData gameRuleData = UmaSaveDataFactory.forNewGameRuleData();
        
        manager.applySaveData(accountSaveDataMap, gameRuleData);
        manager.trainAndNextDay(accountSaveData, TrainActionType.FREE_TRAIN);
    }
    
    @Override
    public void log(String tag, String message) {
        System.out.println(JavaFeatureForGwt.stringFormat("[%s]%s", tag, message));
    }

    @Override
    public void notifiedModifiedResourceNum(Map<GameResourceType, Long> map, boolean plus) {
        log(DemoGameplayFrontend.class.getSimpleName(), "notifiedModifiedResourceNum");
    }

    @Override
    public void notifiedReplayRaceRecord() {
        log(DemoGameplayFrontend.class.getSimpleName(), "ReplayRaceRecord by charImageRecorder.printRecordPackage()");
        charImageRecorder.printRecordPackage();
    }

    @Override
    public void notifiedChangeOperationBoardState() {
        // will not change in demo
    }

    @Override
    public void notifiedHorseStatusChange(HorsePrototype horsePrototype,
            List<GameResourcePair> gainList) {
        log(DemoGameplayFrontend.class.getSimpleName(), "notifiedModifiedResourceNum");
    }

}
