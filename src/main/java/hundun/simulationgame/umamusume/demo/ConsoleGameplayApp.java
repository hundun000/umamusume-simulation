package hundun.simulationgame.umamusume.demo;

import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.game.gameplay.IGameplayLogicCallback;
import hundun.simulationgame.umamusume.game.gameplay.UmaGameplayManager;
import hundun.simulationgame.umamusume.game.gameplay.UmaSaveDataFactory;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourceType;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import hundun.simulationgame.umamusume.record.text.Translator;
import hundun.simulationgame.umamusume.record.text.Translator.StrategyPackage;

/**
 * @author hundun
 * Created on 2023/01/04
 */
public class ConsoleGameplayApp implements IGameplayLogicCallback {

    UmaGameplayManager<TextFrameData> manager;
    CharImageRecorder charImageRecorder;
    public static final String SINGLETON_ID = "DEMO_GAMEPLAY_FRONTEND";
    
    public ConsoleGameplayApp() {
        Translator translator = Translator.Factory.emptyAsChinese();
        StrategyPackage strategyPackage = new StrategyPackage();
        this.charImageRecorder = new CharImageRecorder(translator, strategyPackage);
        
        this.manager = new UmaGameplayManager<TextFrameData>(translator, charImageRecorder, this);
    }
    
    @Override
    public void log(String tag, String message) {
        System.out.println(JavaFeatureForGwt.stringFormat("[%s]%s", tag, message));
    }

    @Override
    public void notifiedModifiedResourceNum(Map<GameResourceType, Long> map, boolean plus) {
        log(ConsoleGameplayApp.class.getSimpleName(), "notifiedModifiedResourceNum");
    }

//    @Override
//    public void notifiedReplayRaceRecord() {
//        log(ConsoleGameplayApp.class.getSimpleName(), "ReplayRaceRecord by charImageRecorder.printRecordPackage()");
//        charImageRecorder.printRecordPackage();
//    }

    @Override
    public void notifiedChangeOperationBoardState() {
        // will not change in demo
    }

    @Override
    public void notifiedHorseStatusChange(HorsePrototype horsePrototype,
            List<GameResourcePair> gainList) {
        log(ConsoleGameplayApp.class.getSimpleName(), "notifiedModifiedResourceNum");
    }

}
