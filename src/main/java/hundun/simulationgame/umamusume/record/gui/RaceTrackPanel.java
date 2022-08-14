package hundun.simulationgame.umamusume.record.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import hundun.simulationgame.umamusume.core.horse.HorseModel;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.HorseInfo;


public class RaceTrackPanel extends JPanel{
	
    private static final long serialVersionUID = 1L;
    Rectangle[] horsePositions;  // Represents the horse
	Color[] jockeyColors;  // Horse colors
	int horseCount;

	Integer[] winningTimes;

	
	/**
	 * Constructor.  Called by GUIDisplay.java
	 * Constructs the race track and racing horses
	 * @param horseCount
	 */
	public RaceTrackPanel(int horseCount){
		 horsePositions = new Rectangle[horseCount];
		 winningTimes = new Integer[horseCount];
		 for(int j = 0; j < horseCount; j++){
			 horsePositions[j] = new Rectangle();
			 horsePositions[j].setLocation(10,10+(j*50));
			 winningTimes[j] = null;
		 }
	     this.horseCount = horseCount;
		 jockeyColors = new Color[horseCount];	 
		 initColors();

		
	}

	
	/**
	 * Establishes 6 possible jockey colors.  Add more here if you want to increase
	 * race size above 6
	 */
	public void initColors(){
		jockeyColors = new Color[6];
		jockeyColors[0] = Color.RED;
		jockeyColors[1] = Color.ORANGE;
		jockeyColors[2] = Color.GREEN;
		jockeyColors[3] = Color.CYAN;
		jockeyColors[4] = Color.YELLOW;
		jockeyColors[5] = Color.red;		
	}
	
	 /* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setFont(new Font("TimesRoman", Font.BOLD, 24));
		 g2.setPaint(Color.WHITE);
		 g2.setColor(Color.WHITE);
		 for(int j = 0; j < horseCount; j++){
			 g2.fillRect (0, horsePositions[j].y, horsePositions[j].x,25);
		 }
		 g2.setColor(Color.BLACK);
		 
		 g2.drawLine(1000,10,1000,250);
		 for(int j = 0; j < horseCount; j++){
			 g2.setPaint(jockeyColors[j]);
			 g2.fillRect (horsePositions[j].x, horsePositions[j].y, 25, 25);
			 g2.setColor(Color.BLACK);
			 g2.drawString(Integer.toString(j), horsePositions[j].x+10,horsePositions[j].y+20);
			 if (winningTimes[j] != null) {            
	             g2.setColor(Color.BLACK);
	             g2.drawString("done at " + winningTimes[j], horsePositions[j].x+30, horsePositions[j].y+20);//horsePosition[winningHorse].y, horsePosition[winningHorse].x+50);
	         }
			 // System.out.println("New position " + horsePosition[j].x + " " +horsePosition[j].y);
		 }
		 
		 repaint();
    }






    public void renderRecordNode(GuiFrameData guiFrameData) {
        for (HorseInfo horse : guiFrameData.getHorseInfos()) {
            double uiPosition = horse.getTrackPosition() * 1000 / guiFrameData.getRaceInfo().getLength();
            horsePositions[horse.getTrackNumber()].x = (int) uiPosition;
            
            if (horse.getReachTime() != null) {
                winningTimes[horse.getTrackNumber()] = horse.getReachTime();
            }
        }
        
        validate();
        repaint();
        
    }


}
