package hundun.simulationgame.umamusume;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import hundun.simulationgame.umamusume.display.GUIDisplayer;
import hundun.simulationgame.umamusume.display.IDisplayer;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.horse.RunStrategyType;
import hundun.simulationgame.umamusume.race.Race;
import hundun.simulationgame.umamusume.race.RaceLengthType;


/**
 * @author hundun
 * Created on 2021/09/24
 */
public class UmamusumeApp {
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    // ms
    private int frameLength = -1;
    public static final int tickNumPerSecond = 100;
    
    private final IDisplayer displayer;
    Race race;
    
    boolean debugOnlyFirstTick = true;
    
    public UmamusumeApp(IDisplayer displayer) {
        this.displayer = displayer;
    }
    
    public void demoRun(){
        try {
            race = new Race(displayer, RaceLengthType.MILE, 1600);
            race.addHorse(HorsePrototypeFactory.SILENCE_SUZUKA_A, RunStrategyType.FIRST);
            race.addHorse(HorsePrototypeFactory.SPECIAL_WEEK_A, RunStrategyType.FRONT);
            race.addHorse(HorsePrototypeFactory.GRASS_WONDER_A, RunStrategyType.BACK);
    
            if (debugOnlyFirstTick) {
                tick();
                return;
            }
            
            if (frameLength > 0) {
                this.scheduler.scheduleAtFixedRate(new RaceTickTask(), frameLength, frameLength, TimeUnit.MILLISECONDS);
                while (!race.isAllReached()) {
                    Thread.sleep(frameLength / 2);
                }
                this.scheduler.shutdown();
            } else {
                while (!race.isAllReached()) {
                    tick();
                }
                tick();
            }
            
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void tick() {
        race.tickUpdate();

        if (displayer != null) {
            displayer.renderRaceSituation(race);
        }
    }

    
    public class RaceTickTask implements Runnable {

        public RaceTickTask() {
            super();
        }

        @Override
        public void run() {
            try { 
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
