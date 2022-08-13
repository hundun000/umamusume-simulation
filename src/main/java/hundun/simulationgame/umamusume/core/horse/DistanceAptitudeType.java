package hundun.simulationgame.umamusume.core.horse;


/**
 * @author hundun
 * Created on 2021/09/30
 */
public enum DistanceAptitudeType {
    S(1.05, 1.0),
    A(1.00, 1.0),
    B(0.9, 1.0),
    C(0.8, 1.0),
    D(0.7, 1.0),
    E(0.5, 0.6),
    F(0.3, 0.5),
    G(0.1, 0.4),
    ;

    final double speedRate;

    final double accelerationRate;
    
    private DistanceAptitudeType(double speedRate, double accelerationRate) {
        this.speedRate = speedRate;
        this.accelerationRate = accelerationRate;
    }

    public double getSpeedRate() {
        return speedRate;
    }

    public double getAccelerationRate() {
        return accelerationRate;
    }
    
    
}
