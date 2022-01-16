package hundun.simulationgame.umamusume.event;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/10/12
 */
@Data
public abstract class BaseEvent {
    protected int tick;
    protected String description;
    
    public BaseEvent(int tick) {
        super();
        this.tick = tick;
    }
    
    
}
