package hundun.simulationgame.umamusume.horse;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorsePrototypeFactory;

/**
 * @author hundun
 * Created on 2022/06/22
 */
public class HorsePrototypeFactoryTest {

    static HorsePrototypeFactory factory;
    
    @BeforeClass
    public static void init() {
        factory = new HorsePrototypeFactory();
        factory.registerAllDefault();
    }
    
    @Test
    public void testRandomRival() {
        int testTime = 5;
        for (int i = 0; i < testTime; i++) {
            List<HorsePrototype> result = factory.getRandomRivals(2, HorsePrototypeFactory.SPECIAL_WEEK_A, 0.1);
            System.out.println("RandomRival = " + result.stream().map(entry -> entry.getName()).collect(Collectors.toList()));
        }
        
        
    }

}
