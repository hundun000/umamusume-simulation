package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.race.RaceSituation;

/**
 * @author hundun
 * Created on 2021/10/12
 */
public abstract class BaseEvent {
    protected final RaceSituation situation;
    public RaceSituation getSituation() {
        return situation;
    }
    
    public BaseEvent(RaceSituation situation) {
        super();
        this.situation = situation;
    }
    
}
