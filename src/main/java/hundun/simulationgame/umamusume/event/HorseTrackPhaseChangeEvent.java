package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hundun
 * Created on 2021/10/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HorseTrackPhaseChangeEvent extends BaseEvent {
    
    private HorseTrackPhase from;
    private HorseTrackPhase to;
    
    public HorseTrackPhaseChangeEvent(int tick, HorsePrototype horse, HorseTrackPhase from, HorseTrackPhase to) {
        super(tick);
        this.from = from;
        this.to = to;
        
        setDescription(horse.getName() + "阶段变化：" + from + "->" + to);
    }
    
}
