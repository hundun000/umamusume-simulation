package hundun.simulationgame.umamusume.record.gui;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2022/06/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiFrameData {
    RaceInfo raceInfo;
    List<HorseInfo> horseInfos;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RaceInfo {
        private int length;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HorseInfo {
        private int trackNumber;
        private Double trackPosition;
        private Integer reachTime;
    }


}
