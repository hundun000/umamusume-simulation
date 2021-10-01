package hundun.simulationgame.umamusume.race;

import lombok.Getter;

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
    @Getter
    final double speedRate;
    @Getter
    final double speedDeltaRate;
    
    private DistanceAptitudeType(double speedRate, double speedDeltaRate) {
        this.speedRate = speedRate;
        this.speedDeltaRate = speedDeltaRate;
    }
}
