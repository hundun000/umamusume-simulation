package hundun.simulationgame.umamusume.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2022/06/23
 */

public class RecordPackage<T> {
    @Setter
    @Getter
    private RecordNode<T> startNode;
    @Setter
    @Getter
    private EndRecordNode endNode;
    @Getter
    private List<RecordNode<T>> nodes = new ArrayList<>();
    @Getter
    private List<String> logs = new ArrayList<>();
    
    public void log(String msg) {
        logs.add(msg);
    }
    
    public void addNode(int tick, String timeText, T content) {
        nodes.add(new RecordNode<T>(tick, timeText, content));
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecordNode<T> {
        private int tick;
        private String timeText;
        private T content;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EndRecordNode {
        private Map<String, Integer> horseReachTickMap;
    }
}
