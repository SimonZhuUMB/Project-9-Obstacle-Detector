package io.github.houman31;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Image;
import javax.swing.border.Border;

// pushing a class LaunchPage as public
public class LaunchPage implements ActionListener{
	//variables of UI components
	JButton buttonmotion;
	JButton buttonproximity;
	JFrame frame;
	
	/** Constructor to setup the UI components */
	LaunchPage(){
		// pushing images for User Interface
		ImageIcon imagemotion = new ImageIcon((new ImageIcon("motionsensor.jpg").getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)) );
		ImageIcon imageproximity = new ImageIcon((new ImageIcon("proximitysensor.jpg").getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)) );
		ImageIcon clickproximity = new ImageIcon((new ImageIcon("click_pd.jpg").getImage().getScaledInstance(270,250, Image.SCALE_DEFAULT)) );
		ImageIcon clickmotion = new ImageIcon((new ImageIcon("click_md1.jpg").getImage().getScaledInstance(250, 250, Image.SCALE_AREA_AVERAGING)) );
		ImageIcon imageatsign = new ImageIcon("atsignpic.png");
		
		Border border=BorderFactory.createLineBorder(Color.white,1); //set border 

		buttonmotion = new JButton(); 				// create button object 
		//button methods
		buttonmotion.setBounds(200,100,100,50);		//sets parameters of the size of the button
		buttonmotion.addActionListener(this);		//adds an action to the create account button from implemented action listener
		buttonmotion.setIcon(clickmotion);		    //pushing image named 'clickmotion' as Icon
		buttonmotion.setForeground(Color.white);    //button foreground
		buttonmotion.setBorder(border); 			//push 'border' as Border of button
		buttonmotion.setBorder(BorderFactory.createLineBorder(Color.white));   //sets color of border of button as white
		

		
		//create java button object as buttonproximity
		buttonproximity = new JButton();			
		//button methods
		buttonproximity.setBounds(200,100,100,50);	//sets dimensions of the button
		buttonproximity.addActionListener(this);	//adds an action to the create account button from implemented action listener
		buttonproximity.setIcon(clickproximity);	//adding image as Icon
		buttonproximity.setForeground(Color.white);	//sets foreground color as white
		buttonproximity.setBorder(BorderFactory.createLineBorder(Color.white));  //sets color of border of button as white
		
		
		
		JPanel specialPanel = new JPanel();				 //creates panel object
		//Jpanel methods
		specialPanel.setBackground(new Color(0xc2e2ff)); //set background color
		specialPanel.setBounds(0,0,250,250);			 //set dimensions for panel
		specialPanel.setLayout(null);
		

		JPanel bluePanel = new JPanel(); 				//creates Jpanel object as bluePanel
		//Jpanel methods
		bluePanel.setBackground(Color.white);			//set bluePanel background color to white
		bluePanel.setBounds(250,-5,250,250);	
		

		JPanel greenPanel = new JPanel(); 				//Jpanel object named greenLabel
		//Jpanel methods
		greenPanel.setBackground(Color.decode("#304f67")); //set background color for greenPanel
		greenPanel.setBounds(0,250,250,250);			//Panel dimensions	
		greenPanel.setLayout(new BorderLayout());		//set layout
		

		JPanel redPanel =  new JPanel();				//creates panel object
		//Jpanel methods
		redPanel.setBackground(Color.decode("#b5b5b5"));//sets bachground color of redPanel 
		redPanel.setBounds(250,250,250,250);			//sets parameters of the size of panel
	 
		
		JLabel motiondetection = new JLabel();			//creates Jlabel object named motiondetection
		//Label methods
		motiondetection.setIcon(imagemotion);			//sets image of label
		motiondetection.setForeground(Color.black);		//foreground color as black	
		motiondetection.setBorder(border);				//sets border function as Border of Jlabel
		motiondetection.setBounds(0,0,250,250);			//dimension of label 
		
		JLabel proximitydetection = new JLabel();		//creates JLabel Object
		//Label methods
		proximitydetection.setIcon(imageproximity);		//sets image of Label	
		proximitydetection.setForeground(Color.black);	//sets foreground color
		proximitydetection.setBorder(border);			//border of JLabel 'proximitydetection'
		proximitydetection.setBounds(0,0,250,250);		//parameters of dimensions of label
		
		frame = new JFrame();							//creates frame object
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//sets default close operation for frame	
		frame.setTitle("Obstacle Detection!");			//sets title of frsme
		frame.setSize(500, 500);						//size of frame
		frame.setResizable(false);						//resizable is disable 
		frame.setLayout(null);							//null for frame layout
		frame.setIconImage(imageatsign.getImage());		//sets icon image of Java GUI application
		frame.getContentPane().setBackground(new Color(0xc2e2ff));	//background color
		frame.add(buttonmotion);						//add button to frame
		frame.add(buttonproximity);						//add button to frame
		/**
		 * add panels in the frame 
	 	*/
		frame.add(redPanel);							
		frame.add(bluePanel);						
		frame.add(specialPanel);						
		frame.add(greenPanel);

		greenPanel.add(buttonmotion);					//sets greenPanel for 'buttonmotion'
		redPanel.add(buttonproximity);					//sets redPanel for 'buttonproximity'
		specialPanel.add(motiondetection);				//sets specialPanel Label for 'motiondetection'
		bluePanel.add(proximitydetection);				//bluePanel for Label 'proximitydetection'
		
	
		frame.setVisible(true);							//makes the frame visible and within focus
	

	}

	@Override
	//Action Listener for motion & Proximity button, what happens when the button is clicked.
	public void actionPerformed(ActionEvent el) {
		if(el.getSource()==buttonmotion) { //if condition is true, it opens new window and shows updates on motion detection 
			
			MotionDisplay form = new MotionDisplay();
			form.setTitle("Motion Detection!");						//title of new window that will open when 'Motion Detection' is clicked
			form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//sets default close operation
			form.setPreferredSize(new Dimension(600, 400));			//dimensions of form
			form.pack();											//sizes the frame so that all its contents are at or above their preferred sizes. 
			
			form.setVisible(true);									//makes the form visible and within focus
			
			form.initializeServerCommunication();
		}
		/**
	 	*when proximity detection button is clicked, opens new window which shows result of proximity 
	 	*/
		
		else if (el.getSource()== buttonproximity) {			//when proximity detection button is clicked, opens new window which shows result of proximity
			
			ProximityDisplay form = new ProximityDisplay();
			form.setTitle("Proximity Detection!");				//sets title of form		
			form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//default close operation sets as exit on close 
			form.setPreferredSize(new Dimension(600, 400));		//parameters for dimensions of form
			form.pack();										//sizes the frame so that all its contents are at or above their preferred sizes.
			
			form.setVisible(true);								//show it
			
			form.initializeServerCommunication();
		}
		
		
	}
		
	}

