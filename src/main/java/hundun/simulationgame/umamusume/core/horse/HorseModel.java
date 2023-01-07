package hundun.simulationgame.umamusume.core.horse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import hundun.simulationgame.umamusume.core.event.EventManager;
import hundun.simulationgame.umamusume.core.event.HorseTrackPhaseChangeEvent;
import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;
import hundun.simulationgame.umamusume.core.race.TrackWetType;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt.NumberFormat;
import hundun.simulationgame.umamusume.game.nogameplay.NoGameplayApp;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import lombok.Getter;
import lombok.Setter;

public class HorseModel {
    
    private EventManager eventManager;
    

	// ====== construct-init constant ======
    @Getter
    private HorsePrototype prototype;
    @Getter
    @Setter
	private int trackNumber;

    @Getter
    @Setter
    private RunStrategyType runStrategyType;
    
    @Setter
	private RacePrototype racePrototype;

    @Setter
    private TrackWetType trackWetType;

	// ====== post-construct-init constant ======
	
	private int buffedSpeed;
    private int buffedStamina;
    private int buffedPower;
    private int buffedGuts;
    private int buffedWisdom;
	private double v0;
	private double v1;
	private double v2;
	private double v3;
	private double v4;
	private double a0;
	private double a1;
    private double a2;
    private double a3;
    private double a4;
    int hpOffset = -500;
    double hpCostRate = 1.0;
    // FIXME
    double lastHpCostRate = 1.53;
    private DistanceAptitudeType distanceAptitudeType;
    private RunStrategyAptitudeType runStrategyAptitudeType;
    private TrackGroundAptitudeType trackGroundAptitudeType;
    // ====== runtime-init constant ======
    @Getter
    private Double sprintStartPosition;
    
    @Getter
    private Integer reachTime;

    
    private Map<HorseTrackPhase, Double> hpCostRecord = new LinkedHashMap<>();
    private Map<HorseTrackPhase, Integer> tickCostRecord = new HashMap<>();
    // ====== change every frame ======
    @Getter
	private Double currentSpeed;

    @Getter
    private Double trackPosition;

    @Getter
    private double currentHp;

    @Getter
    private HorseTrackPhase trackPhase;

    @Getter
    private Double targetSpeed;

    private Boolean hpEnough;
    private Double targetHpCost;

    @Getter
    private Double currentAcceleration;
    
	public HorseModel(HorsePrototype prototype, EventManager eventManager){
		
		this.prototype = prototype;

		this.eventManager = eventManager;
		
	}

	public void tickUpdate(int tickCount){

	    if (trackPhase != HorseTrackPhase.REACHED) {
	        updateTargetHpCost();
	        updateTargetSpeed();
	        updateAcceleration();
	        updateSpeed();
	        updateDistanceAndHp();
	    }
	    
	    updateTrackPhase(tickCount);
	}
	private void updateTargetSpeed() {
	    double newTargetSpeed;
        if (hpEnough) {
            switch (this.getTrackPhase()) {
                case START_GATE:
                    newTargetSpeed = 0.85*v0;
                    break;
                case START_CRUISE:
                    newTargetSpeed = v1;
                    break;
                case MIDDLE_CRUISE:
                case MIDDLE_CRUISE_HALF:
                    newTargetSpeed = v2;
                    break;
                case LAST_CRUISE:
                    newTargetSpeed = v3;
                    break;
                case LAST_SPRINT:
                    newTargetSpeed = v4;
                    break;
                default:
                    newTargetSpeed = 0;
                    break;
            }
        } else {
            newTargetSpeed = (0.85 * v0);
        }
        this.targetSpeed = (newTargetSpeed);
    }

    private void updateTargetHpCost() {
	    
        double targetHpCostPerSecond;
        switch (this.getTrackPhase()) {
            case START_GATE:
                targetHpCostPerSecond = startGateHpCostPerSecond(hpCostRate, trackWetType, racePrototype.getGroundType());
                break;
            case LAST_CRUISE:
            case LAST_SPRINT:
                targetHpCostPerSecond = normalHpCostPerSecond(currentSpeed, v0, hpCostRate * lastHpCostRate, trackWetType, racePrototype.getGroundType());
                break;
            default: 
                targetHpCostPerSecond = normalHpCostPerSecond(currentSpeed, v0, hpCostRate, trackWetType, racePrototype.getGroundType());
                break;
        }
        targetHpCost = targetHpCostPerSecond / RaceSituation.tickNumPerGameSecond;
        hpEnough = this.getCurrentHp() >= targetHpCost;
        
        
	}
	
	

    

    private void updateAcceleration() {
	    
        int accelerationSign;
        if (Math.abs(targetSpeed - currentSpeed) < 0.000001) {
            accelerationSign = 0;
        } else {
            accelerationSign = targetSpeed - currentSpeed > 0 ? 1 : -1;
        }
        
        Double newAcceleration;
        if (accelerationSign != 0) {
            double absAcceleration;
            switch (this.getTrackPhase()) {
                case START_GATE:
                    absAcceleration = a0;
                    break;
                case START_CRUISE:
                    absAcceleration = a1;
                    break;
                case MIDDLE_CRUISE:
                case MIDDLE_CRUISE_HALF:
                    absAcceleration = a2;
                    break;
                case LAST_CRUISE:
                    absAcceleration = a3;
                    break;
                case LAST_SPRINT:
                    absAcceleration = a4;
                    break;
                default:
                    absAcceleration = 0;
                    break;
            }
            newAcceleration = accelerationSign * absAcceleration;
            
        } else {
            newAcceleration = null;
        }
        
        
        this.currentAcceleration = (newAcceleration);
	}

    private void updateSpeed() {
        double newSpeed;
	    if (currentAcceleration != null) {
            double currentAccelerationPerTick = currentAcceleration / RaceSituation.tickNumPerGameSecond;
            
            if (currentAcceleration > 0) {
                newSpeed = Math.min(currentSpeed + currentAccelerationPerTick, targetSpeed);
            } else {
                newSpeed = Math.max(currentSpeed + currentAccelerationPerTick, targetSpeed);
            }
        } else {
            newSpeed = currentSpeed;
        }
        
        this.currentSpeed = (newSpeed);

    }

    private void updateTrackPhase(int tickCount) {
	    HorseTrackPhase newPhase = null;
        switch (trackPhase) {
            case START_GATE:
                if (trackPosition >= racePrototype.getLength() * (1.0/6.0)) {
                    newPhase = HorseTrackPhase.MIDDLE_CRUISE;
                } else if (Math.abs(currentSpeed - 0.85*v0) < 0.000001) {
                    newPhase = HorseTrackPhase.START_CRUISE;
                }
                break;
            case START_CRUISE:
                if (trackPosition >= racePrototype.getLength() * (1.0/6.0)) {
                    newPhase = HorseTrackPhase.MIDDLE_CRUISE;
                }
                break;
            case MIDDLE_CRUISE:
                if (trackPosition >= racePrototype.getLength() * (2.5/6.0)) {
                    newPhase = HorseTrackPhase.MIDDLE_CRUISE_HALF;
                }
                break;
            case MIDDLE_CRUISE_HALF:
                if (trackPosition >= racePrototype.getLength() * (4.0/6.0)) {
                    newPhase = HorseTrackPhase.LAST_CRUISE;
                }
                break;
            case LAST_CRUISE:
                if (sprintStartPosition == null) {
                    sprintStartPosition = calculateSprintStartPosition();
                    eventManager.newHorseSprintStartPositionSetEvent(prototype, sprintStartPosition, currentHp);
                }
                if (trackPosition >= sprintStartPosition) {
                    newPhase = HorseTrackPhase.LAST_SPRINT;
                } else if (trackPosition >= racePrototype.getLength()) {
                    newPhase = HorseTrackPhase.REACHED;
                    handleReach(tickCount);
                }
                break;
            case LAST_SPRINT:
                if (trackPosition >= racePrototype.getLength()) {
                    newPhase = HorseTrackPhase.REACHED;
                    handleReach(tickCount);
                }
                break;
            default:
                break;
        }
        
        if (newPhase != null) {
            eventManager.newHorseTrackPhaseChangeEvent(prototype, trackPhase, newPhase);
            trackPhase = newPhase;
        }
        
        if (trackPhase != HorseTrackPhase.REACHED) {
            tickCostRecord.merge(trackPhase, 1, (oldValue, newValue) -> oldValue + newValue);
        }
        
    }

    private void handleReach(Integer tickCount) {
        this.reachTime = tickCount;
        String msg = "ReachReport:\n";
        msg += "hpCostRecord = " + hpCostRecord.toString() + "\n";
        msg += "tickCostRecord = " + tickCostRecord.toString() + "\n";
        eventManager.log(msg);
    }

    private Double calculateSprintStartPosition() {
        double v4Arg = (v4-v0+12) * (v4-v0+12) / (12*12);
        double v3Arg = (v3-v0+12) * (v3-v0+12) / (12*12);
        double numerator = currentHp - (racePrototype.getLength()/(3*v3)) * 20 * lastHpCostRate * v3Arg;
        double denominator = 20 * lastHpCostRate * (v4Arg/v4 - v3Arg/v3);
        double case1Result = denominator != 0 ? (numerator / denominator) : 1;
        double case2Result = racePrototype.getLength() / 3.0;
        double sprintLength = Math.min(case1Result, case2Result);
        double sprintStartPosition = racePrototype.getLength() - sprintLength;
        return sprintStartPosition;
    }

    
    
    public void lateInitMore() {
        
        this.trackPhase = HorseTrackPhase.START_GATE;
        this.trackPosition = 0.0;
        this.currentSpeed = 3.0;
        
        this.currentHp = HorseModel.calculateStartHp(prototype, racePrototype.getLength(), runStrategyType, hpOffset);
        this.distanceAptitudeType = prototype.getDistanceAptitudes().get(racePrototype.getLengthType());
        this.runStrategyAptitudeType = prototype.getRunStrategyAptitudes().get(runStrategyType);
        this.trackGroundAptitudeType = prototype.getTrackGroundAptitudes().get(racePrototype.getGroundType());
        double TEMP_emotionRate = 1.0;
        
        this.buffedSpeed = (int)(prototype.getBaseSpeed() * TEMP_emotionRate) + trackWetType.getSpeedOffSetMap().get(racePrototype.getGroundType());
        this.buffedStamina = (int)(prototype.getBaseStamina() * TEMP_emotionRate);
        this.buffedPower = (int)(prototype.getBasePower() * TEMP_emotionRate) + trackWetType.getPowerOffSetMap().get(racePrototype.getGroundType());
        this.buffedGuts = (int)(prototype.getBaseGuts() * TEMP_emotionRate);
        this.buffedWisdom = (int)(prototype.getBaseWisdom() * TEMP_emotionRate * runStrategyAptitudeType.getRate());
        
        
        v0 = publicBaseSpeed(racePrototype.getLength());
        v1 = startCruisePhaseTargetSpeed(v0, buffedWisdom, runStrategyType);
        v2 = middleCruisePhaseTargetSpeed(v0, buffedWisdom, runStrategyType);
        v3 = lastCruisePhaseTargetSpeed(v0, buffedWisdom, buffedSpeed, runStrategyType, distanceAptitudeType);
        v4 = lastSprintPhaseTargetSpeed(v0, buffedWisdom, buffedSpeed, runStrategyType, distanceAptitudeType);
        
        a0 = startGatePhaseAcceleration(v0, buffedPower, runStrategyType, trackGroundAptitudeType, distanceAptitudeType);
        a1 = startCruisePhaseAcceleration(v0, buffedPower, runStrategyType, trackGroundAptitudeType, distanceAptitudeType);
        a2 = middleCruisePhaseAcceleration(v0, buffedPower, runStrategyType, trackGroundAptitudeType, distanceAptitudeType);
        a3 = lastPhaseAcceleration(v0, buffedPower, runStrategyType, trackGroundAptitudeType, distanceAptitudeType);
        a4 = a3;
        
        NumberFormat formatter = NumberFormat.getFormat(1, 3);
        StringBuilder builder = new StringBuilder();
        builder.append("Track").append(trackNumber).append("\n");
        builder.append(prototype.getName()).append(", ");
        builder.append("").append(runStrategyType.chinese).append(runStrategyAptitudeType).append("\n");
        builder.append("buffedSpeed = ").append(buffedSpeed).append(", ");
        builder.append("buffedStamina = ").append(buffedStamina).append(", ");
        builder.append("buffedPower = ").append(buffedPower).append(", ");
        builder.append("buffedGuts = ").append(buffedGuts).append(", ");
        builder.append("buffedWisdom = ").append(buffedWisdom).append("\n");
        builder.append("v0 = ").append(formatter.format(v0)).append(", ");
        builder.append("v1 = ").append(formatter.format(v1)).append(", ");
        builder.append("v2 = ").append(formatter.format(v2)).append(", ");
        builder.append("v3 = ").append(formatter.format(v3)).append(", ");
        builder.append("v4 = ").append(formatter.format(v4)).append("\n");
        builder.append("a0 = ").append(formatter.format(a0)).append(", ");
        builder.append("a1 = ").append(formatter.format(a1)).append(", ");
        builder.append("a2 = ").append(formatter.format(a2)).append(", ");
        builder.append("a3 = ").append(formatter.format(a3)).append(", ");
        builder.append("a4 = ").append(formatter.format(a4)).append("\n");
        
        
        eventManager.log(builder.toString());
    }
    
    
    

    private void updateDistanceAndHp() {

        double currentSpeedPerTick = currentSpeed / RaceSituation.tickNumPerGameSecond;
        this.trackPosition = (this.getTrackPosition() + currentSpeedPerTick);
        
        if (hpEnough) {
            currentHp -= targetHpCost;
            hpCostRecord.merge(trackPhase, targetHpCost, (oldValue, newValue) -> oldValue + newValue);
        } else {
            currentHp = 0.0;
        }
        
    }
    
    private static double publicBaseSpeed(int raceLength) { 
        return (20 - 1.0 * (raceLength - 2000) / 1000);
    }
    
    private static double startCruisePhaseTargetSpeed(double v0, int horseIntelligence, RunStrategyType runStrategyType) { 
        double part1 = runStrategyType.getStartCruiseSpeedRate() * v0;
        double part2 = ((horseIntelligence/5500.0) * Math.log10(horseIntelligence/10.0) - 0.65/2) * 0.01 * v0;
        return part1 + part2;
    }
    
    private static double startGatePhaseAcceleration(double v0, int horsePower, RunStrategyType runStrategyType, TrackGroundAptitudeType trackGroundAptitudeType, DistanceAptitudeType distanceAptitudeType) { 
        double part1 = 24.0;
        double allRate = runStrategyType.getStartCruiseAccelerationRate() * trackGroundAptitudeType.getRate() * distanceAptitudeType.getAccelerationRate();
        double part2 = 0.0006 * Math.sqrt(500*horsePower) * allRate;
        return part1 + part2;
    }
    
    private static double startCruisePhaseAcceleration(double v0, int horsePower, RunStrategyType runStrategyType, TrackGroundAptitudeType trackGroundAptitudeType, DistanceAptitudeType distanceAptitudeType) { 
        double allRate = runStrategyType.getStartCruiseAccelerationRate() * trackGroundAptitudeType.getRate() * distanceAptitudeType.getAccelerationRate();
        double part2 = 0.0006 * Math.sqrt(500*horsePower) * allRate;
        return part2;
    }
    
    
    private static double middleCruisePhaseTargetSpeed(double v0, int horseIntelligence, RunStrategyType runStrategyType) { 
        double part1 = runStrategyType.getMiddleCruiseSpeedRate() * v0;
        double part2 = ((horseIntelligence/5500.0) * Math.log10(horseIntelligence/10.0) - 0.65/2) * 0.01 * v0;
        return part1 + part2;
    }
    
    private static double middleCruisePhaseAcceleration(double v02, int horsePower, RunStrategyType runStrategyType, TrackGroundAptitudeType trackGroundAptitudeType, DistanceAptitudeType distanceAptitudeType) {
        double allRate = runStrategyType.getMiddleCruiseAccelerationRate() * trackGroundAptitudeType.getRate() * distanceAptitudeType.getAccelerationRate();
        double part2 = 0.0006 * Math.sqrt(500*horsePower) * allRate;
        return part2;
    }
    
    private static double lastCruisePhaseTargetSpeed(double v0, int horseIntelligence, int horseSpeed, RunStrategyType runStrategyType, DistanceAptitudeType distanceAptitudeType) { 
        double part1 = runStrategyType.getLastCruiseSpeedRate() * v0;
        double part2 = Math.sqrt(horseSpeed * 500.0) * distanceAptitudeType.getSpeedRate() * 0.002;
        double part3 = ((horseIntelligence/5500.0) * Math.log10(horseIntelligence * 0.1) - 0.65/2) * 0.01 * v0;
        
        return part1 + part2 + part3;
    }
    
    private static double lastSprintPhaseTargetSpeed(double v0, int horseIntelligence, int horseSpeed, RunStrategyType runStrategyType, DistanceAptitudeType distanceAptitudeType) { 
        double part1_1 = (runStrategyType.getLastCruiseSpeedRate() + 0.01) * v0;
        double part1_2 = Math.sqrt(horseSpeed * 500.0) * distanceAptitudeType.getSpeedRate() * 0.002;
        double part1 = (part1_1 + part1_2) * 1.05;
        double part2 = part1_2;
        return part1 + part2;
    }
    
    private static double lastPhaseAcceleration(double v02, int horsePower, RunStrategyType runStrategyType, TrackGroundAptitudeType trackGroundAptitudeType, DistanceAptitudeType distanceAptitudeType) {
        double allRate = runStrategyType.getLastCruiseAccelerationRate() * trackGroundAptitudeType.getRate() * distanceAptitudeType.getAccelerationRate();
        double part2 = 0.0006 * Math.sqrt(500*horsePower) * allRate;
        return part2;
    }
    
    private static double normalHpCostPerSecond(double v, double v0, double hpCostRate, TrackWetType trackWetType, TrackGroundType trackGroundType) { 
        return 20 * hpCostRate * (v-v0+12)*(v-v0+12)/144.0 * trackWetType.getHpRateMap().get(trackGroundType);
    }
    
    private static double startGateHpCostPerSecond(double hpCostRate, TrackWetType trackWetType, TrackGroundType trackGroundType) {
        return 20 * hpCostRate * trackWetType.getHpRateMap().get(trackGroundType);
    }

    
    private static double calculateStartHp(HorsePrototype prototype, int length, RunStrategyType runStrategyType, int hpOffset) {
        double baseHp = length + 0.8 * prototype.getBaseStamina() * runStrategyType.getInitHpRate();
        double withOffsetHp = baseHp * (1 + hpOffset/10000.0);
        return withOffsetHp;
    }
	

	
}

