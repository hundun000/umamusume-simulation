package hundun.simulationgame.umamusume.core.horse;

import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/09/30
 */
public enum RunStrategyAptitudeType {
    S(1.10),
    A(1.00),
    B(0.85),
    C(0.75),
    D(0.6),
    E(0.4),
    F(0.2),
    G(0.1),
    ;

    @Getter
    final double rate;

    private RunStrategyAptitudeType(double rate) {
        this.rate = rate;
    }

}
