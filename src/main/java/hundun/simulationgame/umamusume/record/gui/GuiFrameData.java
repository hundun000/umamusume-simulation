package hundun.simulationgame.umamusume.record.gui;

import java.util.List;

/**
 * @author hundun
 * Created on 2022/06/24
 */
public class GuiFrameData {
    RaceInfo raceInfo;
    List<HorseInfo> horseInfos;
    
    public GuiFrameData(RaceInfo raceInfo, List<HorseInfo> horseInfos) {
        super();
        this.raceInfo = raceInfo;
        this.horseInfos = horseInfos;
    }

    public RaceInfo getRaceInfo() {
        return raceInfo;
    }

    public List<HorseInfo> getHorseInfos() {
        return horseInfos;
    }
    
    public static class RaceInfo {
        private int length;

        public RaceInfo(int length) {
            super();
            this.length = length;
        }

        public int getLength() {
            return length;
        }
    }
    
    public static class HorseInfo {
        private int trackNumber;
        private Double trackPosition;
        private Integer reachTime;
        
        public HorseInfo(int trackNumber, Double trackPosition, Integer reachTime) {
            super();
            this.trackNumber = trackNumber;
            this.trackPosition = trackPosition;
            this.reachTime = reachTime;
        }

        public int getTrackNumber() {
            return trackNumber;
        }

        public Double getTrackPosition() {
            return trackPosition;
        }

        public Integer getReachTime() {
            return reachTime;
        }
        
        
    }


}
