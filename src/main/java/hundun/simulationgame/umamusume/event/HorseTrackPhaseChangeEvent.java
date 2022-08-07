package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.RaceSituation;

/**
 * @author hundun
 * Created on 2021/10/12
 */
public class HorseTrackPhaseChangeEvent extends BaseEvent {
    private HorsePrototype horse;
    private HorseTrackPhase from;
    private HorseTrackPhase to;
    
    public HorseTrackPhaseChangeEvent(RaceSituation raceSituation, HorsePrototype horse, HorseTrackPhase from, HorseTrackPhase to) {
        super(raceSituation);
        this.horse = horse;
        this.from = from;
        this.to = to;
    }

    public HorsePrototype getHorse() {
        return horse;
    }

    public HorseTrackPhase getFrom() {
        return from;
    }

    public HorseTrackPhase getTo() {
        return to;
    }

    
}
