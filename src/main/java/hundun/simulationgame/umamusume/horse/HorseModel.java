package hundun.simulationgame.umamusume.horse;

import java.util.HashMap;
import java.util.Map;

import hundun.simulationgame.umamusume.UmamusumeApp;
import hundun.simulationgame.umamusume.display.IDisplayer;
import hundun.simulationgame.umamusume.race.DistanceAptitudeType;
import hundun.simulationgame.umamusume.race.RunStrategyAptitudeType;
import hundun.simulationgame.umamusume.race.RaceLengthType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class HorseModel {
    @Getter
    private HorsePrototype prototype;

    private IDisplayer displayer;
    

	// ====== input constant ======
	@Setter
    @Getter
	private int trackNumber;
	@Setter
    @Getter
    private RunStrategyType runStrategyConfig;
	@Setter
    @Getter
    private RaceLengthType raceLengthType;
	@Setter
    @Getter
    private int raceLength;

	// ====== init constant ======
	private double v0;
	private double v1;
	private double v2;
	private double v3;
	private double v4;
	private double a0;
	private double a1 = 0.5;
    private double a2 = 0.5;
    private double a3 = 0.5;
    private double a4 = 0.5;
    int hpOffset = -500;
    private DistanceAptitudeType distanceAptitudeType;
    private RunStrategyAptitudeType strategyAptitudeType;
    // ====== late-init constant ======
    @Setter(value = AccessLevel.PRIVATE)
    @Getter
    private Double sprintStartPosition;
    @Setter(value = AccessLevel.PRIVATE)
    @Getter
    private Integer reachTime;
    private Map<HorseTrackPhase, Double> hpCostRecord = new HashMap<>();
    // ====== change every frame ======
    @Getter
	private double currentSpeed;
    @Setter(value = AccessLevel.PRIVATE)
    @Getter
    private double trackPosition = 0;
    @Setter(value = AccessLevel.PRIVATE)
    @Getter
    private double currentHp;
    @Setter(value = AccessLevel.PRIVATE)
    @Getter
    private HorseTrackPhase trackPhase;
    @Setter(value = AccessLevel.PRIVATE)
    private double targetSpeed;
    @Setter(value = AccessLevel.PRIVATE)
    @Getter
    private Double currentSpeedDelta;
    @Setter(value = AccessLevel.PRIVATE)
    private int targetHpCost;
	
	public HorseModel(HorsePrototype prototype, IDisplayer displayer){
		
		this.prototype = prototype;

		this.displayer = displayer;
		
	}

	public void tickUpdate(int tickCount){

	    updateTrackPhase(tickCount);
	    if (trackPhase != HorseTrackPhase.REACHED) {
	        updateSpeed();
	        updateDistance();
	    }

	}
	
	private void updateSpeed() {
	    
	    double hpCostRate = 1.0;
        double targetHpCostPerSecond = hpCostPerSecond(currentSpeed, v0, hpCostRate);
        double targetHpCostPerTick = targetHpCostPerSecond / UmamusumeApp.tickNumPerSecond;
        boolean hpEnough = this.getCurrentHp() >= targetHpCostPerTick;
	    
	    double targetSpeedPerSecond;
	    if (hpEnough) {
	        switch (this.getTrackPhase()) {
                case START_GATE:
                    targetSpeedPerSecond = 0.85*v0;
                    break;
                case START_CRUISE:
                    targetSpeedPerSecond = v1;
                    break;
                case MIDDLE_CRUISE:
                    targetSpeedPerSecond = v2;
                    break;
                case LAST_CRUISE:
                    targetSpeedPerSecond = v3;
                    break;
                case LAST_SPRINT:
                    targetSpeedPerSecond = v4;
                    break;
                default:
                    targetSpeedPerSecond = 0;
                    break;
            }
	    } else {
	        targetSpeedPerSecond = (0.85 * v0);
	    }
        

        int speedDeltaSign;
        if (Math.abs(targetSpeedPerSecond - currentSpeed) < 0.000001) {
            speedDeltaSign = 0;
        } else {
            speedDeltaSign = targetSpeedPerSecond - currentSpeed > 0 ? 1 : -1;
        }
        
        Double currentSpeedDeltaPerSecond;
        if (speedDeltaSign != 0) {
            double absA;
            switch (this.getTrackPhase()) {
                case START_GATE:
                    absA = a0;
                    break;
                case START_CRUISE:
                    absA = a1;
                    break;
                case MIDDLE_CRUISE:
                    absA = a2;
                    break;
                case LAST_CRUISE:
                    absA = a3;
                    break;
                case LAST_SPRINT:
                    absA = a4;
                    break;
                default:
                    absA = 0;
                    break;
            }
            currentSpeedDeltaPerSecond = Math.signum(targetSpeedPerSecond - currentSpeed) * absA;
            double currentSpeedDeltaPerTick = currentSpeedDeltaPerSecond / UmamusumeApp.tickNumPerSecond;
            
            if (currentSpeedDeltaPerSecond > 0) {
                targetSpeedPerSecond = Math.min(currentSpeed + currentSpeedDeltaPerTick, targetSpeedPerSecond);
            } else {
                targetSpeedPerSecond = Math.max(currentSpeed + currentSpeedDeltaPerTick, targetSpeedPerSecond);
            }
        } else {
            currentSpeedDeltaPerSecond = null;
            targetSpeedPerSecond = currentSpeed;
        }
        
        currentSpeed = targetSpeedPerSecond;
        if (hpEnough) {
            currentHp -= targetHpCostPerTick;
            hpCostRecord.merge(trackPhase, targetHpCostPerTick, (oldValue, newValue) -> oldValue + newValue);
        } else {
            currentHp = 0;
        }

        //this.setHpEnough(hpEnough);
        this.setCurrentSpeedDelta(currentSpeedDeltaPerSecond);
        this.setTargetSpeed(targetSpeedPerSecond);
    }

    private void updateTrackPhase(int tickCount) {
	    HorseTrackPhase newPhase = null;
        switch (trackPhase) {
            case START_GATE:
                if (trackPosition >= this.getRaceLength() * 0.2) {
                    newPhase = HorseTrackPhase.MIDDLE_CRUISE;
                } else if (Math.abs(currentSpeed - 0.85*v0) < 0.000001) {
                    newPhase = HorseTrackPhase.START_CRUISE;
                }
                break;
            case START_CRUISE:
                if (trackPosition >= this.getRaceLength() * 0.2) {
                    newPhase = HorseTrackPhase.MIDDLE_CRUISE;
                }
                break;
            case MIDDLE_CRUISE:
                if (trackPosition >= this.getRaceLength() * (2.0/3.0)) {
                    newPhase = HorseTrackPhase.LAST_CRUISE;
                }
                break;
            case LAST_CRUISE:
                if (sprintStartPosition == null) {
                    sprintStartPosition = calculateSprintStartPosition();
                    displayer.log(prototype.getName() + " sprintStartPosition init as " + sprintStartPosition);
                }
                if (trackPosition >= sprintStartPosition) {
                    newPhase = HorseTrackPhase.LAST_SPRINT;
                } else if (trackPosition >= this.getRaceLength()) {
                    newPhase = HorseTrackPhase.REACHED;
                    this.reachTime = tickCount;
                }
                break;
            case LAST_SPRINT:
                if (trackPosition >= this.getRaceLength()) {
                    newPhase = HorseTrackPhase.REACHED;
                    this.reachTime = tickCount;
                }
                break;
            default:
                break;
        }
        
        if (newPhase != null) {
            trackPhase = newPhase;
            displayer.log(prototype.getName() + " tick " + tickCount + " change phase to " + newPhase);
        }
        
    }

    private Double calculateSprintStartPosition() {
        double lastSprintHpCostRate = 1.5;
        double v4Arg = (v4-v0+12) * (v4-v0+12) / (12*12);
        double v3Arg = (v3-v0+12) * (v3-v0+12) / (12*12);
        double numerator = currentHp - (this.getRaceLength()/(3*v3)) * 20 * lastSprintHpCostRate * v3Arg;
        double denominator = 20 * lastSprintHpCostRate * (v4Arg/v4 - v3Arg/v3);
        double case1Result = denominator != 0 ? (numerator / denominator) : 1;
        double case2Result = this.getRaceLength() / 3.0;
        double sprintLength = Math.min(case1Result, case2Result);
        double sprintStartPosition = this.getRaceLength() - sprintLength;
        return sprintStartPosition;
    }

    
    
    public void lateInitMore() {
        this.currentHp = this.calculateStartHp(prototype, this.getRaceLength());
        this.trackPhase = HorseTrackPhase.START_GATE;
        
        this.currentSpeed = 0.3;
        this.distanceAptitudeType = prototype.getDistanceAptitudes().get(raceLengthType);
        
        v0 = publicBaseSpeed(this.getRaceLength());
        v1 = startCruisePhaseTargetSpeed(v0, prototype.getBaseWisdom());
        v2 = middleCruisePhaseTargetSpeed(v0, prototype.getBaseWisdom());
        v3 = lastCruisePhaseTargetSpeed(v0, prototype.getBaseWisdom(), prototype.getBaseSpeed());
        v4 = lastSprintPhaseTargetSpeed(v0, prototype.getBaseWisdom(), prototype.getBaseSpeed());
        
        a0 = 24 + 0.0006*Math.sqrt(500.0*prototype.getBasePower());
        
        
        
        String msg = prototype.getName()
                + "\n脚质 = " + getRunStrategyConfig().name
                + "\nhp = " + currentHp
                + "\nv0 = " + v0
                + "\nv1 = " + v1
                + "\nv2 = " + v2
                + "\nv3 = " + v3
                + "\nv4 = " + v4
                + "\na0 = " + a0
                + "\na1 = " + a1
                + "\na2 = " + a2
                + "\na3 = " + a3
                + "\na4 = " + a4
                + "\n"
                ;
        displayer.log(msg);
    }
    
    
    private void updateDistance() {

        
        double currentSpeedPerTick = currentSpeed / UmamusumeApp.tickNumPerSecond;

        
        
        this.setTrackPosition(this.getTrackPosition() + currentSpeedPerTick);
        
    }
    
    protected double publicBaseSpeed(int raceLength) { 
        return (20 - 1.0 * (raceLength - 2000) / 1000);
    }
    
    protected double startCruisePhaseTargetSpeed(double v0, int horseIntelligence) { 
        double part1 = this.getRunStrategyConfig().getStartCruiseSpeedRate() * v0;
        double part2 = ((horseIntelligence/5500.0) * Math.log10(horseIntelligence/10.0) - 0.65/2) * 0.01 * v0;
        return part1 + part2;
    }
    
    protected double middleCruisePhaseTargetSpeed(double v0, int horseIntelligence) { 
        double part1 = this.getRunStrategyConfig().getMiddleCruiseSpeedRate() * v0;
        double part2 = ((horseIntelligence/5500.0) * Math.log10(horseIntelligence/10.0) - 0.65/2) * 0.01 * v0;
        return part1 + part2;
    }
    
    protected double lastCruisePhaseTargetSpeed(double v0, int horseIntelligence, int horseSpeed) { 
        double part1 = this.getRunStrategyConfig().getLastCruiseSpeedRate() * v0;
        // TODO
        double TEMP_distanceRate = 1.0;
        double part2 = Math.sqrt(horseSpeed * 500.0) * TEMP_distanceRate * 0.002;
        double part3 = ((horseIntelligence/5500.0) * Math.log10(horseIntelligence * 0.1) - 0.65/2) * 0.01 * v0;
        
        return part1 + part2 + part3;
    }
    
    protected double lastSprintPhaseTargetSpeed(double v0, int horseIntelligence, int horseSpeed) { 
        double part1_1 = (this.getRunStrategyConfig().getLastCruiseSpeedRate() + 0.01) * v0;
        // TODO
        double TEMP_distanceRate = 1.0;
        double part1_2 = Math.sqrt(horseSpeed * 500.0) * TEMP_distanceRate * 0.002;
        double part1 = (part1_1 + part1_2) * 1.05;
        double part2 = part1_2;
        return part1 + part2;
    }
    
    protected double hpCostPerSecond(double v, double v0, double hpCostRate) { 
        double TEMP_groundRate = 1.0;
        return 20 * hpCostRate * (v-v0+12)*(v-v0+12)/144.0 * TEMP_groundRate;
    }
    
    private double calculateStartHp(HorsePrototype prototype, int length) {
        double baseHp = length + 0.8 * prototype.getBaseStamina() * this.getRunStrategyConfig().getInitHpRate();
        double withOffsetHp = baseHp * (1 + hpOffset/10000.0);
        return withOffsetHp;
    }
	

	
}

