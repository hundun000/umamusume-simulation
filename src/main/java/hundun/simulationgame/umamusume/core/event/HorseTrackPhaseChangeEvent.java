package hundun.simulationgame.umamusume.core.event;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.core.race.RaceSituation;

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
