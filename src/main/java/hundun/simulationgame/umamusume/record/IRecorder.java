package hundun.simulationgame.umamusume.record;

import hundun.simulationgame.umamusume.event.BaseEvent;
import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.race.RaceSituation;

public interface IRecorder<T> {
	//public void renderHorsePosition(HorseModel horse);

    void onTick(RaceSituation situation);
    
    void onFinish(); 
    void printRecordPackage(); 
    void onEvent(BaseEvent event);
    
    void log(String msg);

    void onStart(RaceSituation raceSituation);

    RecordPackage<T> getRecordPackage();
}