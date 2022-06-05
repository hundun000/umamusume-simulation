package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.race.Race;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/10/12
 */
@Getter
@Setter
public abstract class BaseEvent {
    protected final Race situation;
    protected String description;
    
    public BaseEvent(Race situation) {
        super();
        this.situation = situation;
    }
    
}
