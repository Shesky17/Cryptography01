package Encrypte;

import javax.swing.*;

/**
 *@author Shesky
 */

public class EncoderDriver
{	
	public EncoderDriver() 
	{
		//frame
		JFrame frame = new JFrame("Encoder V1.2");
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    //panel
	    JPanel panel = new EncoderPanel();
	    panel.setSize(400,300);
	       
	    //unresizable
	    frame.setResizable(false);
	    
	    //add panel
	    frame.add(panel);
	    
	    //display
	    frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		new EncoderDriver();
	}
	
}
