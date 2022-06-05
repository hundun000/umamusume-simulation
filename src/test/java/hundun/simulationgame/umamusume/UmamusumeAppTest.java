package hundun.simulationgame.umamusume;

import static org.junit.Assert.*;

import org.junit.Test;

import hundun.simulationgame.umamusume.UmamusumeApp;
import hundun.simulationgame.umamusume.display.gui.GUIDisplayer;
import hundun.simulationgame.umamusume.display.text.SimpleTextDisplayer;
import hundun.simulationgame.umamusume.record.CharImageRecorder;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class UmamusumeAppTest {

   
   @Test
   public void testCharImageDisplayer(){
       
       UmamusumeApp app = new UmamusumeApp(new CharImageRecorder());
       app.randomRun();
       
   }
   

}
