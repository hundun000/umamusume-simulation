/*
 * Author: Younus Mahmood
 * Race.java
 */

package hundun.simulationgame.umamusume.race;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import hundun.simulationgame.umamusume.display.IDisplayer;
import hundun.simulationgame.umamusume.display.gui.GUIDisplayer;
import hundun.simulationgame.umamusume.event.EventManager;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.horse.RunStrategyType;
import lombok.Getter;
import lombok.Setter;

public class Race {

    private IDisplayer displayer;
    private EventManager eventManager;
	
	// ====== construct-init constant ======
	@Getter
    private RacePrototype prototype;
    private TrackWetType trackWetType;
	// ====== post-construct-init constant ======
	@Getter
    private List<HorseModel> horses = new ArrayList<>();
	
	// ====== change every frame ======
    @Getter
    @Setter
    private int tickCount = 0;

	
	public Race(IDisplayer displayer, RacePrototype prototype, TrackWetType trackWetType) {
	    this.displayer = displayer;
	    this.prototype = prototype;
	    this.trackWetType = trackWetType;
	    
	    this.eventManager = new EventManager(this, displayer);
	}
	
//	public void setupRace() throws InterruptedException{	
//		
//		
//		Race race = new Race();
//		race.EnrollOneHorse("Michael Jordan",0,22,new EarlySprintStrategy());
//		race.EnrollOneHorse("Kevin Durant",1,24,new SlowStartStrategy());
//		race.EnrollOneHorse("LeBron James",2,25,new EarlySprintStrategy());
//		race.EnrollOneHorse("Stephen Curry",3,25,new SlowStartStrategy());
//		race.EnrollOneHorse("Derrick Rose",4,25,new SteadyRunStrategy());	
//		
//	}
	
	/**
	 * This method is used by the test case to enroll one horse in the race.
	 * 
	 * @param horseName
	 * @param horseNumber
	 * @param maxSpeed
	 * @param strategy
	 * @param horsePositions
	 * @param flag
	 */
	public void addHorse(HorsePrototype horsePrototype, RunStrategyType runStrategyType){
	    int trackNumber = this.getHorses().size();

	    HorseModel model = new HorseModel(horsePrototype, eventManager);
	    model.setRacePrototype(prototype);
	    model.setTrackWetType(trackWetType);
        model.setTrackNumber(trackNumber);
        model.setRunStrategyType(runStrategyType);
        model.lateInitMore();
	    this.getHorses().add(model);
	}
	
	public boolean isAllReached() {
        boolean allDone = horses.stream().filter(horse -> horse.getTrackPhase() != HorseTrackPhase.REACHED).findFirst().isEmpty();
        return allDone;
    }

    private void tickUpdate() {

        this.setTickCount(this.getTickCount() + 1);
        for(HorseModel horse: this.getHorses()){
            horse.tickUpdate(this.getTickCount());
        }

    }	

    public void calculateResult() {
        displayer.onStart(this);
        while (!this.isAllReached()) {
            this.tickUpdate();

            if (displayer != null) {
                displayer.onTick(this);
                if (this.isAllReached()) {
                    displayer.onFinish();
                }
            }
        }  
    }
    
}
