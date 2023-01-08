package hundun.simulationgame.umamusume.game.nogameplay;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.core.race.TrackWetType;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData;
import hundun.simulationgame.umamusume.record.gui.GuiFrameRecorder;
import hundun.simulationgame.umamusume.record.gui.RaceTrackPanel;

/**
 * @author hundun
 * Created on 2023/01/14
 */
public class GuiNoGameplayApp {
    
    HorsePrototypeFactory factory;
    private final GuiFrameRecorder displayer;
    RaceSituation raceSituation;
    
    RaceTrackPanel raceTrackPanel;
    int size;
    boolean guiDone = false;
    
    public GuiNoGameplayApp(GuiFrameRecorder displayer) {
        this.displayer = displayer;
        this.factory = new HorsePrototypeFactory();
        factory.registerAllDefault();
    }
    
    public void demoRun(){
        raceSituation = new RaceSituation(displayer, RacePrototypeFactory.OKA_SHO, TrackWetType.GOOD);
        raceSituation.addHorse(HorsePrototypeFactory.SILENCE_SUZUKA_A, RunStrategyType.FIRST);
        raceSituation.addHorse(HorsePrototypeFactory.SPECIAL_WEEK_A, RunStrategyType.FRONT);
        raceSituation.addHorse(HorsePrototypeFactory.GRASS_WONDER_A, RunStrategyType.BACK);

        raceSituation.calculateResult();
        displayer.printRecordPackage();
        
        guiShowResule();
    }
    
    private void guiShowResule() {
        JFrame f = new JFrame("GuiFrameRecorder");
        f.setSize(1300, 300);
        size = raceSituation.getHorses().size();
        raceTrackPanel = new RaceTrackPanel(size);
        f.add(raceTrackPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
        ShowRaceResultTask task = new ShowRaceResultTask(raceTrackPanel, displayer.getRecordPackage());
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0L, ShowRaceResultTask.TASK_DELAY);
        while (!guiDone) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private class ShowRaceResultTask extends TimerTask {

        private static final int REAL_SECOND_TO_TASK_COUNT = 500;
        public static final long TASK_DELAY = 1000 / REAL_SECOND_TO_TASK_COUNT;
        
        RaceTrackPanel raceTrackPanel;
        RecordPackage<GuiFrameData> recordPackage;
        
        public ShowRaceResultTask(RaceTrackPanel raceTrackPanel, RecordPackage<GuiFrameData> recordPackage) {
            super();
            this.raceTrackPanel = raceTrackPanel;
            this.recordPackage = recordPackage;
        }


        @Override
        public void run() {
            if (recordPackage.getNodes().size() == 0) {
                this.cancel();
                GuiNoGameplayApp.this.guiDone = true;
            } else {
                raceTrackPanel.renderRecordNode(recordPackage.getNodes().remove(0).getContent());
            }
        }
        
    }
}
