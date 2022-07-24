package hundun.simulationgame.umamusume.record.text;

import java.util.List;

import hundun.simulationgame.umamusume.record.gui.GuiFrameData;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.GuiFrameDataBuilder;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.HorseInfo;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.RaceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2022/08/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextFrameData {
    String raceInfo;
    String eventInfo;
}
