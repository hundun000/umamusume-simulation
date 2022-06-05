package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.record.IRecorder;

/**
 * @author hundun
 * Created on 2022/06/22
 */
public class EventManager {

    RaceSituation raceSituation;
    IRecorder<?> displayer;
    
    public EventManager(RaceSituation raceSituation, IRecorder<?> displayer) {
        super();
        this.raceSituation = raceSituation;
        this.displayer = displayer;
    }

    public void log(String string) {
        displayer.log(string);
    }

    public void newHorseSprintStartPositionSetEvent(HorsePrototype prototype, double sprintStartPosition, double currentHp) {
        var event = new HorseSprintStartPositionSetEvent(raceSituation, prototype, sprintStartPosition, currentHp);
        displayer.onEvent(event);
    }
    
    public void newHorseTrackPhaseChangeEvent(HorsePrototype prototype, HorseTrackPhase oldPhase,
            HorseTrackPhase newPhase) {
        var event = new HorseTrackPhaseChangeEvent(raceSituation, prototype, oldPhase, newPhase);
        displayer.onEvent(event);
    }

}
