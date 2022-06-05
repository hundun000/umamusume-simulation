package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorseTrackPhase;
import hundun.simulationgame.umamusume.race.Race;
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
    
    public HorseTrackPhaseChangeEvent(Race race, HorsePrototype horse, HorseTrackPhase from, HorseTrackPhase to) {
        super(race);
        this.from = from;
        this.to = to;
        
        setDescription(horse.getName() + " 阶段变化：" + from.getChinese() + " -> " + to.getChinese());
    }
    
    public String allDoneDescription(){
        return "所有马 " + "阶段变化：" + from.getChinese() + " -> " + to.getChinese();
    }
    
}
