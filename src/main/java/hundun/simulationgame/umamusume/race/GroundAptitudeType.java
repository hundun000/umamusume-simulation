package hundun.simulationgame.umamusume.race;

import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/09/30
 */
public enum GroundAptitudeType {
    S(1.05),
    A(1.00),
    B(0.9),
    C(0.8),
    D(0.7),
    E(0.5),
    F(0.3),
    G(0.1),
    ;
    @Getter
    final double rate;

    private GroundAptitudeType(double rate) {
        this.rate = rate;
    }
    
    
    
    
}
