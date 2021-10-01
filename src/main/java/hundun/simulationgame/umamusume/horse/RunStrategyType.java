package hundun.simulationgame.umamusume.horse;

import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/09/27
 */
public enum RunStrategyType {
    FIRST("逃", 0.95, 1.0, 0.98, 0.962),
    FRONT("先", 0.89, 0.991, 0.975, 0.985),
    BACK("差", 1.0, 0.938, 0.994, 0.994),
    TAIL("追", 0.995, 0.931, 1.0, 1.0),
    ;
    @Getter
    final String name;
    @Getter
    final double initHpRate;
    @Getter
    final double startCruiseSpeedRate;
    @Getter
    final double middleCruiseSpeedRate;
    @Getter
    final double lastCruiseSpeedRate;
    
    private RunStrategyType(
            String name, 
            double initHpRate, 
            double startCruiseSpeedRate,
            double middleCruiseSpeedRate, 
            double lastCruiseSpeedRate) {
        this.name = name;
        this.initHpRate = initHpRate;
        this.startCruiseSpeedRate = startCruiseSpeedRate;
        this.middleCruiseSpeedRate = middleCruiseSpeedRate;
        this.lastCruiseSpeedRate = lastCruiseSpeedRate;
    }
    
    
    
}
