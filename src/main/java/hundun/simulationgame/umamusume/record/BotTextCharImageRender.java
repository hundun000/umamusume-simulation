package hundun.simulationgame.umamusume.record;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.event.BaseEvent;
import hundun.simulationgame.umamusume.event.HorseSprintStartPositionSetEvent;
import hundun.simulationgame.umamusume.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.RaceSituation;


/**
 * @author hundun
 * Created on 2022/06/23
 */
public class BotTextCharImageRender {
    
    
    Map<HorseTrackPhase, Integer> horseTrackPhaseChangeEventCountMap = new HashMap<>();
    
    
    NumberFormat cameraPosFormatter = new DecimalFormat("#0");
    NumberFormat minuteFormatter = new DecimalFormat("#00");
    NumberFormat secondFormatter = new DecimalFormat("#00.00");
    
    
    private final static String horseRunningTemplate = "${HORSE_ICON} ${ARROW}\n";
    
    private final static String horseReachedTemplate = "${HORSE_ICON} ${REACH_TEXT}\n";
    private final static int maxDiffCharNum = 10;
    
    public String renderStart(RaceSituation raceSituation) {
        StringBuilder builder = new StringBuilder();
        builder.append(raceSituation.getPrototype().getName()).append(" ").append(raceSituation.getPrototype().getLength()).append("米\n");
        for (var horse : raceSituation.getHorses()) {
            builder.append("赛道").append(horse.getTrackNumber() + 1).append(": ")
            .append(horse.getPrototype().getName()).append(" ")
            .append(horse.getRunStrategyType().getChinese()).append("  ")
            .append("速").append(horse.getPrototype().getBaseSpeed()).append("，")
            .append("耐").append(horse.getPrototype().getBaseStamina()).append("，")
            .append("力").append(horse.getPrototype().getBasePower()).append("，")
            .append("根").append(horse.getPrototype().getBaseGuts()).append("，")
            .append("智").append(horse.getPrototype().getBaseWisdom()).append("")
            .append("\n");
        }
        return builder.toString();
    }
    
    public String renderEventOrNot(BaseEvent event) {
        RaceSituation situation = event.getSituation();
        SimpleEntry<EventRenderType, String> checkResult = checkNeedSampleByEvent(event, situation.getTickCount());
        if (checkResult.getKey() == EventRenderType.NOT_RENDER) {
            return null;
        }
        
        String newDescription = checkResult.getValue();
        String renderReason = "GameEvent at tick " + situation.getTickCount();
        
        if (checkResult.getKey() == EventRenderType.WITH_RACE_SITUATION) {
            return this.renderRaceSituation(renderReason, newDescription, situation);
        } else if (checkResult.getKey() == EventRenderType.ONLY_DESCRIPTION) {
            return newDescription;
        } else {
            return null;
        }

    }
    
    private enum EventRenderType {
        NOT_RENDER,
        ONLY_DESCRIPTION,
        WITH_RACE_SITUATION,
        ;
    }
    
    private String renderHorseTrackPhase(HorseTrackPhase phase) {
        if (phase == HorseTrackPhase.REACHED) {
            return phase.getChinese();
        } else {
            return "进入" + phase.getChinese() + "阶段";
        }
    }
    
    private SimpleEntry<EventRenderType, String> checkNeedSampleByEvent(BaseEvent event, int tick) {
        if (event instanceof HorseTrackPhaseChangeEvent) {
            var childEvent = (HorseTrackPhaseChangeEvent) event;
            horseTrackPhaseChangeEventCountMap.merge(childEvent.getTo(), 1, (oldValue, newValue) -> oldValue + newValue);
            boolean allDone = horseTrackPhaseChangeEventCountMap.get(childEvent.getTo()) == childEvent.getSituation().getHorses().size();
            boolean firstDone = horseTrackPhaseChangeEventCountMap.get(childEvent.getTo()) == 1;
            String phaseText = renderHorseTrackPhase(childEvent.getTo());
            String normalDescription = String.format("%s %s%s", 
                    renderTime(tick),
                    childEvent.getHorse().getName(), 
                    phaseText);
            String allDoneDescription = String.format("%s %s最晚%s", 
                    renderTime(tick),
                    childEvent.getHorse().getName(), 
                    phaseText);
            String firstDoneDescription = String.format("%s %s率先%s", 
                    renderTime(tick),
                    childEvent.getHorse().getName(), 
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
                    return new SimpleEntry<>(EventRenderType.ONLY_DESCRIPTION, 
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
    
    private String renderCameraProcessBar(int raceLength, double cameraPosition) {
        int numBarNode = 10;
        int numPassed = (int) (numBarNode * (cameraPosition / raceLength));
        int numTodo = numBarNode - numPassed;
        return "[" + "=".repeat(numPassed) + "o" + " ".repeat(numTodo) + "]";
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
    public String renderRaceSituation(String renderReason, String description, RaceSituation situation) {
        
        int size = situation.getHorses().size();
        double cameraPosition = Math.min(
                situation.getHorses().stream().mapToDouble(horse -> horse.getTrackPosition()).min().getAsDouble(),
                situation.getPrototype().getLength()
                );
        List<Double> diffList = situation.getHorses().stream().mapToDouble(horse -> horse.getTrackPosition() - cameraPosition).boxed().collect(Collectors.toList());
        double maxDiff = diffList.stream().mapToDouble(i -> i.doubleValue()).max().getAsDouble();
        double numCharPerDiff = 1.0 * maxDiffCharNum / maxDiff;
        List<Integer> numCharList = diffList.stream().mapToInt(diff -> (int)(diff * numCharPerDiff)).boxed().collect(Collectors.toList());
        
        //String cameraText = formatter.format(minPosition) + " ···················· " + situation.getPrototype().getLength() + " Fin.\n";
        String cameraProcessBar = renderCameraProcessBar(situation.getPrototype().getLength(), cameraPosition);
        StringBuilder horseTextsBuilder = new StringBuilder();
        int maxNameLength = situation.getHorses().stream()
                .mapToInt(item -> item.getPrototype().getCharImage().length())
                .max()
                .getAsInt();
                
        for (int i = 0; i < size; i++) {
            HorseModel horse = situation.getHorses().get(i);
            Integer numChar = numCharList.get(i);
            horseTextsBuilder.append(drawHorseCharImage(situation, horse, numChar, maxNameLength));
        }
        String horseTexts = horseTextsBuilder.toString();
        
        StringBuilder result = new StringBuilder();
        result.append(description).append("\n")
                .append(cameraProcessBar).append(" ").append(cameraPosFormatter.format(cameraPosition)).append("m\n")
                .append(horseTexts);
        return result.toString();
    }
    
    private String drawHorseCharImage(RaceSituation situation, HorseModel horse, Integer numChar, int maxNameLength) {
        String hpSubText = horse.getCurrentHp() > 0 ? "hp = " + cameraPosFormatter.format(horse.getCurrentHp()) + "" : "<疲劳>";
        String horseIcon = String.format("%-" + maxNameLength + "s", horse.getPrototype().getCharImage());
        if (horse.getReachTime() == null) {
            String arrowCharImage = "-".repeat(numChar + 1) + "> ";
            String positionSubText = cameraPosFormatter.format(horse.getTrackPosition()) + "m";
            String speedSubText = "v=" + cameraPosFormatter.format(horse.getCurrentSpeed());
            if (horse.getCurrentAcceleration() != null) {
                if (horse.getCurrentAcceleration() > 0) {
                    speedSubText += "↑a=" + cameraPosFormatter.format(horse.getCurrentAcceleration());
                } else {
                    speedSubText += "↓a=" + cameraPosFormatter.format(horse.getCurrentAcceleration());
                }
                speedSubText += "(target=" + cameraPosFormatter.format(horse.getTargetSpeed()) + ")";
            } else {
                speedSubText += "-";
            }
            if (horse.getTrackPhase() == HorseTrackPhase.LAST_SPRINT) {
                speedSubText += "冲";
            }
            String text = horseRunningTemplate
                    .replace("${HORSE_ICON}", horseIcon)
                    .replace("${ARROW}", arrowCharImage)
                    .replace("${POS}", positionSubText)
                    .replace("${HP}", hpSubText)
                    .replace("${SPEED}", speedSubText)
                    ;
            return text;
        } else {
            String reachText = "冲线时间：" + renderTime(horse.getReachTime());
            String text = horseReachedTemplate
                    .replace("${HORSE_ICON}", horseIcon)
                    .replace("${REACH_TEXT}", reachText)
                    ;
            return text;
        }
    }

    private String renderTime(int tick) {
        double second = RaceSituation.tickCountToSecond(tick);
        int minute = (int) (second / 60);
        double remaidSecond = second - minute * 60;
        return minuteFormatter.format(minute) + ":" + secondFormatter.format(remaidSecond) + "";
    }

 
}
