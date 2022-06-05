package hundun.simulationgame.umamusume.display.text;

import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.record.IRecorder;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class SimpleTextDisplayer {

    //@Override
    public void onTick(RaceSituation situation) {
        //System.out.println("TextDisplayer::tick " + situation.getTickCount());
        for (HorseModel horse : situation.getHorses()) {
            if (horse.getReachTime() != null) {
                //System.out.println("TextDisplayer:: " + horse.getPrototype().getName() + " reached at " + horse.getReachTime());
            } else {
                //System.out.println("TextDisplayer:: " + horse.getPrototype().getName() + " pos " + horse.getTrackPosition());
            }
        }
    }

}
