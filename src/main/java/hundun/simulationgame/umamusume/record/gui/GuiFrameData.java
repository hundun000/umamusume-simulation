package hundun.simulationgame.umamusume.record.gui;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2022/06/24
 */
@Getter
@Builder
@AllArgsConstructor
public class GuiFrameData {
    RaceInfo raceInfo;
    List<HorseInfo> horseInfos;
    
    @Getter
    @Builder
    @AllArgsConstructor
    public static class RaceInfo {
        private int length;
    }
    
    @Getter
    @Builder
    @AllArgsConstructor
    public static class HorseInfo {
        private int trackNumber;
        private Double trackPosition;
        private Integer reachTime;
    }
}
