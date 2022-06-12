package hundun.simulationgame.umamusume.record.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.UmamusumeApp;
import hundun.simulationgame.umamusume.event.BaseEvent;
import hundun.simulationgame.umamusume.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.record.IRecorder;
import hundun.simulationgame.umamusume.record.RecordPackage;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class CharImageRecorder implements IRecorder<String> {
    
    BotTextCharImageRender render = new BotTextCharImageRender();
    
    @Setter
    private SampleType sampleType = SampleType.ONLY_EVENT;
    public enum SampleType {
        AUTO_RATE,
        ONLY_EVENT,
        ;
    }

    

    
    RecordPackage<String> recordPackage;
    
    
    

    
    @Override
    public void log(String msg) {
        // do nothing
    }
    
    @Override
    public void onFinish() {

    }
    
    private boolean checkNeedSampleByTick(RaceSituation situation) {
        if (sampleType == SampleType.AUTO_RATE) {
            int sampleRate = 1000;
            
            boolean lastCorner = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() == HorseTrackPhase.LAST_SPRINT || horse.getTrackPhase() == HorseTrackPhase.LAST_CRUISE).findFirst().isPresent();
            boolean anyHorseSpeedDelta = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() != HorseTrackPhase.REACHED && horse.getCurrentAcceleration() != null).findFirst().isPresent();
            if (lastCorner || anyHorseSpeedDelta) {
                sampleRate = 100;
            }
            
            boolean startGate = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() == HorseTrackPhase.START_GATE).findFirst().isPresent();
            boolean allReached = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() == HorseTrackPhase.REACHED).findFirst().isPresent();
            if (startGate || allReached) {
                sampleRate = 1;
            }
            
            boolean needPrint = situation.getTickCount() % sampleRate == 0;
            if (needPrint) {
                return true;
            }
        }
        return false;
    }
    
   
    @Override
    public void onStart(RaceSituation raceSituation) {
        recordPackage = new RecordPackage<String>();
        recordPackage.addNode(raceSituation.getTickCount(), render.renderStart(raceSituation));
    }
    
    

    @Override
    public void onTick(RaceSituation situation) {
        
        
        if (!checkNeedSampleByTick(situation)) {
            return;
        }
        String renderReason = "sample at tick " + situation.getTickCount();
        StringBuilder builder = new StringBuilder();
        builder.append(render.renderRaceSituation(renderReason, "", situation));
        
        recordPackage.addNode(situation.getTickCount(), builder.toString());
    }
    
    @Override
    public void onEvent(BaseEvent event) {
        String result = render.renderEventOrNot(event);
        if (result != null) {
            recordPackage.addNode(event.getSituation().getTickCount(), result);
        }
    }



    @Override
    public RecordPackage<String> getRecordPackage() {
        return recordPackage;
    }

    @Override
    public void printRecordPackage() {
        recordPackage.getNodes().forEach(item -> {
            System.out.println(String.format("[tick %d] %s", item.getTick(), item.getContent()));
        });
    }
    
}
