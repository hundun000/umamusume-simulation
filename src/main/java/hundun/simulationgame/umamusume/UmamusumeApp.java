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
import hundun.simulationgame.umamusume.race.RacePrototype;
import hundun.simulationgame.umamusume.race.RacePrototypeFactory;
import hundun.simulationgame.umamusume.race.TrackWetType;


/**
 * @author hundun
 * Created on 2021/09/24
 */
public class UmamusumeApp {
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    // ms
    private int frameLength = 100;
    public static final int tickNumPerGameSecond = 100;
    
    private final IDisplayer displayer;
    Race race;
    
    boolean debugOnlyFirstTick = false;
    boolean debugTickLoopWithoutDelay = true;
    
    public UmamusumeApp(IDisplayer displayer) {
        this.displayer = displayer;
    }
    
    public void demoRun(){
        try {
            race = new Race(displayer, RacePrototypeFactory.OKA_SHO, TrackWetType.GOOD);
            race.addHorse(HorsePrototypeFactory.SILENCE_SUZUKA_A, RunStrategyType.FIRST);
            race.addHorse(HorsePrototypeFactory.SPECIAL_WEEK_A, RunStrategyType.FRONT);
            race.addHorse(HorsePrototypeFactory.GRASS_WONDER_A, RunStrategyType.BACK);
    
            if (debugOnlyFirstTick) {
                tick();
                return;
            }
            
            if (debugTickLoopWithoutDelay) {
                while (!race.isAllReached()) {
                    tick();
                }
                tick();
                return;
            }
            
            
            this.scheduler.scheduleAtFixedRate(new RaceTickTask(), frameLength, frameLength, TimeUnit.MILLISECONDS);
            while (!race.isAllReached()) {
                Thread.sleep(frameLength / 2);
            }
            this.scheduler.shutdown();

            
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
