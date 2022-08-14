package hundun.simulationgame.umamusume.record.base;

import hundun.simulationgame.umamusume.core.event.BaseEvent;
import hundun.simulationgame.umamusume.core.horse.HorseModel;
import hundun.simulationgame.umamusume.core.race.RaceSituation;

public interface IRecorder<T> {
	//public void renderHorsePosition(HorseModel horse);

    void onTick(RaceSituation situation);
    
    void onFinish(); 
    void printRecordPackage(); 
    void onEvent(BaseEvent event);
    
    void log(String msg);

    void onStart(RaceSituation raceSituation);

    RecordPackage<T> getRecordPackage();

    void onEnd(RaceSituation raceSituation);
}