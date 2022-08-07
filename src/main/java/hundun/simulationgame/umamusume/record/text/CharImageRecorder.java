package hundun.simulationgame.umamusume.record.text;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.UmamusumeApp;
import hundun.simulationgame.umamusume.event.BaseEvent;
import hundun.simulationgame.umamusume.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.record.IRecorder;
import hundun.simulationgame.umamusume.record.RecordPackage;
import hundun.simulationgame.umamusume.record.RecordPackage.EndRecordNode;
import hundun.simulationgame.umamusume.record.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.StrategyPackage;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.Translator;
import hundun.simulationgame.umamusume.util.JavaFeatureForGwt;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class CharImageRecorder implements IRecorder<TextFrameData> {
    
    final BotTextCharImageRender render;
    


    

    
    RecordPackage<TextFrameData> recordPackage;
    
    
    public CharImageRecorder(Translator translator, StrategyPackage strategyPackage) {
        this.render = new BotTextCharImageRender(translator, strategyPackage);
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
        
        recordPackage.setStartNode(new RecordNode<TextFrameData>(
                raceSituation.getTickCount(),
                render.renderTime(raceSituation.getTickCount()),
                new TextFrameData(
                        render.renderStart(raceSituation),
                        "Start"
                        )
                ));
    }
    
    

    @Override
    public void onTick(RaceSituation situation) {
        
        recordPackage.addNode(situation.getTickCount(), 
                render.renderTime(situation.getTickCount()),
                render.renderRaceSituation(null, situation)
                );
    }
    
    @Override
    public void onEvent(BaseEvent event) {
        TextFrameData result = render.renderEventOrNot(event);
        if (result != null) {
            recordPackage.addNode(
                    event.getSituation().getTickCount(),
                    render.renderTime(event.getSituation().getTickCount()),
                    result);
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
                    System.out.println(JavaFeatureForGwt.stringFormat(
                            "[tick %d] %s\n%s", 
                            item.getTick(), 
                            item.getContent().getEventInfo(),
                            JavaFeatureForGwt.requireNonNullElse(
                                    item.getContent().getRaceInfo(), 
                                    ""
                                    )
                            ));
                });
        EndRecordNode item = recordPackage.getEndNode();
        System.out.println(JavaFeatureForGwt.stringFormat(
                "[End] %s",  
                item.getHorseReachTickMap()
                ));
    }


    @Override
    public void onEnd(RaceSituation raceSituation) {
        Map<String, Integer> horseReachTickMap = raceSituation.getHorses().stream()
//                .map(it -> new AbstractMap.SimpleEntry<String, Integer>(
//                        it.getPrototype().getName(), 
//                        it.getReachTime()
//                        ))
                .collect(Collectors.toMap(
                        it -> it.getPrototype().getName(), 
                        it -> it.getReachTime()
                        ))
                ;
        recordPackage.setEndNode(new EndRecordNode(horseReachTickMap));
        
    }
    
}
