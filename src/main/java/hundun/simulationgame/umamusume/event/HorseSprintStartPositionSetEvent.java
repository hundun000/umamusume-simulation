package hundun.simulationgame.umamusume.event;

import hundun.simulationgame.umamusume.race.Race;

/**
 * @author hundun
 * Created on 2021/10/14
 */
public class HorseSprintStartPositionSetEvent extends BaseEvent {

    double sprintStartPosition;
    double currentHp;
    
    public HorseSprintStartPositionSetEvent(Race race, double sprintStartPosition, double currentHp) {
        super(race);
        this.sprintStartPosition = sprintStartPosition;
        this.currentHp = currentHp;
    }

}
