package hundun.simulationgame.umamusume.record.gui;

import java.util.List;

import hundun.simulationgame.umamusume.core.race.RacePrototype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2022/06/24
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiFrameData {
    RaceInfo raceInfo;
    List<HorseInfo> horseInfos;
    String eventInfo;
    Double cameraPosition;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RaceInfo {
        private int length;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HorseInfo {
        private int trackNumber;
        private Double trackPosition;
        private Double cameraOffsetPosition;
        private Integer reachTime;
    }


}
