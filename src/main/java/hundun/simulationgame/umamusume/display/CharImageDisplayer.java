package hundun.simulationgame.umamusume.display;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.Race;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class CharImageDisplayer implements IDisplayer {

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
    
    @Override
    public void renderRaceSituation(Race situation) {
        StringBuilder builder = new StringBuilder();
        
        int sampleRate = 1000;
        
        boolean lastCorner = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() == HorseTrackPhase.LAST_SPRINT || horse.getTrackPhase() == HorseTrackPhase.LAST_CRUISE).findFirst().isPresent();
        boolean anyHorseSpeedDelta = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() != HorseTrackPhase.REACHED && horse.getCurrentSpeedDelta() != null).findFirst().isPresent();
        if (lastCorner || anyHorseSpeedDelta) {
            sampleRate = 100;
        }
        
        boolean startGate = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() == HorseTrackPhase.START_GATE).findFirst().isPresent();
        boolean allReached = situation.getHorses().stream().filter(horse -> horse.getTrackPhase() == HorseTrackPhase.REACHED).findFirst().isPresent();
        if (startGate || allReached) {
            sampleRate = 1;
        }
        
        boolean needPrint = situation.getTickCount() % sampleRate == 0;
        if (!needPrint) {
            return;
        }
        
        builder.append("====== tick ").append(situation.getTickCount()).append(" ======\n");
        int size = situation.getHorses().size();
        double minPosition = situation.getHorses().stream().mapToDouble(horse -> horse.getTrackPosition()).min().getAsDouble();
        List<Double> diffList = situation.getHorses().stream().mapToDouble(horse -> horse.getTrackPosition() - minPosition).boxed().collect(Collectors.toList());
        double maxDiff = diffList.stream().mapToDouble(i -> i.doubleValue()).max().getAsDouble();
        double numCharPerDiff = 1.0 * maxDiffCharNum / maxDiff;
        List<Integer> numCharList = diffList.stream().mapToInt(diff -> (int)(diff * numCharPerDiff)).boxed().collect(Collectors.toList());
        
        builder.append(formatter.format(minPosition)).append(" ···················· " + situation.getLength() + " Fin.\n");
        for (int i = 0; i < size; i++) {
            HorseModel horse = situation.getHorses().get(i);
            Integer numChar = numCharList.get(i);
            drawHorseCharImage(situation, horse, numChar, builder);
        }
        System.out.println(builder.toString());
    }
    
    
    private void drawHorseCharImage(Race situation, HorseModel horse, Integer numChar, StringBuilder builder) {
        String hpSubText = horse.getCurrentHp() > 0 ? "hp=" + formatter.format(horse.getCurrentHp()) + "" : "<疲劳>";
        if (horse.getReachTime() == null) {
            String arrowCharImage = "-".repeat(numChar + 1) + "> ";
            String positionSubText = horse.getReachTime() != null ? ("Reach at " + horse.getReachTime()) : "pos=" + formatter.format(horse.getTrackPosition()) ;
            String speedSubText = "v=" + formatter.format(horse.getCurrentSpeed());
            if (horse.getCurrentSpeedDelta() != null) {
                if (horse.getCurrentSpeedDelta() > 0) {
                    speedSubText+= "↑a=" + formatter.format(horse.getCurrentSpeedDelta());
                } else {
                    speedSubText+= "↓a=" + formatter.format(horse.getCurrentSpeedDelta());
                }
            } else {
                speedSubText+= "-";
            }
            builder.append(horse.getPrototype().getName()).append("\t")
                    .append(arrowCharImage).append("\t")      
                    .append(positionSubText).append(", ")
                    .append(hpSubText).append(", ")
                    .append(speedSubText)
                    .append("\n");
        } else {
            builder.append(horse.getPrototype().getName()).append("\t")
            .append("reached at tick=").append(horse.getReachTime())  
            .append(hpSubText)
            .append("\n");
        }
    }

}
