package hundun.simulationgame.umamusume.display.text;

import hundun.simulationgame.umamusume.display.IDisplayer;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.race.Race;

/**
 * @author hundun
 * Created on 2021/09/25
 */
public class SimpleTextDisplayer implements IDisplayer {

    @Override
    public void onTick(Race situation) {
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
