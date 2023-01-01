package hundun.simulationgame.umamusume.record.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import hundun.simulationgame.umamusume.core.event.BaseEvent;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.HorseInfo;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.RaceInfo;

/**
 * @author hundun
 * Created on 2022/06/24
 */
public class GuiFrameRecorder  implements IRaceRecorder<GuiFrameData> {

    RaceTrackPanel raceTrackPanel;
    RecordPackage<GuiFrameData> recordPackage;
    int size;
    boolean guiDone = false;
    
    private GuiFrameData fromRaceSituation(RaceSituation situation) {
        return new GuiFrameData(
                new RaceInfo(
                        situation.getPrototype().getLength()
                ),
                situation.getHorses().stream()
                        .map(model -> new HorseInfo(
                                model.getTrackNumber(),
                                model.getTrackPosition(),
                                model.getReachTime()
                                )
                        )
                        .collect(Collectors.toList())
                );
    }
    
    @Override
    public void onTick(RaceSituation situation) {
        if (situation.getTickCount() % 100 == 0) {
            recordPackage.addNode(
                    situation.getTickCount(), 
                    renderTime(situation.getTickCount()),
                    fromRaceSituation(situation));
        }
    }

    private String renderTime(int tickCount) {
        return "timeOfTick" + tickCount;
    }

    @Override
    public void onFinish() {
        JFrame f = new JFrame("GuiFrameRecorder");
        f.setSize(1300, 300);
        raceTrackPanel = new RaceTrackPanel(size);
        f.add(raceTrackPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
        ShowRaceResultTask task = new ShowRaceResultTask(raceTrackPanel, recordPackage);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0L, 100L);
        while (!guiDone) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printRecordPackage() {
        // do nothing
        
    }

    @Override
    public void onEvent(BaseEvent event) {
        System.out.println("[game event]" + event.getClass().getSimpleName());
        if (recordPackage.getNodes().size() > 0) {
            RecordNode<GuiFrameData> last = recordPackage.getNodes().get(recordPackage.getNodes().size() - 1);
            if (last.getTick() == event.getSituation().getTickCount()) {
                return;
            }
        }
        recordPackage.addNode(
                event.getSituation().getTickCount(), 
                renderTime(event.getSituation().getTickCount()),
                fromRaceSituation(event.getSituation()));
        
    }

    @Override
    public void logEvent(String msg) {
        System.out.println("[game log]" + msg);
    }

    @Override
    public void onStart(RaceSituation raceSituation) {
        
        recordPackage = new RecordPackage<>();
        size = raceSituation.getHorses().size();
        
    }

    @Override
    public RecordPackage<GuiFrameData> getRecordPackage() {
        return recordPackage;
    }

    private class ShowRaceResultTask extends TimerTask {
        private static final double GAME_SECOND_TO_REAL_SECOND = 15.0 / 80.0;
        private static final int REAL_SECOND_TO_TASK_COUNT = 2;
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
                GuiFrameRecorder.this.guiDone = true;
            } else {
                raceTrackPanel.renderRecordNode(recordPackage.getNodes().remove(0).getContent());
            }
        }
        
    }

    @Override
    public void onEnd(RaceSituation raceSituation) {
        // do nothing
        
    }
    
}
