package hundun.simulationgame.umamusume.record;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2022/06/23
 */

public class RecordPackage<T> {
    @Getter
    private List<RecordNode<T>> nodes = new ArrayList<>();
    @Getter
    private List<String> logs = new ArrayList<>();
    
    public void log(String msg) {
        logs.add(msg);
    }
    
    public void addNode(int tick, T content) {
        nodes.add(new RecordNode<T>(tick, content));
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecordNode<T> {
        private int tick;
        private T content;
    }
}
