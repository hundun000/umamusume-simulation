package hundun.simulationgame.umamusume.record.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.record.raw.GuiFrameData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2022/06/23
 */
@Data
public class RecordPackage<T> {

    private StartRecordNode<T> startNode;
    
    private EndRecordNode endNode;
    
    private List<RecordNode<T>> nodes = new ArrayList<>();

    private List<String> logs = new ArrayList<>();

    
    public void addLog(String msg) {
        logs.add(msg);
    }
    
    public void addNode(int tick, String timeText, T content) {
        nodes.add(new RecordNode<T>(tick, timeText, content));
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordNode<T> {
        private int tick;
        private String timeText;
        private T content;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StartRecordNode<T> {
        RecordNode<T> normalPart;
        private Map<String, String> runStrategyTextMap = new HashMap<>();
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EndRecordNode {
        private List<EndRecordHorseInfo> horseInfos;
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class EndRecordHorseInfo {
            String horseName;
            int reachTick;
            String reachTimeText;
        }
    }
}
