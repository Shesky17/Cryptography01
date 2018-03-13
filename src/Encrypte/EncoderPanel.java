package Encrypte;

import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.Base64;

/**
 * @author Shesky
 * version 1.2
 * Trying to make button reset the label
 * ENTER button still unavailiable
 */
public class EncoderPanel extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -406342686223995117L;
	
	private ArrayList<String> alphabet = new ArrayList<String>();
	private ArrayList<String> shiftedAlphabet = new ArrayList<String>();
	private String alphabetStr = new String("abcdefghijklmnopqrstuvwxyz ");
	private String shiftedAlphabetStr = new String("`~!@#$%^&*()_-+=|,<.>/?123 ");//("xyzabcdefghijklmnopqrstuvw*");
	private String emptySpace = new String(" ");

	private ArrayList<String> shifted = new ArrayList<String>();
	private ArrayList<String> shiftedOutput = new ArrayList<String>();

	private ArrayList<String> inOrder = new ArrayList<String>();
	private ArrayList<String> reverseOrder = new ArrayList<String>();
	
	private String finalOutput = "";
	
	private String base64Final = "";
	
	//random var parts
	private Random random;
	private String firstRnd = "";
	private String lastTwoRnd = "";
	private int rnd = generateRnd();
	
	
	JButton encrypteBtn;
	JTextArea inputArea;
	JLabel inputLabel;
	JLabel outputLabel;	
	JLabel encryptedMessageLabel;
	
	Writer writer = null;
	
	/*
	 * default constructor
	 * for GUI
	 */
	public EncoderPanel()
	{	  	  		
		firstRnd = alphabetStr.substring(rnd,rnd+1); //generate 1 letter
		lastTwoRnd = alphabetStr.substring(rnd,rnd+2); //generate 2 letters
		
		//add key listener
	    this.addKeyListener(this);
	    this.setFocusable(true);
		
		inputLabel = new JLabel("Input: ");
		inputLabel.setFont(new Font("Serif", Font.BOLD, 27));
		this.add(inputLabel);
		
		inputArea = new JTextArea(1,10);
		this.add(inputArea);
		
		encrypteBtn = new JButton("Encode");
		this.add(encrypteBtn);
		
		//encryptedMessageLabel
		encryptedMessageLabel = new JLabel("Encoded msg: ");
		this.add(encryptedMessageLabel);
		encryptedMessageLabel.setFont(new Font("Serif", Font.BOLD, 27));
		
		//outputLabel
		outputLabel = new JLabel();
		this.add(outputLabel);
		outputLabel.setFont(new Font("Serif", Font.BOLD, 27));
		
		encrypteBtn.addActionListener(new encryptListener());
	}
	
	/*
	 * add the string to arraylist
	 * reverse the arraylist
	 * add random space amoung the reverse arraylist
	 */
	public String encrypte(String str)
	{	
		clear();
		
		//add the string to arraylist
		for(int i=0; i<str.length(); i++)
		{
			inOrder.add(str.substring(i, i+1));
		}
		
		//reverse
		for(int i=0; i<inOrder.size(); i++)
		{
			reverseOrder.add(0, inOrder.get(i));
		}
		
		//shift
		for(int i=0; i<reverseOrder.size(); i++)
		{
			shiftedOutput.add(shift(reverseOrder).get(i));
		}
		
		//add null letters(rnd)
		//1 at the front
		//2 at the back
		shiftedOutput.add(0, firstRnd);
		shiftedOutput.add(shiftedOutput.size(), lastTwoRnd); 
		
		//return in base64 encoded string form
		for(int i=0; i<shiftedOutput.size(); i++)
		{
			finalOutput += shiftedOutput.get(i);
		}		
		/*
		base64Final = Base64.getEncoder().encodeToString(finalOutput.getBytes());
		return base64Final;
		*/
		return finalOutput;
	}
	
	//generate a txt file with encoded msg
	private void generateFile()
	{
		try 
		{
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Encoder Message.txt"), "utf-8"));
		    writer.write(finalOutput);//(base64Final);
		} 
		catch (IOException ex) 
		{
		  // report
		} 
		finally 
		{
		   try 
		   {
			   writer.close();
		   } 
		   catch (Exception ex)
		   {
			   /*ignore*/
		   }
		}
		
	}
	
	/*
	 * Actionlistener
	 */
	private class encryptListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
	    {
	    	outputLabel.setText(encrypte(inputArea.getText().toLowerCase()));
	    	outputLabel.setFont(new Font("Serif", Font.BOLD, 27));
	    	generateFile();
	    }
	}
	
	/*
	 * Clear method
	 */
	public void clear()
	{
		rnd = generateRnd();
		firstRnd = alphabetStr.substring(rnd,rnd+1); //generate 1 letter again, clear original one
		lastTwoRnd = alphabetStr.substring(rnd,rnd+2); //generate 2 letters, clear original one
		
		shifted.clear();
		shiftedOutput.clear();
		inOrder.clear();
		reverseOrder.clear();
		finalOutput = "";
	}
	
	
	/*
	 * shifting
	 */
	public ArrayList<String> shift(ArrayList<String> list)
	{
		//add letters
		for(int i=0; i< 26; i++)
		{
			alphabet.add(alphabetStr.substring(i, i+1));
			shiftedAlphabet.add(shiftedAlphabetStr.substring(i, i+1));
		}
		
		for(int i=0; i< list.size(); i++)
		{
			for(int j=0; j<alphabet.size(); j++)
			{
				if(list.get(i).compareTo(alphabet.get(j))==0)
				{
					shifted.add(shiftedAlphabet.get(j));
				}
//				else if(list.get(i).equals(" "))
//				{
//					shifted.add("*");
//				}
			}
		}
		return shifted;
	}
	
	/*
	 * generate rnd index num for rnd spaces
	 */
	public int generateRnd()
	{
		random = new Random();
		return random.nextInt(10);
	}
	
	/*
	 * keylistener
	 * when press enter, encrypte
	 * when press esc, close the window 
	 */
	//currently unavailiable
	public void keyPressed(KeyEvent e) 
	{
	    if (e.getKeyCode() == KeyEvent.VK_ENTER) 
	    {
//	      outputLabel.setText(encrypte(inputArea.getText().toLowerCase()));
//	      outputLabel.setFont(new Font("Serif", Font.BOLD, 27));
//	      generateFile();
	      
//	      e.consume();
//	      encrypteBtn.doClick();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
	    {
	      System.exit(0);
//	      encrypteBtn.doClick();
	    }
	 }
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{ 
	}
	
}
