package hundun.simulationgame.umamusume.record.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import hundun.simulationgame.umamusume.core.event.BaseEvent;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import hundun.simulationgame.umamusume.record.text.Translator;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.EventRenderType;
import hundun.simulationgame.umamusume.record.text.Translator.StrategyPackage;

/**
 * @author hundun
 * Created on 2022/06/24
 */
public class GuiFrameRecorder  implements IRaceRecorder<GuiFrameData> {

    // for rend event
    final GuiFrameDataRender render;
    
    
    RecordPackage<GuiFrameData> recordPackage;

    
    
    public GuiFrameRecorder(Translator translator, StrategyPackage strategyPackage) {
        this.render = new GuiFrameDataRender(translator, strategyPackage);
    }
    
    
    @Override
    public void onTick(RaceSituation situation) {
        recordPackage.addNode(situation.getTickCount(), 
                render.renderTime(situation.getTickCount()),
                render.renderRaceSituation(null, situation)
                );
    }

    @Override
    public void onFinish() {
        
    }

    @Override
    public void printRecordPackage() {
        // do nothing
        
    }

    @Override
    public void onEvent(BaseEvent event) {
        GuiFrameData result = render.renderEventOrNot(event);
        if (result != null) {
            recordPackage.addNode(
                    event.getSituation().getTickCount(),
                    render.renderTime(event.getSituation().getTickCount()),
                    result);
        }
    }
    

    @Override
    public void logEvent(String msg) {
        System.out.println("[game log]" + msg);
    }

    @Override
    public void onStart(RaceSituation raceSituation) {
        
        
        
        recordPackage = new RecordPackage<>();
        
        
    }

    @Override
    public RecordPackage<GuiFrameData> getRecordPackage() {
        return recordPackage;
    }

    

    @Override
    public void onEnd(RaceSituation raceSituation) {
        List<EndRecordHorseInfo> horseReachTickMap = raceSituation.getHorses().stream()
                .map(it -> new EndRecordHorseInfo(
                        it.getPrototype().getCharImage(),
                        it.getReachTime(), 
                        render.renderTime(it.getReachTime())
                        ))
                .collect(Collectors.toList())
                ;
        recordPackage.setEndNode(new EndRecordNode(horseReachTickMap));
    }
    
}
