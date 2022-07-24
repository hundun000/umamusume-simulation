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
import java.util.Objects;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.UmamusumeApp;
import hundun.simulationgame.umamusume.event.BaseEvent;
import hundun.simulationgame.umamusume.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.record.IRecorder;
import hundun.simulationgame.umamusume.record.RecordPackage;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.Translator;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class CharImageRecorder implements IRecorder<TextFrameData> {
    
    final BotTextCharImageRender render;
    


    

    
    RecordPackage<TextFrameData> recordPackage;
    
    
    public CharImageRecorder(Translator translator) {
        this.render = new BotTextCharImageRender(translator);
    }

    
    @Override
    public void log(String msg) {
        // do nothing
    }
    
    @Override
    public void onFinish() {

    }
    
   
    @Override
    public void onStart(RaceSituation raceSituation) {
        recordPackage = new RecordPackage<TextFrameData>();
        
        recordPackage.addNode(raceSituation.getTickCount(), 
                TextFrameData.builder()
                        .eventInfo("Start")
                        .raceInfo(render.renderStart(raceSituation))
                        .build()
                );
    }
    
    

    @Override
    public void onTick(RaceSituation situation) {
        
        recordPackage.addNode(situation.getTickCount(), 
                render.renderRaceSituation(null, situation)
                );
    }
    
    @Override
    public void onEvent(BaseEvent event) {
        TextFrameData result = render.renderEventOrNot(event);
        if (result != null) {
            recordPackage.addNode(event.getSituation().getTickCount(), result);
        }
    }



    @Override
    public RecordPackage<TextFrameData> getRecordPackage() {
        return recordPackage;
    }

    @Override
    public void printRecordPackage() {
        recordPackage.getNodes().stream()
                .filter(item -> item.getContent().getEventInfo() != null)
                .forEach(item -> {
                    System.out.println(String.format(
                            "[tick %d] %s\n%s", 
                            item.getTick(), 
                            item.getContent().getEventInfo(),
                            Objects.requireNonNullElse(
                                    item.getContent().getRaceInfo(), 
                                    ""
                                    )
                            ));
                });
    }
    
}
