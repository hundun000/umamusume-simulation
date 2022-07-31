package hundun.simulationgame.umamusume;

import static org.junit.Assert.*;

import org.junit.Test;

import hundun.simulationgame.umamusume.UmamusumeApp;
import hundun.simulationgame.umamusume.record.gui.GuiFrameRecorder;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.StrategyPackage;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.Translator;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class UmamusumeAppTest {

    @Test
    public void testCharImageDisplayerChinese(){
        
        UmamusumeApp app = new UmamusumeApp(new CharImageRecorder(
                Translator.Factory.emptyAsChinese(),
                StrategyPackage.Factory.shortWidth()
                ));
        app.randomRun();
        
    }

   
   @Test
   public void testCharImageDisplayerEnglish(){
       
       UmamusumeApp app = new UmamusumeApp(new CharImageRecorder(
               Translator.Factory.english(),
               StrategyPackage.Factory.longWidth()
               ));
       app.randomRun();
       
   }
   
   @Test
   public void testGuiDisplayer(){
       
       UmamusumeApp app = new UmamusumeApp(new GuiFrameRecorder());
       app.randomRun();
       
   }

}
