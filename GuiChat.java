package remote;
//====== GUI Packages ======
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.SwingConstants;

//====== Handling events ====== 
import java.awt.event.*;

//====== Mapping (GuiChat - Actor) ====== 
import akka.actor.ActorRef;

public class GuiChat extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JLabel lblOnlineUsers;
	private JButton btnLogin;
	private JButton btnDisconnect;
	private JScrollPane scrollPane;
	private JTextArea room;
	private JScrollPane scrollPane_1;
	private JTextArea usersList;
	private JScrollPane scrollPane_2;
	private JTextArea clientInput;
	private JButton btnSend;
	private ActorRef communicator;
	private Messages messages;

	public JTextArea getRoom () {
		return this.room;
	}

	public void setActorReference(ActorRef communicator) {
	    this.communicator = communicator;
	}

	public GuiChat() {

		messages =  new Messages();
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 642);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblUsername = new JLabel("Username");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUsername, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUsername, 10, SpringLayout.WEST, contentPane);
		lblUsername.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 15));
		contentPane.add(lblUsername);
		
		username = new JTextField("");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.EAST, lblUsername, -5, SpringLayout.WEST, username);
		sl_contentPane.putConstraint(SpringLayout.NORTH, username, 2, SpringLayout.NORTH, lblUsername);
		sl_contentPane.putConstraint(SpringLayout.WEST, username, 97, SpringLayout.WEST, contentPane);
		
		username.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				btnLogin.setEnabled(false);
				username.setEnabled(false);
				clientInput.setEnabled(true);
				btnSend.setEnabled(true);
				communicator.tell(messages.new LoginMessage(username.getText()),null);
			}

		});

		contentPane.add(username);
		username.setColumns(10);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setForeground(new Color(255, 69, 0));
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnDisconnect, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnDisconnect, 332, SpringLayout.WEST, contentPane);
		btnDisconnect.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				clientInput.setEnabled(false);
				btnSend.setEnabled(false);
			}
		});
		contentPane.add(btnDisconnect);
		
		lblOnlineUsers = new JLabel("Online Users");
		sl_contentPane.putConstraint(SpringLayout.EAST, lblOnlineUsers, -30, SpringLayout.EAST, contentPane);
		lblOnlineUsers.setForeground(new Color(0, 0, 0));
		lblOnlineUsers.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		contentPane.add(lblOnlineUsers);
		
		btnLogin = new JButton("Sign in");
		btnLogin.setForeground(new Color(50, 205, 50));
		sl_contentPane.putConstraint(SpringLayout.EAST, username, -6, SpringLayout.WEST, btnLogin);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLogin, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnLogin, -6, SpringLayout.WEST, btnDisconnect);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clientInput.setEnabled(true);
				btnSend.setEnabled(true);
			}
		});
		btnLogin.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		contentPane.add(btnLogin);
		
		scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 19, SpringLayout.SOUTH, btnDisconnect);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -96, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -146, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);
		
		room = new JTextArea("");
		room.setEditable(false);
		room.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 15));
		scrollPane.setViewportView(room);
		
		scrollPane_1 = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblOnlineUsers, -1, SpringLayout.NORTH, scrollPane_1);
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane_1, 0, SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane_1, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane_1, 6, SpringLayout.EAST, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane_1, -10, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane_1);
		
		usersList = new JTextArea("");
		usersList.setEditable(false);
		usersList.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 13));
		scrollPane_1.setViewportView(usersList);
		
		scrollPane_2 = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane_2, 6, SpringLayout.SOUTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane_2, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane_2, 0, SpringLayout.SOUTH, scrollPane_1);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane_2, -227, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane_2);
		
		clientInput = new JTextArea("");
		clientInput.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 14));
		scrollPane_2.setViewportView(clientInput);
		//textAreaInput
		clientInput.setEnabled(false);

		
		btnSend = new JButton("Send");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSend, 6, SpringLayout.EAST, scrollPane_2);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnSend, 0, SpringLayout.SOUTH, scrollPane_1);
		btnSend.setForeground(new Color(50, 205, 50));
		btnSend.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 13));
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				communicator.tell( messages.new ChatMessage(clientInput.getText()) , null);
				clientInput.setText("");
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSend, 6, SpringLayout.SOUTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, scrollPane);
		contentPane.add(btnSend);
	}
}

