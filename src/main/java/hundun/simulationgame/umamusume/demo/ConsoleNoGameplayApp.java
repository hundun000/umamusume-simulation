package hundun.simulationgame.umamusume.demo;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import hundun.simulationgame.umamusume.core.horse.HorseModel;
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.core.race.TrackWetType;
import hundun.simulationgame.umamusume.game.nogameplay.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.game.nogameplay.RacePrototypeFactory;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage;
import hundun.simulationgame.umamusume.record.raw.GuiFrameData;
import hundun.simulationgame.umamusume.record.raw.GuiFrameRecorder;


/**
 * 区别于IGameplayFrontend。NoGameplayApp自由指定比赛属性和赛马属性，然后计算比赛结果。
 * @author hundun
 * Created on 2021/09/24
 */
public class ConsoleNoGameplayApp {
    
    HorsePrototypeFactory factory;
    private final IRaceRecorder<?> displayer;
    RaceSituation raceSituation;
    
    public ConsoleNoGameplayApp(IRaceRecorder<?> displayer) {
        this.displayer = displayer;
        this.factory = new HorsePrototypeFactory();
        factory.registerAllDefault();
    }
    
    
    
    public void demoRun(){
        
        raceSituation = new RaceSituation(displayer, RacePrototypeFactory.OKA_SHO, TrackWetType.GOOD);
        raceSituation.addHorse(HorsePrototypeFactory.SILENCE_SUZUKA_A, RunStrategyType.FIRST);
        raceSituation.addHorse(HorsePrototypeFactory.SPECIAL_WEEK_A, RunStrategyType.FRONT);
        raceSituation.addHorse(HorsePrototypeFactory.GRASS_WONDER_A, RunStrategyType.BACK);

        raceSituation.calculateResult();
        displayer.printRecordPackage();
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

    

}
