package io.github.houman31;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.atsign.client.api.AtClient;
import org.atsign.common.AtException;
import org.atsign.common.AtSign;
import org.atsign.common.KeyBuilders;

// A Swing application inherits from top-level container javax.swing.JFrame
public class MotionDisplay extends JFrame {							

	JTextArea messagesTextArea;		//Allocate  Jtextarea
	final LimitedMessages messages = new LimitedMessages(19);	//show max 19 rows as messages in a single window
	final AtomicLong counter = new AtomicLong(0);
	/** Constructor to setup the GUI */
	public MotionDisplay() {

		Container rootContainer = this.getContentPane();			// Setup the content-pane of JFrame 

		
		messagesTextArea = new JTextArea(32, 100);					//sets text area for message as rows & columns
		JScrollPane scroll = new JScrollPane(messagesTextArea);		//Provides a scrollable view of a message 
		// Wrap the JTextArea inside a JScrollPane
		rootContainer.add(scroll, BorderLayout.CENTER);				//Set content-pane for Border Layout

		JButton button = new JButton("Close Window");				
		button.addActionListener(new ActionListener( ) {			//push action listner for motion detection button

			@Override
			public void actionPerformed(ActionEvent e) {
				MotionDisplay.this.dispose();
			}
			
		});
		
		rootContainer.add(button, BorderLayout.PAGE_END);
	}

	public void initializeServerCommunication() {
		// Run the GUI codes 
		Thread communicationThread = new Thread(new Runnable() {	

			@Override
			public void run() {
				// connects pico and atsign server for Detection
				String ROOT_URL = "root.atsign.org:64";
				AtSign atSign = new AtSign("@snowape16belgian");
				AtSign pico = new AtSign("@present61");

				AtClient atClient;
				// Set the UI manager (shall be the first step of the GUI construction)
				try {
					atClient = AtClient.withRemoteSecondary(ROOT_URL, atSign);
					
					org.atsign.common.Keys.PublicKey pk2 = new KeyBuilders.PublicKeyBuilder(pico).key("inMotion").build();

					while (true) {			//sets running to be true
						
						Thread.sleep(500);
						// Sleep is a static function	
						String keymotion = "inMotion";

						atClient.executeCommand("delete:cached:public:" + keymotion + pico.toString(), false);

						String datamotion = atClient.get(pk2).get();
						//runs message on window with true/false
						String message = "[" + counter.incrementAndGet() + "] Motion Detected: " + datamotion;

						messages.add(message);
						// creating an instance of an anonymous implementation of the Runnable interface and passing it to invokeLater
						SwingUtilities.invokeLater(new Runnable() { 

							@Override
							public void run() {		//declares run method

								
									messagesTextArea.setText( messages.toString());
								
								
							}

						});
					}
					
				} catch (Exception ex) {
					//shows error message 
					String errorMessage = "[" + counter.incrementAndGet() + "] ERROR: " + ex.getMessage();
					messages.add(errorMessage);
					
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {			//declares run method

							messagesTextArea.setText( messages.toString());		
						}

					});
				}
			}
		});

		communicationThread.start();
	}

}
