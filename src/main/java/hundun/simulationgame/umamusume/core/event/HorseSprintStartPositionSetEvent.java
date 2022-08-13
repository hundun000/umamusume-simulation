package hundun.simulationgame.umamusume.core.event;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.race.RaceSituation;

/**
 * @author hundun
 * Created on 2021/10/14
 */
public class HorseSprintStartPositionSetEvent extends BaseEvent {
    HorsePrototype horse;
    double sprintStartPosition;
    double currentHp;
    
    public HorseSprintStartPositionSetEvent(RaceSituation raceSituation, HorsePrototype horse, double sprintStartPosition, double currentHp) {
        super(raceSituation);
        this.horse = horse;
        this.sprintStartPosition = sprintStartPosition;
        this.currentHp = currentHp;
    }

    public HorsePrototype getHorse() {
        return horse;
    }

    public double getSprintStartPosition() {
        return sprintStartPosition;
    }

    public double getCurrentHp() {
        return currentHp;
    }

    
}
