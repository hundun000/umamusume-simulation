package hundun.simulationgame.umamusume.display;

import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.race.Race;

public interface IDisplayer {
	//public void renderHorsePosition(HorseModel horse);

    void renderRaceSituation(Race situation);

    default void log(String msg) {
        System.out.println("[GameLog]" + msg);
    }

}