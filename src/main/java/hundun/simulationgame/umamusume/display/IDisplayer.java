package hundun.simulationgame.umamusume.display;

import hundun.simulationgame.umamusume.event.BaseEvent;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.race.Race;

public interface IDisplayer {
	//public void renderHorsePosition(HorseModel horse);

    void onTick(Race situation);

    default void onFinish() {
        System.out.println("[onFinish]");
    };
    
    default void onEvent(BaseEvent event){
        System.out.println("[GameEvent]" + event.getDescription());
    }
    default void log(String msg) {
        System.out.println("[GameLog]" + msg);
    }

    default void onStart(Race race) {
        System.out.println("[onStart]");
    };

}