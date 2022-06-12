package hundun.simulationgame.umamusume;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.horse.RunStrategyType;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.race.RaceLengthType;
import hundun.simulationgame.umamusume.race.RacePrototype;
import hundun.simulationgame.umamusume.race.RacePrototypeFactory;
import hundun.simulationgame.umamusume.race.TrackWetType;
import hundun.simulationgame.umamusume.record.IRecorder;


/**
 * @author hundun
 * Created on 2021/09/24
 */
public class UmamusumeApp {
    
    HorsePrototypeFactory factory;
    // ms
    private int frameLength = 100;
    
    
    private final IRecorder<?> displayer;
    RaceSituation raceSituation;
    
    boolean debugOnlyFirstTick = false;
    boolean debugTickLoopWithoutDelay = true;
    
    public UmamusumeApp(IRecorder<?> displayer) {
        this.displayer = displayer;
        this.factory = new HorsePrototypeFactory();
        factory.registerAllDefault();
    }
    
    
    
    public void demoRun(){
        
        raceSituation = new RaceSituation(displayer, RacePrototypeFactory.OKA_SHO, TrackWetType.GOOD);
        raceSituation.addHorse(HorsePrototypeFactory.SILENCE_SUZUKA_A, RunStrategyType.FIRST);
        raceSituation.addHorse(HorsePrototypeFactory.SPECIAL_WEEK_A, RunStrategyType.FRONT);
        raceSituation.addHorse(HorsePrototypeFactory.GRASS_WONDER_A, RunStrategyType.BACK);

        runCore();
    }
    
    public void randomRun(){
        
        try {
            
            
            raceSituation = new RaceSituation(displayer, RacePrototypeFactory.OKA_SHO, TrackWetType.GOOD);
            HorsePrototype base = HorsePrototypeFactory.SPECIAL_WEEK_A;
            
            List<HorsePrototype> randomRivals = factory.getRandomRivals(3, base, 0.2);
            randomRivals.forEach(item -> {
                raceSituation.addHorse(item, item.getDefaultRunStrategyType());
            });
            raceSituation.addHorse(base, base.getDefaultRunStrategyType());
            
            raceSituation.calculateResult();
            displayer.printRecordPackage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void runCore(){
        try {
            HorsePrototypeFactory factory = new HorsePrototypeFactory();
            factory.registerAllDefault();
            raceSituation = new RaceSituation(displayer, RacePrototypeFactory.OKA_SHO, TrackWetType.GOOD);
            HorsePrototype base = HorsePrototypeFactory.SPECIAL_WEEK_A;
            
            List<HorsePrototype> randomRivals = factory.getRandomRivals(3, base, 0.2);
            randomRivals.forEach(item -> {
                raceSituation.addHorse(item, item.getDefaultRunStrategyType());
            });
            raceSituation.addHorse(base, base.getDefaultRunStrategyType());
            
            raceSituation.calculateResult();
            displayer.printRecordPackage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
