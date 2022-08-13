package hundun.simulationgame.umamusume.core.horse;

import hundun.simulationgame.umamusume.record.base.IChineseNameEnum;

/**
 * @author hundun
 * Created on 2021/09/27
 */
public enum RunStrategyType implements IChineseNameEnum {
    FIRST("逃", 0.95, 1.0, 0.98, 0.962, 1.0, 1.0, 0.996),
    FRONT("先", 0.89, 0.991, 0.975, 0.985, 0.985, 1.0, 0.996),
    BACK("差", 1.0, 0.938, 0.994, 0.994, 0.975, 1.0, 1.0),
    TAIL("追", 0.995, 0.931, 1.0, 1.0, 0.945, 1.0, 0.997),
    ;
    
    final String chinese;

    final double initHpRate;

    final double startCruiseSpeedRate;

    final double middleCruiseSpeedRate;

    final double lastCruiseSpeedRate;

    final double startCruiseAccelerationRate;

    final double middleCruiseAccelerationRate;

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
    
    @Override
    public String getChinese() {
        return chinese;
    }
    
    public static RunStrategyType fromChinese(String target) {
        for (RunStrategyType value : values()) {
            if (value.getChinese().equals(target)) {
                return value;
            }
        }
        return null;
    }

    public double getInitHpRate() {
        return initHpRate;
    }

    public double getStartCruiseSpeedRate() {
        return startCruiseSpeedRate;
    }

    public double getMiddleCruiseSpeedRate() {
        return middleCruiseSpeedRate;
    }

    public double getLastCruiseSpeedRate() {
        return lastCruiseSpeedRate;
    }

    public double getStartCruiseAccelerationRate() {
        return startCruiseAccelerationRate;
    }

    public double getMiddleCruiseAccelerationRate() {
        return middleCruiseAccelerationRate;
    }

    public double getLastCruiseAccelerationRate() {
        return lastCruiseAccelerationRate;
    }
    
    
    
}
