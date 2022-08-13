package hundun.simulationgame.umamusume.record.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2022/06/23
 */

public class RecordPackage<T> {

    private StartRecordNode<T> startNode;
    public StartRecordNode<T> getStartNode() {
        return startNode;
    }
    public void setStartNode(StartRecordNode<T> startNode) {
        this.startNode = startNode;
    }
    
    private EndRecordNode endNode;
    public EndRecordNode getEndNode() {
        return endNode;
    }
    public void setEndNode(EndRecordNode endNode) {
        this.endNode = endNode;
    }
    
    private List<RecordNode<T>> nodes = new ArrayList<>();
    public List<RecordNode<T>> getNodes() {
        return nodes;
    }
    private List<String> logs = new ArrayList<>();
    public List<String> getLogs() {
        return logs;
    }
    
    public void log(String msg) {
        logs.add(msg);
    }
    
    
    
    public void addNode(int tick, String timeText, T content) {
        nodes.add(new RecordNode<T>(tick, timeText, content));
    }
    
    public static class RecordNode<T> {
        private int tick;
        private String timeText;
        private T content;
        public RecordNode(int tick, String timeText, T content) {
            super();
            this.tick = tick;
            this.timeText = timeText;
            this.content = content;
        }
        public RecordNode() {
            super();
        }
        public int getTick() {
            return tick;
        }
        public void setTick(int tick) {
            this.tick = tick;
        }
        public String getTimeText() {
            return timeText;
        }
        public void setTimeText(String timeText) {
            this.timeText = timeText;
        }
        public T getContent() {
            return content;
        }
        public void setContent(T content) {
            this.content = content;
        }
        
    }
    
    public static class StartRecordNode<T> {
        RecordNode<T> normalPart;
        private Map<String, String> runStrategyTextMap = new HashMap<>();
        
        public StartRecordNode(RecordNode<T> normalPart, Map<String, String> runStrategyTextMap) {
            super();
            this.normalPart = normalPart;
            this.runStrategyTextMap = runStrategyTextMap;
        }
        
        public RecordNode<T> getNormalPart() {
            return normalPart;
        }
        public void setNormalPart(RecordNode<T> normalPart) {
            this.normalPart = normalPart;
        }
        
        
        public Map<String, String> getRunStrategyTextMap() {
            return runStrategyTextMap;
        }
        public void setRunStrategyTextMap(Map<String, String> runStrategyTextMap) {
            this.runStrategyTextMap = runStrategyTextMap;
        }
    }
    
    
    public static class EndRecordNode {
        private List<EndRecordHorseInfo> horseInfos;

        public EndRecordNode(List<EndRecordHorseInfo> horseInfos) {
            super();
            this.horseInfos = horseInfos;
        }

        public EndRecordNode() {
            super();
        }

        public List<EndRecordHorseInfo> getHorseInfos() {
            return horseInfos;
        }
        
        public void setHorseInfos(List<EndRecordHorseInfo> horseInfos) {
            this.horseInfos = horseInfos;
        }
        
        public static class EndRecordHorseInfo {
            String horseName;
            int reachTick;
            String reachTimeText;
            
            
            public EndRecordHorseInfo() {
                super();
            }
            
            public EndRecordHorseInfo(String horseName, int reachTick, String reachTimeText) {
                super();
                this.horseName = horseName;
                this.reachTick = reachTick;
                this.reachTimeText = reachTimeText;
                
            }

            public int getReachTick() {
                return reachTick;
            }

            public void setReachTick(int reachTick) {
                this.reachTick = reachTick;
            }

            public String getReachTimeText() {
                return reachTimeText;
            }

            public void setReachTimeText(String reachTimeText) {
                this.reachTimeText = reachTimeText;
            }

            public String getHorseName() {
                return horseName;
            }

            public void setHorseName(String horseName) {
                this.horseName = horseName;
            }
            
            
        }
    }
}
