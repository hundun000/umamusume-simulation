package hundun.simulationgame.umamusume.event;
/**
 * @author hundun
 * Created on 2021/10/14
 */
public class HorseSprintStartPositionSetEvent extends BaseEvent {

    double sprintStartPosition;
    double currentHp;
    
    public HorseSprintStartPositionSetEvent(int tick, double sprintStartPosition, double currentHp) {
        super(tick);
        this.sprintStartPosition = sprintStartPosition;
        this.currentHp = currentHp;
    }

}
