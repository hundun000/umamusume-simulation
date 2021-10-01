package hundun.simulationgame.umamusume.display;

import javax.swing.JFrame;

import hundun.simulationgame.umamusume.horse.HorseModel;
import hundun.simulationgame.umamusume.race.Race;


public class GUIDisplayer implements IDisplayer {
	RaceTrackPanel raceTrack;
	public GUIDisplayer(){
		JFrame f = new JFrame("BedRock RaceTrack");
		f.setSize(1300, 300);
		raceTrack = new RaceTrackPanel(5);
		f.add(raceTrack);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);	 
	}


    @Override
    public void renderRaceSituation(Race situation) {
        raceTrack.renderRaceSituation(situation);
    }

	
}
