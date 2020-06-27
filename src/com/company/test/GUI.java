package com.company.test;

/**
 * Class to read create gui for user to make and send email
 * 
 * @author Shook Kirk
 * 
 *version 1.0
 *
 *since 06/26/2020
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.mail.MessagingException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
	// Variables to hold user email and password
	private String userEmail;
	private String userPassword;
	
	// Components to create and modify GUI
	private JFrame frame;
	private String chosenFile;
	private int response;
	private File file;
	private JFileChooser chooser;
	private ExcelReader reader;
	private JPanel mainPanel;
	private JPanel north;
	private JPanel subject;
	private JLabel subjectLabel;
	private JTextArea writeSubject;
	private JPanel greetingPanel;
	private JPanel greetButtons;
	private JLabel sayHello;
	private ButtonGroup greetGroup;
	private JRadioButton radHello;
	private JRadioButton radHola;
	private JRadioButton radHey;
	private JRadioButton radBuenos;
	private JLabel messageLabel;
	private JPanel messagePanel;
	private JTextArea emailMessage;
	private JPanel donePanel;
	private JButton doneButton;
	private JTextArea writeMessage;
	
	// Variables to hold user inputed information for the email
	private String subjectEmail;
	private String emailGreeting;
	private String bodyMessage;
	private String greetingChoice = "Hello";
	
	// Mail object to create and send email
	private SendGmail mail;
	
	// Constructor that calls login method to get user email and password
	public GUI(){ 
		getLogin();
	}
	
	// Creates frame to get user login information
	public void getLogin() {
		JPanel mainPanel = new JPanel();
		JFrame loginFrame = new JFrame();
		loginFrame.setSize(300,200);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setLocation(300, 400);
		loginFrame.add(mainPanel);
		
		mainPanel.setLayout(null);
		
		JLabel emailLabel = new JLabel("Email ");
		emailLabel.setBounds(10, 20, 80, 25);
		mainPanel.add(emailLabel);
		
		JTextField emailTextField = new JTextField(20);
		emailTextField.setBounds(100, 20, 165, 25);
		mainPanel.add(emailTextField);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 50, 80, 25);
		mainPanel.add(passwordLabel);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(100, 50, 165, 25);
		mainPanel.add(passwordField);
		
		JButton button = new JButton("Continue");
		button.setBounds(10, 90, 90, 25);
		mainPanel.add(button);
		loginFrame.setVisible(true);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// If statement to cheack if login is missing email, password, or both
				if(emailTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Email is empty");
				}
				else if(passwordField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Password is empty");
				}
				else {
					userEmail = emailTextField.getText();
					userPassword = passwordField.getText();
					System.out.println(userEmail+userPassword);
					mail = new SendGmail(userEmail,userPassword);
					loginFrame.setVisible(false);
					writeMassEmail();  // If all is good, method to get user message for email is run
				}
			}
		});
	}
	
	// Method to get file to be read
	public String getFile() {
		chooser = new JFileChooser(".");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		response = chooser.showOpenDialog(null);
		
		if(response == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		String fileChosen = file.toString(); // Returns file as a string
		return fileChosen;
	}
	
	// Method to preview email before mass sending
	public void previewMassEmail() {
		// Selected greeting is chosen
		if(radHello.isSelected()) {
			greetingChoice = "Hello";
		}
		
		if(radHola.isSelected()) {
			greetingChoice = "Hola";
		}
		if(radHey.isSelected()) {
			greetingChoice = "Hey";
		}
		if(radBuenos.isSelected()) {
			greetingChoice = "Buenos dia";
		}
		
		// Clear the frame to show email and all its components
		frame.getContentPane().removeAll();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		
		// Components to display email
		JPanel doneMainPanel = new JPanel();
		doneMainPanel.setLayout(new BorderLayout());
		doneMainPanel.setBackground(Color.LIGHT_GRAY);
		
		JPanel northDonePanel = new JPanel();
		northDonePanel.setLayout(new BorderLayout());
		doneMainPanel.add(northDonePanel, BorderLayout.NORTH);
		northDonePanel.setBackground(Color.LIGHT_GRAY);
		
		JPanel previewTitlePanel = new JPanel();
		northDonePanel.add(previewTitlePanel, BorderLayout.NORTH);
		previewTitlePanel.setBackground(Color.LIGHT_GRAY);
		
		JPanel subjectPreviewPanel = new JPanel();
		subjectPreviewPanel.setLayout(new BorderLayout());
		northDonePanel.add(subjectPreviewPanel, BorderLayout.SOUTH);
		subjectPreviewPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel titleLabel = new JLabel(" Email Preview ");
		previewTitlePanel.add(titleLabel);
						
		JLabel subjectPreviewLabel = new JLabel("Subject:     ");
		subjectPreviewPanel.add(subjectPreviewLabel, BorderLayout.WEST);
		
		JTextArea subjectLinePreview = new JTextArea(subjectEmail);
		subjectLinePreview.setEditable(false);
		subjectPreviewPanel.add(subjectLinePreview, BorderLayout.CENTER);
		
		Font font = subjectLinePreview.getFont();
		subjectLinePreview.setFont(font.deriveFont(Font.BOLD));
		
		JTextArea messagePreview = new JTextArea(greetingChoice+" STUDENT NAME,"+"\n"+bodyMessage+"\n\nSIGNATURE");
		messagePreview.setEditable(false);
		doneMainPanel.add(messagePreview, BorderLayout.CENTER);
		
		JPanel messageTitlePanel = new JPanel();
		messageTitlePanel.setLayout(new BorderLayout());
		doneMainPanel.add(messageTitlePanel, BorderLayout.WEST);
		messageTitlePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel messageTitle = new JLabel(" Message: ");
		messageTitlePanel.add(messageTitle, BorderLayout.NORTH);
		
		JPanel allGoodPanel = new JPanel();
		allGoodPanel.setLayout(new BorderLayout());
		doneMainPanel.add(allGoodPanel, BorderLayout.SOUTH);
		allGoodPanel.setBackground(Color.LIGHT_GRAY);
		
		JButton agButton = new JButton("All Good");
		allGoodPanel.add(agButton, BorderLayout.EAST);
		frame.add(doneMainPanel);
		
		agButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Frame is cleared to get final approval to send
				frame.getContentPane().removeAll();
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Mass Email");
				frame.setVisible(true);
				frame.pack();
				frame.setLocation(300,200);
				frame.setSize(400, 250);
				JPanel lastMainPanel = new JPanel();
				
				lastMainPanel.setLayout(new BorderLayout());
				
				JLabel youSurePanel = new JLabel("Ready to send?");
				lastMainPanel.add(youSurePanel, BorderLayout.NORTH);
				
				JPanel lastButtons = new JPanel();
				lastMainPanel.add(lastButtons, BorderLayout.SOUTH);
				
				JButton sendButton = new JButton("Send"); // Button to send email
				
				lastButtons.add(sendButton);
				
				frame.add(lastMainPanel);
				
				sendButton.addActionListener(new ActionListener() {
					
					// When send is selected loop goes through all student objects and message is sent to the email saves
					@SuppressWarnings("finally")
					public void actionPerformed(ActionEvent e) {
						for(int i=1;i<reader.studentList.getCount();i++) {
							try {
								String emailMessage = greetingChoice+" "+reader.studentList.array[i].getFullName()+",\n"+bodyMessage;
								mail.sendMail(reader.studentList.array[i].getEmail(), subjectEmail, emailMessage);
							} catch (MessagingException e1) {
								e1.getStackTrace();
							}finally {
								continue;
							}
						}
						
						// If a problem is reported a count of how many emails weren't sent is shown to the user
						String doneMessage = "Emails Sent. Number of bad emails: "+Integer.toString(mail.getErrorCount());
						JOptionPane.showMessageDialog(null, doneMessage);
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
				});
			}
				
		});
	}
	
	// Method to get user message for the email
	public void writeMassEmail() {
		
		frame = new JFrame();
		frame.setSize(500, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		chosenFile = getFile(); // Method is called to get file to be read
		try {
			reader = new ExcelReader(chosenFile);
			JOptionPane.showMessageDialog(null, "Student uploaded successfully."); // If no problem message is shown to user
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// GUI frame is created
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.LIGHT_GRAY);
		
		north = new JPanel();
		north.setLayout(new BorderLayout());
		north.setBackground(Color.LIGHT_GRAY);
		
		subject = new JPanel();
		subject.setLayout(new BorderLayout());
		north.add(subject, BorderLayout.NORTH);
		subject.setBackground(Color.LIGHT_GRAY);
		
		subjectLabel = new JLabel(" Subject Line:    ");
		writeSubject = new JTextArea("Subject...");
		
		writeSubject.setLineWrap(true);
		writeSubject.setWrapStyleWord(true);
		subject.add(subjectLabel, BorderLayout.WEST);
		subject.add(writeSubject, BorderLayout.CENTER);
		
		greetingPanel = new JPanel();
		greetingPanel.setLayout(new BorderLayout());
		greetingPanel.setBackground(Color.LIGHT_GRAY);
		greetButtons = new JPanel();
		greetButtons.setBackground(Color.LIGHT_GRAY);
		
		// Option to select a greeting
		sayHello = new JLabel(" Select a greeting:");
		greetGroup = new ButtonGroup();
		radHello = new JRadioButton("Hello");
		radHola = new JRadioButton("Hola");
		radHey = new JRadioButton("Hey");
		radBuenos = new JRadioButton("Buenos Dia");
		radHello.setBackground(Color.LIGHT_GRAY);
		radHola.setBackground(Color.LIGHT_GRAY);
		radHey.setBackground(Color.LIGHT_GRAY);
		radBuenos.setBackground(Color.LIGHT_GRAY);
		
		// Group is created of all the greeting
		greetGroup.add(radHello);
		greetGroup.add(radHola);
		greetGroup.add(radHey);
		greetGroup.add(radBuenos);
		
		// Buttons added to panel
		greetButtons.add(radHello);
		greetButtons.add(radHola);
		greetButtons.add(radHey);
		greetButtons.add(radBuenos);
		
		greetingPanel.add(sayHello, BorderLayout.WEST);
		greetingPanel.add(greetButtons, BorderLayout.CENTER);
		
		north.add(greetingPanel, BorderLayout.SOUTH);
		mainPanel.add(north, BorderLayout.NORTH);
		
		// Components to get message body
		messageLabel = new JLabel(" Email Message: ");
		messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(messageLabel, BorderLayout.NORTH);
		mainPanel.add(messagePanel, BorderLayout.WEST);
		messagePanel.setBackground(Color.LIGHT_GRAY);
		
		writeMessage = new JTextArea("Write message here...");
		writeMessage.setLineWrap(true);
		writeMessage.setWrapStyleWord(true);
		mainPanel.add(writeMessage, BorderLayout.CENTER);
		
		// Panel to hold button to get user inout that they are done
		donePanel = new JPanel();
		donePanel.setLayout(new BorderLayout());
		doneButton = new JButton("Done");
		donePanel.add(doneButton, BorderLayout.EAST);
		donePanel.setBackground(Color.LIGHT_GRAY);
		
		mainPanel.add(donePanel, BorderLayout.SOUTH);
		
		frame.add(mainPanel);
		frame.setVisible(true);
		
		doneButton.addActionListener(new ActionListener() {

			// If done is selected then body and subject line is saved as a string
			public void actionPerformed(ActionEvent e) {
				bodyMessage = writeMessage.getText();
				subjectEmail =  writeSubject.getText();
				
				// Preview method is called
				previewMassEmail();
			}
		});
		
		// New frame pops up showing all the student objects and info who will receive an email
		JFrame studentList = new JFrame("Student List");
		studentList.setSize(400, 300);
		studentList.setLocation(400, 250);
		studentList.setLayout(new BorderLayout());
		
		JLabel studentLabel = new JLabel("Students to be Emailed:");
		studentList.add(studentLabel, BorderLayout.NORTH);
		
		JTextArea studentInfo = new JTextArea();
		studentList.getContentPane().add(studentInfo, BorderLayout.CENTER);
		studentInfo.setEditable(false);
		
		// Loop to go through student array
		for(int i = 0;i<reader.studentList.getCount();i++) {
			studentInfo.append(reader.studentList.array[i].toString());
		}
		studentList.setVisible(true);
	}
}

