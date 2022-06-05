package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.display.IDisplayer;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.Race;

/**
 * @author hundun
 * Created on 2022/06/22
 */
public class EventManager {

    Race race;
    IDisplayer displayer;
    
    public EventManager(Race race, IDisplayer displayer) {
        super();
        this.race = race;
        this.displayer = displayer;
    }

    public void log(String string) {
        displayer.log(string);
    }

    public void newHorseTrackPhaseChangeEvent(HorsePrototype prototype, HorseTrackPhase oldPhase,
            HorseTrackPhase newPhase) {
        var event = new HorseTrackPhaseChangeEvent(race, prototype, oldPhase, newPhase);
        displayer.onEvent(event);
    }

}
