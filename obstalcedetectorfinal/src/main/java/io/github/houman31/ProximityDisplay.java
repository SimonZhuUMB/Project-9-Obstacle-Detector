package io.github.houman31;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/*
* A Swing application inherits from top-level container javax.swing.JFrame
*/
public class ProximityDisplay extends JFrame {

	JTextArea messagesTextArea;			//Allocate  Jtextarea
	final LimitedMessages messages = new LimitedMessages(19);	//show max 19 rows as messages in a single window w/o scrolling
	final AtomicLong counter = new AtomicLong(0);
	/** Constructor to setup the GUI */
	public ProximityDisplay() {

		Container rootContainer = this.getContentPane();			// Setup the content-pane of JFrame 

		
		messagesTextArea = new JTextArea(32, 100);					//rows and columns
		JScrollPane scroll = new JScrollPane(messagesTextArea);		// Wrap the JTextArea inside a JScrollPane

		rootContainer.add(scroll, BorderLayout.CENTER);				//Set content-pane for scroll Border Layout

		JButton button = new JButton("Close Window");
		button.addActionListener(new ActionListener( ) {			//push action listner for proximity detection button

			@Override
			public void actionPerformed(ActionEvent e) {			
				ProximityDisplay.this.dispose();

				
			}
			
		});
		
		rootContainer.add(button, BorderLayout.PAGE_END);			//sets Content-pane for button border layout
	}

	public void initializeServerCommunication() {
		Thread communicationThread = new Thread(new Runnable() {	// Run the GUI codes 

			@Override
			public void run() {
				//connnets Pico and atsign server
				String ROOT_URL = "root.atsign.org:64";
				AtSign atSign = new AtSign("@snowape16belgian");
				AtSign pico = new AtSign("@present61");
				// Set the UI manager (shall be the first step of the GUI construction)
				AtClient atClient;
				try {
					atClient = AtClient.withRemoteSecondary(ROOT_URL, atSign);
					
					org.atsign.common.Keys.PublicKey pk = new KeyBuilders.PublicKeyBuilder(pico).key("inProximity").build();

					while (true) {		//set while running is true
						
						Thread.sleep(500);
						
						String keyproximity = "inProximity";

						atClient.executeCommand("delete:cached:public:" + keyproximity + pico.toString(),false);

						String dataproximity = atClient.get(pk).get();
						//displays proximity detection message with result in boolean
						String message = "[" + counter.incrementAndGet() + "]  in Proximity: " + dataproximity;

						messages.add(message);
						
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {

								
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



