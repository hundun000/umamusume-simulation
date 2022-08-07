package hundun.simulationgame.umamusume.race;


import java.util.ArrayList;
import java.util.List;
import hundun.simulationgame.umamusume.event.EventManager;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.horse.RunStrategyType;
import hundun.simulationgame.umamusume.record.IRecorder;

public class RaceSituation {
    public static final int tickNumPerGameSecond = 100;
    
    private IRecorder<?> displayer;
    private EventManager eventManager;
	
	// ====== construct-init constant ======
    private RacePrototype prototype;
	public RacePrototype getPrototype() {
        return prototype;
    }
    private TrackWetType trackWetType;
	// ====== post-construct-init constant ======
    private List<HorseModel> horses = new ArrayList<>();
	public List<HorseModel> getHorses() {
        return horses;
    }
	// ====== change every frame ======
    private int tickCount = 0;
    public int getTickCount() {
        return tickCount;
    }
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }
	
	public RaceSituation(IRecorder<?> displayer, RacePrototype prototype, TrackWetType trackWetType) {
	    this.displayer = displayer;
	    this.prototype = prototype;
	    this.trackWetType = trackWetType;
	    
	    this.eventManager = new EventManager(this, displayer);
	}
	
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
        boolean allDone = !horses.stream().filter(horse -> horse.getTrackPhase() != HorseTrackPhase.REACHED).findFirst().isPresent();
        return allDone;
    }

    private void tickUpdate() {

        this.setTickCount(this.getTickCount() + 1);
        for(HorseModel horse: this.getHorses()){
            horse.tickUpdate(this.getTickCount());
        }
        if (displayer != null) {
            displayer.onTick(this);
            if (this.isAllReached()) {
                displayer.onFinish();
            }
        }
    }	

    public void calculateResult() {
        displayer.onStart(this);
        while (!this.isAllReached()) {
            this.tickUpdate();
        }  
        displayer.onEnd(this);
    }
    
    public static double tickCountToSecond(int tickCount) {
        return 1.0 * tickCount / tickNumPerGameSecond;
    }
    
    
}
