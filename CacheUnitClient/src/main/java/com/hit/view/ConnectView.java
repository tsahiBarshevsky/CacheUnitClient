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
import javax.swing.JTextField;

public class ConnectView extends Observable implements View {


	public JButton reset;
	public JButton connect;
	public JTextField ip1;
	public JTextField ip2;
	public JTextField ip3;
	public JTextField ip4;
	
	public JTextField port;
	public JFrame frame;
	public JLabel ipL;
	public JLabel portL;
	public JLabel dot1;
	public JLabel dot2;
	public JLabel dot3;
	
	public ConnectView() {
		frame = new JFrame("Connection Panel");

		frame.setPreferredSize(new Dimension(300,135));
		
		
		reset = new JButton("Reset to Default");
		connect = new JButton("Connect");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(connect);
		buttonPanel.add(reset);
		frame.add("South", buttonPanel);
		
		ipL = new JLabel("IP Address:");
		ip1 = new JTextField("127");
		ip1.setPreferredSize(new Dimension(27,20));
		ip2 = new JTextField("0");
		ip2.setPreferredSize(new Dimension(27,20));
		ip3 = new JTextField("0");
		ip3.setPreferredSize(new Dimension(27,20));
		ip4 = new JTextField("1");
		ip4.setPreferredSize(new Dimension(27,20));
		dot1 = new JLabel(".");
		dot2 = new JLabel(".");
		dot3 = new JLabel(".");
		JPanel ipPanel = new JPanel();
		ipPanel.add(ipL);
		ipPanel.add(ip1);
		ipPanel.add(dot1);
		ipPanel.add(ip2);
		ipPanel.add(dot2);
		ipPanel.add(ip3);
		ipPanel.add(dot3);
		ipPanel.add(ip4);
		frame.add("North", ipPanel);
		
		portL = new JLabel("Port:");
		port = new JTextField("12345");
		port.setPreferredSize(new Dimension(50,20));
		JPanel portPanel = new JPanel();
		portPanel.add(portL);
		portPanel.add(port);
		frame.add("Center", portPanel);
		
		frame.pack();
		
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void addController (ActionListener controller) {
		reset.addActionListener(controller);
		connect.addActionListener(controller);
	}
	
	public void start() {
		frame.setVisible(true);
	}
	
	public void connect() {
		String ip = ip1.getText() + "." + ip2.getText() + "." + ip3.getText() + "." + ip4.getText();
		String port = this.port.getText();
		
		Map<String, String> connect = new HashMap<>();
		connect.put("ip", ip);
		connect.put("port", port);
		
		frame.setVisible(false);
		
		setChanged();
		notifyObservers(connect);		
	}
	
	public void reset() {
		ip1.setText("127");
		ip2.setText("0");
		ip3.setText("0");
		ip4.setText("1");
		port.setText("12345");
	}
	
	public <T> void updateUIData(T t) {
		if(t == "connect") {
			show();
		}
		else if(t == "connect_button") {
			connect();
		}
		else if (t == "reset") {
			reset();
		}
	}
}
