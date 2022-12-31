package hundun.simulationgame.umamusume.gameplay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2023/01/03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResourcePair {
    GameResourceType type;
    Long amount;
}
