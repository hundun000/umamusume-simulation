package hundun.simulationgame.umamusume.game.gameplay.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author hundun
 * Created on 2023/01/04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainRuleConfig {
    List<GameResourcePair> costList; 
    List<GameResourcePair> gainList;
}
