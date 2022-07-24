package hundun.simulationgame.umamusume.horse;

import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/09/27
 */
public enum RunStrategyType {
    FIRST("逃", 0.95, 1.0, 0.98, 0.962, 1.0, 1.0, 0.996),
    FRONT("先", 0.89, 0.991, 0.975, 0.985, 0.985, 1.0, 0.996),
    BACK("差", 1.0, 0.938, 0.994, 0.994, 0.975, 1.0, 1.0),
    TAIL("追", 0.995, 0.931, 1.0, 1.0, 0.945, 1.0, 0.997),
    ;
    @Getter
    final String chinese;
    @Getter
    final double initHpRate;
    @Getter
    final double startCruiseSpeedRate;
    @Getter
    final double middleCruiseSpeedRate;
    @Getter
    final double lastCruiseSpeedRate;
    @Getter
    final double startCruiseAccelerationRate;
    @Getter
    final double middleCruiseAccelerationRate;
    @Getter
    final double lastCruiseAccelerationRate;
    
    private RunStrategyType(String name, double initHpRate, double startCruiseSpeedRate, double middleCruiseSpeedRate,
            double lastCruiseSpeedRate, double startCruiseAccelerationRate, double middleCruiseAccelerationRate,
            double lastCruiseAccelerationRate) {
        this.chinese = name;
        this.initHpRate = initHpRate;
        this.startCruiseSpeedRate = startCruiseSpeedRate;
        this.middleCruiseSpeedRate = middleCruiseSpeedRate;
        this.lastCruiseSpeedRate = lastCruiseSpeedRate;
        this.startCruiseAccelerationRate = startCruiseAccelerationRate;
        this.middleCruiseAccelerationRate = middleCruiseAccelerationRate;
        this.lastCruiseAccelerationRate = lastCruiseAccelerationRate;
    }
    
    
    
    public static RunStrategyType fromChinese(String target) {
        for (RunStrategyType value : values()) {
            if (value.getChinese().equals(target)) {
                return value;
            }
        }
        return null;
    }
    
    
    
}
