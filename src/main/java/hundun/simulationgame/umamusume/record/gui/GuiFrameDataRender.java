package hundun.simulationgame.umamusume.record.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.core.event.BaseEvent;
import hundun.simulationgame.umamusume.core.event.HorseSprintStartPositionSetEvent;
import hundun.simulationgame.umamusume.core.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.core.horse.HorseModel;
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt.NumberFormat;
import hundun.simulationgame.umamusume.record.base.IChineseNameEnum;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.HorseInfo;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.RaceInfo;
import hundun.simulationgame.umamusume.record.text.Translator;
import hundun.simulationgame.umamusume.record.text.Translator.StrategyPackage;
import lombok.Data;


/**
 * @author hundun
 * Created on 2022/06/23
 */
public class GuiFrameDataRender {
    

     
    Map<HorseTrackPhase, Integer> horseTrackPhaseChangeEventCountMap = new HashMap<>();
    
    
    NumberFormat cameraPosFormatter = NumberFormat.getFormat(1, 0);
    NumberFormat minuteFormatter = NumberFormat.getFormat(2, 0);
    NumberFormat secondFormatter = NumberFormat.getFormat(2, 2);
    
    
    
    
    
    private final Translator translator;
    private final StrategyPackage strategyPackage;
    
    public GuiFrameDataRender(Translator translator, StrategyPackage strategyPackage) {
        this.translator = translator;
        this.strategyPackage = strategyPackage;
    }
    
    public String renderHorseStatus(HorsePrototype prototype) {
        return strategyPackage.getHorseStatusTemplate()
                .replace("${NAME_PART}", prototype.getName())
                .replace("${SPEED_KEY}", translator.get("速"))
                .replace("${SPEED_VALUE}", String.valueOf(prototype.getBaseSpeed()))
                .replace("${STAMINA_KEY}", translator.get("耐"))
                .replace("${STAMINA_VALUE}", String.valueOf(prototype.getBaseStamina()))
                .replace("${POWER_KEY}", translator.get("力"))
                .replace("${POWER_VALUE}", String.valueOf(prototype.getBasePower()))
                .replace("${GUTS_KEY}", translator.get("根"))
                .replace("${GUTS_VALUE}", String.valueOf(prototype.getBaseGuts()))
                .replace("${WISDOM_KEY}", translator.get("智"))
                .replace("${WISDOM_VALUE}", String.valueOf(prototype.getBaseWisdom()))
                ;
    }
    
    public String renderStart(RaceSituation raceSituation) {
        StringBuilder builder = new StringBuilder();
        builder.append(raceSituation.getPrototype().getName()).append(" ").append(raceSituation.getPrototype().getLength()).append("米\n");
        for (HorseModel horse : raceSituation.getHorses()) {
            String horseStatusPart = renderHorseStatus(horse.getPrototype()); 
            
            builder.append(strategyPackage.getHorseRaceStartTemplate()
                    .replace("${TRACK_PART}", translator.get("赛道") + String.valueOf(horse.getTrackNumber() + 1))
                    .replace("${HORSE_STATUS_PART}", horseStatusPart)
                    );
        }
        return builder.toString();
    }
    
    public String renderRunStrategyType(RunStrategyType type) {
        return translator.get(type);
    }
    
    public GuiFrameData renderEventOrNot(BaseEvent event) {
        RaceSituation situation = event.getSituation();
        SimpleEntry<EventRenderType, String> checkResult = checkNeedSampleByEvent(event, situation.getTickCount());
        if (checkResult.getKey() == EventRenderType.NOT_RENDER) {
            return null;
        }
        
        String eventInfo = checkResult.getValue();
        
        if (checkResult.getKey() == EventRenderType.WITH_RACE_SITUATION) {
            return this.renderRaceSituation(eventInfo, situation);
        } else if (checkResult.getKey() == EventRenderType.ONLY_DESCRIPTION) {
            return GuiFrameData.builder()
                    .eventInfo(eventInfo)
                    .build();
        } else {
            return null;
        }

    }
    
    public enum EventRenderType {
        NOT_RENDER,
        ONLY_DESCRIPTION,
        WITH_RACE_SITUATION,
        ;
    }
    
    private String renderHorseTrackPhase(HorseTrackPhase phase) {
        if (phase == HorseTrackPhase.REACHED) {
            return translator.get(phase);
        } else {
            return JavaFeatureForGwt.stringFormat(translator.get("进入%s阶段"), translator.get(phase));
        }
    }
    
    private SimpleEntry<EventRenderType, String> checkNeedSampleByEvent(BaseEvent event, int tick) {
        if (event instanceof HorseTrackPhaseChangeEvent) {
            HorseTrackPhaseChangeEvent childEvent = (HorseTrackPhaseChangeEvent) event;
            horseTrackPhaseChangeEventCountMap.merge(childEvent.getTo(), 1, (oldValue, newValue) -> oldValue + newValue);
            boolean allDone = horseTrackPhaseChangeEventCountMap.get(childEvent.getTo()) == childEvent.getSituation().getHorses().size();
            boolean firstDone = horseTrackPhaseChangeEventCountMap.get(childEvent.getTo()) == 1;
            String phaseText = renderHorseTrackPhase(childEvent.getTo());
            String normalDescription = JavaFeatureForGwt.stringFormat(translator.get("%s%s"), 
                    //renderTime(tick),
                    childEvent.getHorse().getCharImage(), 
                    phaseText);
            String allDoneDescription = JavaFeatureForGwt.stringFormat(translator.get("%s最晚%s"), 
                    //renderTime(tick),
                    childEvent.getHorse().getCharImage(), 
                    phaseText);
            String firstDoneDescription = JavaFeatureForGwt.stringFormat(translator.get("%s率先%s"), 
                    //renderTime(tick),
                    childEvent.getHorse().getCharImage(), 
                    phaseText);
            
            if (childEvent.getFrom() == HorseTrackPhase.START_GATE
                    || childEvent.getFrom() == HorseTrackPhase.START_CRUISE
                    || childEvent.getFrom() == HorseTrackPhase.MIDDLE_CRUISE
                    || childEvent.getFrom() == HorseTrackPhase.MIDDLE_CRUISE_HALF
                    ) {
                if (firstDone) {
                    return new SimpleEntry<>(EventRenderType.WITH_RACE_SITUATION, 
                            firstDoneDescription);
                } else {
                    return new SimpleEntry<>(EventRenderType.NOT_RENDER, null);
                }
            } else if (childEvent.getFrom() == HorseTrackPhase.LAST_CRUISE) {
                if (allDone) {
                    return new SimpleEntry<>(EventRenderType.WITH_RACE_SITUATION, 
                            allDoneDescription);
                } else if (firstDone) {
                    return new SimpleEntry<>(EventRenderType.ONLY_DESCRIPTION, 
                            firstDoneDescription);
                } else {
                    return new SimpleEntry<>(EventRenderType.ONLY_DESCRIPTION, 
                            normalDescription);
                }
            } else if (childEvent.getFrom() == HorseTrackPhase.LAST_SPRINT) {
                if (allDone) {
                    return new SimpleEntry<>(EventRenderType.WITH_RACE_SITUATION, 
                            allDoneDescription);
                } else if (firstDone) {
                    return new SimpleEntry<>(EventRenderType.WITH_RACE_SITUATION, 
                            firstDoneDescription);
                } else {
                    return new SimpleEntry<>(EventRenderType.WITH_RACE_SITUATION, 
                            normalDescription);
                }
            } else {
                if (allDone) {
                    return new SimpleEntry<>(EventRenderType.WITH_RACE_SITUATION, 
                            allDoneDescription);
                } else {
                    return new SimpleEntry<>(EventRenderType.ONLY_DESCRIPTION, 
                            normalDescription);
                }
                
            }
        } else if (event instanceof HorseSprintStartPositionSetEvent) {
//            var childEvent = (HorseSprintStartPositionSetEvent) event;
//            String description = childEvent.getHorse().getName() + "的冲刺位置决定为" + formatter.format(childEvent.getSprintStartPosition()) + "m";
//            return new SimpleEntry<>(EventRenderType.ONLY_DESCRIPTION, 
//                    description);
        }
        return new SimpleEntry<>(EventRenderType.NOT_RENDER, null);
    }
    
    /**
     * input: 
     * [50,  60, 75]
     * 
     * calculate:
     * maxDiff = 75 - 50 = 25;
     * numCharPerDiff = 10 / 25 = 0.4
     * numChar(50) = 0;
     * numChar(60) = 10 * numCharPerDiff = 4; 
     * numChar(75) = 25 * numCharPerDiff = 10; 
     * 
     * output:
     * > 50
     * ----> 60
     * ----------> 75
     */
    public GuiFrameData renderRaceSituation(String eventInfo, RaceSituation situation) {
        
        double cameraPosition = Math.min(
                situation.getHorses().stream().mapToDouble(horse -> horse.getTrackPosition()).min().getAsDouble(),
                situation.getPrototype().getLength()
                );
        Map<HorseModel, Double> diffMap = situation.getHorses().stream()
                .collect(Collectors.toMap(
                        it -> it, 
                        it -> it.getTrackPosition() - cameraPosition))
                ;

        
        return GuiFrameData.builder()
                .raceInfo(new RaceInfo(
                        situation.getPrototype().getLength()
                        ))
                .horseInfos(diffMap.entrySet().stream()
                        .map(it -> { 
                            HorseModel model = it.getKey();
                            return new HorseInfo(
                                model.getTrackNumber(),
                                model.getTrackPosition(),
                                it.getValue(),
                                model.getReachTime()
                                );
                        })
                        .collect(Collectors.toList()))
                .eventInfo(eventInfo)
                .cameraPosition(cameraPosition)
                .build();
    }
    
    public String renderTime(int tick) {
        double second = RaceSituation.tickCountToSecond(tick);
        int minute = (int) (second / 60);
        double remaidSecond = second - minute * 60;
        return minuteFormatter.format(minute) + ":" + secondFormatter.format(remaidSecond) + "";
    }
    

    


    public void reset() {
        horseTrackPhaseChangeEventCountMap.clear();
    }
   
 
}
