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

import hundun.simulationgame.umamusume.display.GUIDisplayer;
import hundun.simulationgame.umamusume.display.IDisplayer;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.horse.RunStrategyType;
import lombok.Getter;
import lombok.Setter;

public class Race {

	IDisplayer displayer;
	
	
	@Getter
    private List<HorseModel> horses = new ArrayList<>();

    @Getter
    @Setter
    private int tickCount = 0;
    
    @Getter
    @Setter
    private RaceLengthType lengthType;
    @Getter
    @Setter
    private int length;
	
	public Race(IDisplayer displayer, RaceLengthType lengthType, int length) {
	    this.displayer = displayer;
	    
		this.setLengthType(lengthType);
		setLength(length);
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
	public void addHorse(HorsePrototype prototype, RunStrategyType runStrategyType){
	    int trackNumber = this.getHorses().size();

	    HorseModel model = new HorseModel(prototype, displayer);
	    model.setRaceLengthType(this.getLengthType());
	    model.setRaceLength(this.getLength());
        model.setTrackNumber(trackNumber);
        model.setRunStrategyConfig(runStrategyType);
        model.lateInitMore();
	    this.getHorses().add(model);
	}
	
	public boolean isAllReached() {
        boolean allDone = horses.stream().filter(horse -> horse.getTrackPhase() != HorseTrackPhase.REACHED).findFirst().isEmpty();
        return allDone;
    }

    public void tickUpdate() {

        this.setTickCount(this.getTickCount() + 1);
        for(HorseModel horse: this.getHorses()){
            horse.tickUpdate(this.getTickCount());
        }

    }	

}
