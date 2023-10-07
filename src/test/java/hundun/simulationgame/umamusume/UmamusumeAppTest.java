package hundun.simulationgame.umamusume;

import static org.junit.Assert.*;

import org.junit.Test;

import hundun.simulationgame.umamusume.demo.GuiNoGameplayApp;
import hundun.simulationgame.umamusume.demo.ConsoleNoGameplayApp;
import hundun.simulationgame.umamusume.record.raw.GuiFrameRecorder;
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
        ConsoleNoGameplayApp app = new ConsoleNoGameplayApp(new CharImageRecorder(
                Translator.Factory.emptyAsChinese(),
                strategyPackage 
                ));
        app.randomRun();
        
    }

   
   @Test
   public void testCharImageDisplayerEnglish(){
       StrategyPackage strategyPackage = new StrategyPackage();
       StrategyPackage.Factory.toLongWidth(strategyPackage);
       ConsoleNoGameplayApp app = new ConsoleNoGameplayApp(new CharImageRecorder(
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
