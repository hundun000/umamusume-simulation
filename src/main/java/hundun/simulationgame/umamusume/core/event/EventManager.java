package hundun.simulationgame.umamusume.core.event;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.record.base.IRecorder;

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
        HorseSprintStartPositionSetEvent event = new HorseSprintStartPositionSetEvent(raceSituation, prototype, sprintStartPosition, currentHp);
        displayer.onEvent(event);
    }
    
    public void newHorseTrackPhaseChangeEvent(HorsePrototype prototype, HorseTrackPhase oldPhase,
            HorseTrackPhase newPhase) {
        HorseTrackPhaseChangeEvent event = new HorseTrackPhaseChangeEvent(raceSituation, prototype, oldPhase, newPhase);
        displayer.onEvent(event);
    }

}
