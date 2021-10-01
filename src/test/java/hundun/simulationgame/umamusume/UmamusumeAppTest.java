package hundun.simulationgame.umamusume;

import static org.junit.Assert.*;

import org.junit.Test;

import hundun.simulationgame.umamusume.UmamusumeApp;
import hundun.simulationgame.umamusume.display.CharImageDisplayer;
import hundun.simulationgame.umamusume.display.GUIDisplayer;
import hundun.simulationgame.umamusume.display.SimpleTextDisplayer;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class UmamusumeAppTest {

    @Test
    public void testGuiDisplayer(){
        
        UmamusumeApp app = new UmamusumeApp(new GUIDisplayer());
        app.demoRun();
        
    }
    
    @Test
    public void testTextDisplayer(){
        
        UmamusumeApp app = new UmamusumeApp(new SimpleTextDisplayer());
        app.demoRun();
        
    }
   
   @Test
   public void testCharImageDisplayer(){
       
       UmamusumeApp app = new UmamusumeApp(new CharImageDisplayer());
       app.demoRun();
       
   }
   

}
