package hundun.simulationgame.umamusume.demo;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.demo.ConsoleGameplayApp;
import hundun.simulationgame.umamusume.game.gameplay.UmaSaveDataFactory;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourceType;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.record.raw.GuiFrameData;
import hundun.simulationgame.umamusume.record.text.TextFrameData;

/**
 * @author hundun
 * Created on 2023/01/06
 */
public class DemoGameplayFrontendTest {

    public void prepare(ConsoleGameplayApp demoGameplayFrontend) {
        AccountSaveData<TextFrameData> accountSaveData = UmaSaveDataFactory.forNewAccount(ConsoleGameplayApp.SINGLETON_ID);
        Map<String, AccountSaveData<TextFrameData>> accountSaveDataMap = JavaFeatureForGwt.mapOf(
                ConsoleGameplayApp.SINGLETON_ID, 
                accountSaveData);
        GameRuleData gameRuleData = UmaSaveDataFactory.forNewGameRuleData();
        
        demoGameplayFrontend.manager.applySaveData(accountSaveDataMap, gameRuleData);
    }
    
    @Test
    public void test() {
        ConsoleGameplayApp frontend = new ConsoleGameplayApp();
        prepare(frontend);
        
        AccountSaveData<TextFrameData> accountSaveData = frontend.manager.getAccountSaveData(ConsoleGameplayApp.SINGLETON_ID);
        System.out.println(frontend.charImageRecorder.getRender().renderHorseStatus(
                accountSaveData.getPlayerHorse()
                ));
        assertEquals(1L, accountSaveData.getOwnResoueces().get(GameResourceType.TURN).longValue());
        
        frontend.manager.trainAndNextDay(accountSaveData, TrainActionType.FREE_TRAIN);
        System.out.println(frontend.charImageRecorder.getRender().renderHorseStatus(
                accountSaveData.getPlayerHorse()
                ));
        assertEquals(2L, accountSaveData.getOwnResoueces().get(GameResourceType.TURN).longValue());
        
    }

}
