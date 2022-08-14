package hundun.simulationgame.umamusume.record.text;

import java.util.List;


/**
 * @author hundun
 * Created on 2022/08/10
 */
public class TextFrameData {
    String raceInfo;
    String eventInfo;
    
    public TextFrameData() {
    }
    
    public TextFrameData(String raceInfo, String eventInfo) {
        super();
        this.raceInfo = raceInfo;
        this.eventInfo = eventInfo;
    }

    public String getRaceInfo() {
        return raceInfo;
    }

    public void setRaceInfo(String raceInfo) {
        this.raceInfo = raceInfo;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }
    
    
}
