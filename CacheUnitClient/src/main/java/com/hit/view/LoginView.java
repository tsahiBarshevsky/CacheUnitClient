package com.hit.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends Observable implements View {

	public JButton login;
	public JButton cancel;
	public JTextField userName;
	public JPasswordField password;
	public JFrame frame;
	public JLabel userNameL;
	public JLabel passwordL;
	
	public LoginView() {
		frame = new JFrame("Login Panel");

		frame.setPreferredSize(new Dimension(300,135));
		
		
		login = new JButton("Login");
		cancel = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(login);
		buttonPanel.add(cancel);
		frame.add("South", buttonPanel);
		
		userNameL = new JLabel("User Name:");
		userName = new JTextField("");
		userName.setPreferredSize(new Dimension(100,20));
		JPanel userNamePanel = new JPanel();
		userNamePanel.add(userNameL);
		userNamePanel.add(userName);
		frame.add("North", userNamePanel);
		
		passwordL = new JLabel("Password:");
		password = new JPasswordField("");
		password.setPreferredSize(new Dimension(95,20));
		password.setEchoChar('*');
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passwordL);
		passwordPanel.add(password);
		frame.add("Center", passwordPanel);
		
		frame.pack();
		
	}

	public void show() {
		frame.setVisible(true);
	}
	
	public void addController (ActionListener controller) {
		cancel.addActionListener(controller);
		login.addActionListener(controller);
	}
	
	public void start() {
		frame.setVisible(true);
	}
	
	public void login() {
		if(userName.getText() != null && password.getPassword() != null) {
			Map<String, String> login = new HashMap<>();
			login.put("username", userName.getText());
			login.put("password", new String(password.getPassword()));
		
			frame.setVisible(false);
			userName.setText("");
			password.setText("");
			
			setChanged();
			notifyObservers(login);	
			login.clear();
		}
	}
	
	public void cancel() {
		frame.setVisible(false);
		userName.setText("");
		password.setText("");
	}
	
	public <T> void updateUIData(T t) {
		if(t == "login") {
			show();
		}
		else if(t == "login_button") {
			login();
		}
		else if(t == "cancel") {
			cancel();
		}
	}
}
