package hundun.simulationgame.umamusume.display.gui;

import javax.swing.JFrame;

import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.record.IRecorder;


public class GUIDisplayer {
	RaceTrackPanel raceTrack;
	public GUIDisplayer(){
		JFrame f = new JFrame("BedRock RaceTrack");
		f.setSize(1300, 300);
		raceTrack = new RaceTrackPanel(5);
		f.add(raceTrack);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);	 
	}


    //@Override
    public void onTick(RaceSituation situation) {
        raceTrack.renderRaceSituation(situation);
    }

	
}
