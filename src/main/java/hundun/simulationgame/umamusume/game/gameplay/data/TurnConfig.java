package hundun.simulationgame.umamusume.game.gameplay.data;

import java.util.List;
import java.util.Map;

import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnConfig {
    private RacePrototype race;
    private List<HorsePrototype> rivalHorses;
    private Map<Integer, Integer> rankToAwardMap;
}
