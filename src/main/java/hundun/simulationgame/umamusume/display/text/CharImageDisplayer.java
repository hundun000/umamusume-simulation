package hundun.simulationgame.umamusume.display.text;

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
import hundun.simulationgame.umamusume.display.IDisplayer;
import hundun.simulationgame.umamusume.event.BaseEvent;
import hundun.simulationgame.umamusume.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.Race;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class CharImageDisplayer implements IDisplayer {
    @Setter
    private SampleType sampleType = SampleType.ONLY_EVENT;
    public enum SampleType {
        AUTO_RATE,
        ONLY_EVENT,
        ;
    }
    @Setter
    boolean printOutputBufferWhenFinish = true;
    
    public static final String DEFAULT_SITUATION_TEMPALTE = "====== ${RENDER_REASON} ======\n"
            + "${DESCRIPTION}\n"
            + "${CAMERA_TEXT}\n${HORSE_TEXTS}";
    public static final String BOT_SITUATION_TEMPALTE = "=== ${DESCRIPTION} ===\n"
            + "${HORSE_TEXTS}";
    @Setter
    private String situationTemplate = BOT_SITUATION_TEMPALTE;
    
    public static final String DEFAULT_TEMPLATE = "${NAME} ${ARROW}\n"
            + "\t$pos={POS}, ${HP}, ${SPEED}\n";
    public static final String BOT_TEMPLATE = "${NAME} ${ARROW}(${POS})\n";
    @Setter
    private String horseRunningTemplate = BOT_TEMPLATE;
    
    
    public static final String DEFAULT_REACHED_TEMPLATE = "${NAME} ${REACH_TEXT}\n";
    @Setter
    private String horseReachedTemplate = DEFAULT_REACHED_TEMPLATE;
    
    @Getter
    List<String> outputBuffer = new ArrayList<>();
    
    final static int maxDiffCharNum = 10;
    
    /*
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
    NumberFormat formatter = new DecimalFormat("#0.00");
    
    NumberFormat minuteformatter = new DecimalFormat("#00");
    NumberFormat secondformatter = new DecimalFormat("#00.00");
    
    @Override
    public void log(String msg) {
        // do nothing
    }
    
    @Override
    public void onFinish() {
        if (printOutputBufferWhenFinish) {
            outputBuffer.forEach(item -> System.out.println(item));
        }
    }
    
    private boolean checkNeedSampleByTick(Race situation) {
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
    
    private String renderRaceSituation(String renderReason, String description, Race situation) {
        
        int size = situation.getHorses().size();
        double minPosition = situation.getHorses().stream().mapToDouble(horse -> horse.getTrackPosition()).min().getAsDouble();
        List<Double> diffList = situation.getHorses().stream().mapToDouble(horse -> horse.getTrackPosition() - minPosition).boxed().collect(Collectors.toList());
        double maxDiff = diffList.stream().mapToDouble(i -> i.doubleValue()).max().getAsDouble();
        double numCharPerDiff = 1.0 * maxDiffCharNum / maxDiff;
        List<Integer> numCharList = diffList.stream().mapToInt(diff -> (int)(diff * numCharPerDiff)).boxed().collect(Collectors.toList());
        
        String cameraText = formatter.format(minPosition) + " ···················· " + situation.getPrototype().getLength() + " Fin.\n";
        StringBuilder horseTextsBuilder = new StringBuilder();
        int maxNameLength = situation.getHorses().stream()
                .mapToInt(item -> item.getPrototype().getName().length())
                .max()
                .getAsInt();
                
        for (int i = 0; i < size; i++) {
            HorseModel horse = situation.getHorses().get(i);
            Integer numChar = numCharList.get(i);
            horseTextsBuilder.append(drawHorseCharImage(situation, horse, numChar, maxNameLength));
        }
        String horseTexts = horseTextsBuilder.toString();
        String text = situationTemplate
                .replace("${RENDER_REASON}", renderReason)
                .replace("${DESCRIPTION}", description)
                .replace("${CAMERA_TEXT}", cameraText)
                .replace("${HORSE_TEXTS}", horseTexts)
                ;
        return text;
    }
    
    @Override
    public void onStart(Race race) {
        outputBuffer.add(renderStart(race));
    }
    
    private String renderStart(Race race) {
        StringBuilder builder = new StringBuilder();
        builder.append(race.getPrototype().getName()).append(" ").append(race.getPrototype().getLength()).append("米\n");
        for (var horse : race.getHorses()) {
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

    @Override
    public void onTick(Race situation) {
        
        
        if (!checkNeedSampleByTick(situation)) {
            return;
        }
        String renderReason = "sample at tick " + situation.getTickCount();
        StringBuilder builder = new StringBuilder();
        builder.append(renderRaceSituation(renderReason, "", situation));
        outputBuffer.add(builder.toString());
    }
    
    @Override
    public void onEvent(BaseEvent event) {
        SimpleEntry<Boolean, String> checkResult = checkNeedSampleByEvent(event);
        if (!checkResult.getKey()) {
            return;
        }
        String newDescription = checkResult.getValue();
        String renderReason = "GameEvent at tick " + event.getSituation().getTickCount();
        StringBuilder builder = new StringBuilder();
        builder.append(renderRaceSituation(renderReason, newDescription, event.getSituation()));
        outputBuffer.add(builder.toString());
    }
    
    
    Map<HorseTrackPhase, Integer> horseTrackPhaseChangeEventCountMap = new HashMap<>();
    
    private SimpleEntry<Boolean, String> checkNeedSampleByEvent(BaseEvent event) {
        if (event instanceof HorseTrackPhaseChangeEvent) {
            var childEvent = (HorseTrackPhaseChangeEvent) event;
            if (childEvent.getTo() == HorseTrackPhase.REACHED) {
                // 某些阶段，一律返true
                return new SimpleEntry<>(true, childEvent.getDescription());
            } else {
                horseTrackPhaseChangeEventCountMap.merge(childEvent.getTo(), 1, (oldValue, newValue) -> oldValue + newValue);
                boolean allDone = horseTrackPhaseChangeEventCountMap.get(childEvent.getTo()) == childEvent.getSituation().getHorses().size();
                // 其它阶段，仅所有马均触发时返true
                return new SimpleEntry<>(allDone, allDone ? childEvent.allDoneDescription() : null);
            }
        }
        return new SimpleEntry<>(false, null);
    }

    private String drawHorseCharImage(Race situation, HorseModel horse, Integer numChar, int maxNameLength) {
        String hpSubText = horse.getCurrentHp() > 0 ? "hp = " + formatter.format(horse.getCurrentHp()) + "" : "<疲劳>";
        String nameString = String.format("%-" + maxNameLength + "s", horse.getPrototype().getName());
        if (horse.getReachTime() == null) {
            String arrowCharImage = "-".repeat(numChar + 1) + "> ";
            String positionSubText = formatter.format(horse.getTrackPosition()) + "m";
            String speedSubText = "v=" + formatter.format(horse.getCurrentSpeed());
            if (horse.getCurrentAcceleration() != null) {
                if (horse.getCurrentAcceleration() > 0) {
                    speedSubText += "↑a=" + formatter.format(horse.getCurrentAcceleration());
                } else {
                    speedSubText += "↓a=" + formatter.format(horse.getCurrentAcceleration());
                }
                speedSubText += "(target=" + formatter.format(horse.getTargetSpeed()) + ")";
            } else {
                speedSubText += "-";
            }
            if (horse.getTrackPhase() == HorseTrackPhase.LAST_SPRINT) {
                speedSubText += "冲";
            }
            String text = horseRunningTemplate
                    .replace("${NAME}", nameString)
                    .replace("${ARROW}", arrowCharImage)
                    .replace("${POS}", positionSubText)
                    .replace("${HP}", hpSubText)
                    .replace("${SPEED}", speedSubText)
                    ;
            return text;
        } else {
            String reachText = "冲线时间：" + renderTime(horse.getReachTime());
            String text = horseReachedTemplate
                    .replace("${NAME}", nameString)
                    .replace("${REACH_TEXT}", reachText)
                    ;
            return text;
        }
    }

    private String renderTime(int tick) {
        double second = UmamusumeApp.tickCountToSecond(tick);
        int minute = (int) (second / 60);
        double remaidSecond = second - minute * 60;
        return minuteformatter.format(minute) + ":" + secondformatter.format(remaidSecond) + "";
    }
    
}
