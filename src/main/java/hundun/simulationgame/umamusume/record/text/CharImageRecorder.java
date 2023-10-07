package hundun.simulationgame.umamusume.record.text;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.core.event.BaseEvent;
import hundun.simulationgame.umamusume.core.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.core.horse.HorseModel;
import hundun.simulationgame.umamusume.core.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.demo.ConsoleNoGameplayApp;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.StartRecordNode;
import hundun.simulationgame.umamusume.record.text.Translator.StrategyPackage;
import lombok.Getter;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class CharImageRecorder implements IRaceRecorder<TextFrameData> {
    
    @Getter
    final BotTextCharImageRender render;
    


    

    
    RecordPackage<TextFrameData> recordPackage;
    
    
    public CharImageRecorder(Translator translator, StrategyPackage strategyPackage) {
        this.render = new BotTextCharImageRender(translator, strategyPackage);
    }

    
    @Override
    public void logEvent(String msg) {
        // do nothing
    }
    
    @Override
    public void onFinish() {

    }
    
   
    @Override
    public void onStart(RaceSituation raceSituation) {
        recordPackage = new RecordPackage<TextFrameData>();
        render.reset();
        
        Map<String, String> runStrategyTextMap = raceSituation.getHorses().stream()
                .map(horse -> new AbstractMap.SimpleEntry<String, String>(
                        horse.getPrototype().getName(),
                        render.renderRunStrategyType(horse.getRunStrategyType())
                        ))
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), 
                        entry -> entry.getValue()
                        ))
                ;
        recordPackage.setStartNode(
                new StartRecordNode<TextFrameData>(
                        new RecordNode<TextFrameData>(
                                raceSituation.getTickCount(),
                                render.renderTime(raceSituation.getTickCount()),
                                new TextFrameData(
                                        render.renderStart(raceSituation),
                                        "Start"
                                        )
                        ), 
                        runStrategyTextMap
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
                            "[tick %s] %s\n%s", 
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
                item.getHorseInfos().stream()
                        .map(info -> JavaFeatureForGwt.stringFormat(
                                "%s %s",
                                info.getHorseName(),
                                info.getReachTimeText()
                                ))
                        .collect(Collectors.joining("\n"))
                ));
    }


    @Override
    public void onEnd(RaceSituation raceSituation) {
        List<EndRecordHorseInfo> horseReachTickMap = raceSituation.getHorses().stream()
                .map(it -> new EndRecordHorseInfo(
                        it.getPrototype().getName(),
                        it.getReachTime(), 
                        render.renderTime(it.getReachTime())
                        ))
                .collect(Collectors.toList())
                ;
        recordPackage.setEndNode(new EndRecordNode(horseReachTickMap));
        
    }
    
}
