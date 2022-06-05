package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.race.RaceSituation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/10/12
 */
@Getter
public abstract class BaseEvent {
    protected final RaceSituation situation;
    
    public BaseEvent(RaceSituation situation) {
        super();
        this.situation = situation;
    }
    
}
