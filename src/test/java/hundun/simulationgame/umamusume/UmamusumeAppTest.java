package hundun.simulationgame.umamusume;

import static org.junit.Assert.*;

import org.junit.Test;

import hundun.simulationgame.umamusume.game.nogameplay.GuiNoGameplayApp;
import hundun.simulationgame.umamusume.game.nogameplay.NoGameplayApp;
import hundun.simulationgame.umamusume.record.gui.GuiFrameRecorder;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.Translator;
import hundun.simulationgame.umamusume.record.text.Translator.StrategyPackage;


/**
 * @author hundun
 * Created on 2021/09/25
 */
public class UmamusumeAppTest {

    @Test
    public void testCharImageDisplayerChinese(){
        StrategyPackage strategyPackage = new StrategyPackage();
        NoGameplayApp app = new NoGameplayApp(new CharImageRecorder(
                Translator.Factory.emptyAsChinese(),
                strategyPackage 
                ));
        app.randomRun();
        
    }

   
   @Test
   public void testCharImageDisplayerEnglish(){
       StrategyPackage strategyPackage = new StrategyPackage();
       StrategyPackage.Factory.toLongWidth(strategyPackage);
       NoGameplayApp app = new NoGameplayApp(new CharImageRecorder(
               Translator.Factory.english(),
               strategyPackage
               ));
       app.randomRun();
       
   }

   @Test
   public void testGuiDisplayerChinese(){
       StrategyPackage strategyPackage = new StrategyPackage();
       GuiNoGameplayApp app = new GuiNoGameplayApp(new GuiFrameRecorder(
               Translator.Factory.emptyAsChinese(),
               strategyPackage 
               ));
       app.demoRun();
       
   }

}
