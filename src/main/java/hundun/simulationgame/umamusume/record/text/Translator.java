package hundun.simulationgame.umamusume.record.text;

import java.util.HashMap;
import java.util.Map;

import hundun.simulationgame.umamusume.record.base.IChineseNameEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2023/01/04
 */
public class Translator {
    @Getter
    @Setter
    private Map<String, String> enumChineseToOtherLanguageMap = new HashMap<>();
    @Getter
    @Setter
    private Map<String, String> textChineseToOtherLanguageMap = new HashMap<>();
    
    public static class Factory {
        
        public static Translator emptyAsChinese() {
            Translator result = new Translator();
            return result;
        }
        
        public static Translator english() {
            Translator result = new Translator();
            
            result.textChineseToOtherLanguageMap.put("赛道", "track");
            result.textChineseToOtherLanguageMap.put("速", "speed");
            result.textChineseToOtherLanguageMap.put("耐", "stamina");
            result.textChineseToOtherLanguageMap.put("力", "power");
            result.textChineseToOtherLanguageMap.put("根", "guts");
            result.textChineseToOtherLanguageMap.put("智", "wisdom");
            result.textChineseToOtherLanguageMap.put("进入%s阶段", "into %s phase");
            result.textChineseToOtherLanguageMap.put("%s%s", "%s %s");
            result.textChineseToOtherLanguageMap.put("%s最晚%s", "%s %s lastly");
            result.textChineseToOtherLanguageMap.put("%s率先%s", "%s %s firstly");
            result.textChineseToOtherLanguageMap.put("冲线时间：%s", "reached at: %s");
            
            result.enumChineseToOtherLanguageMap.put("逃", "first");
            result.enumChineseToOtherLanguageMap.put("先", "front");
            result.enumChineseToOtherLanguageMap.put("差", "back");
            result.enumChineseToOtherLanguageMap.put("追", "tail");
            
            result.enumChineseToOtherLanguageMap.put("出闸", "start-gate");
            result.enumChineseToOtherLanguageMap.put("初期巡航", "start-cruise");
            result.enumChineseToOtherLanguageMap.put("中期巡航", "mid-cruise-1");
            result.enumChineseToOtherLanguageMap.put("中期巡航(过半)", "mid-cruise-2");
            result.enumChineseToOtherLanguageMap.put("末期巡航", "last-cruise");
            result.enumChineseToOtherLanguageMap.put("末期冲刺", "last-sprint");
            result.enumChineseToOtherLanguageMap.put("冲线", "reached");
            return result;
        }
    }
    
    public String get(IChineseNameEnum enumValue) {
        return enumChineseToOtherLanguageMap.getOrDefault(enumValue.getChinese(), enumValue.getChinese());
    }
    
    public String get(String chinese) {
        return textChineseToOtherLanguageMap.getOrDefault(chinese, chinese);
    }
    
    @Data
    public static class StrategyPackage {
        private String horseStatusTemplate = "${NAME_PART} "
                + "${SPEED_KEY}${SPEED_VALUE}, "
                + "${STAMINA_KEY}${STAMINA_VALUE}, "
                + "${POWER_KEY}${POWER_VALUE}, "
                + "${GUTS_KEY}${GUTS_VALUE}, "
                + "${WISDOM_KEY}${WISDOM_VALUE}";
        
        private String horseRaceStartTemplate = "${TRACK_PART}: ${HORSE_STATUS_PART}\n";
        private String horseRunningTemplate = "${HORSE_ICON} ${ARROW}\n";
        
        private String horseReachedTemplate = "${HORSE_ICON} ${REACH_TEXT}\n";
        
        private int horsePositionBarMaxWidth = 10;
        private int cameraProcessBarWidth = 10;
        private String cameraProcessBarChar1 = "=";
        private String cameraProcessBarChar2 = "o";
        private String cameraProcessBarChar3 = " ";
        
        public static class Factory {
            
            public static void toLongWidth(StrategyPackage strategyPackage) {
                strategyPackage.setHorsePositionBarMaxWidth(30);
                strategyPackage.setCameraProcessBarWidth(25);
            }

            public static void toCameraProcessBarCharType2(StrategyPackage strategyPackage) {
                strategyPackage.setCameraProcessBarChar1("█");
                strategyPackage.setCameraProcessBarChar2("▓");
                strategyPackage.setCameraProcessBarChar3("░");
            }
        }
        
    }
}
