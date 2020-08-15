package com.hit.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RequestView extends Observable implements View {

	public JButton send;
	public JButton clear;
	public JButton add;
	
	public JComboBox<String> reqType;
	public JLabel reqTypeL;
	
	public JLabel dmIDL;
	public JLabel dmContentL;
	
	public List<JLabel> reqNo;
	public List<JTextField> dmID;
	public List<JTextField> dmContent;
	
	public JFrame frame;
	
	JPanel requestPanel;
	GridBagConstraints c;
	
	String currentReqType;
	
	public RequestView() {
		frame = new JFrame("Request Panel");
		frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                clearForm();
            }
        });
		frame.setPreferredSize(new Dimension(500,600));
		
		requestPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		
		dmIDL = new JLabel("Data Model ID");
		c.gridx = 1;
		c.gridy = 0;
		requestPanel.add(dmIDL, c);
		dmContentL = new JLabel("Data Model Content");
		c.gridx = 2;
		c.gridy = 0;
		requestPanel.add(dmContentL, c);
		
		reqNo = new ArrayList<>();
		dmID = new ArrayList<>();
		dmContent = new ArrayList<>();
		
		reqNo.add(new JLabel("1"));
		c.gridx = 0;
		c.gridy = 1;
		requestPanel.add(reqNo.get(0), c);
		dmID.add(new JTextField(""));
		dmID.get(0).setPreferredSize(new Dimension(200,20));
		dmID.get(0).setEditable(false);
		c.gridx = 1;
		c.gridy = 1;
		requestPanel.add(dmID.get(0), c);
		dmContent.add(new JTextField(""));
		dmContent.get(0).setPreferredSize(new Dimension(200,20));
		dmContent.get(0).setEditable(false);
		c.gridx = 2;
		c.gridy = 1;
		requestPanel.add(dmContent.get(0), c);
		
		add = new JButton("Add");
		c.gridx = 0;
		c.gridy = 2;
		requestPanel.add(add, c);
		
		
		reqTypeL = new JLabel("Request:");
		String reqList[] = {"", "Get", "Update", "Delete"};
		reqType = new JComboBox<String>(reqList);
		JPanel reqHeadPanel = new JPanel();
		reqHeadPanel.add(reqTypeL);
		reqHeadPanel.add(reqType);
		
		send = new JButton("Send");
		clear = new JButton("Clear");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(send);
		buttonPanel.add(clear);
		frame.add("South", buttonPanel);
		
		
		frame.add("North", reqHeadPanel);
		frame.add("Center", requestPanel);
		
		frame.pack();
		
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void addController (ActionListener controller) {
		send.addActionListener(controller);
		clear.addActionListener(controller);
		add.addActionListener(controller);
		reqType.addActionListener(controller);
	}
	
	public void start() {
		frame.setVisible(true);
	}
	
	public void clearForm() {
		reqNo.clear();
		dmID.clear();
		dmContent.clear();
		requestPanel.removeAll();
		requestPanel.getGraphics().clearRect(0, 0, requestPanel.getWidth(), requestPanel.getHeight());
		reqType.setSelectedIndex(0);
		currentReqType = reqType.getSelectedItem().toString();
		
		dmIDL = new JLabel("Data Model ID");
		c.gridx = 1;
		c.gridy = 0;
		requestPanel.add(dmIDL, c);
		dmContentL = new JLabel("Data Model Content");
		c.gridx = 2;
		c.gridy = 0;
		requestPanel.add(dmContentL, c);
		
		reqNo.add(new JLabel("1"));
		c.gridx = 0;
		c.gridy = 1;
		requestPanel.add(reqNo.get(0), c);
		dmID.add(new JTextField(""));
		dmID.get(0).setPreferredSize(new Dimension(200,20));
		dmID.get(0).setEditable(false);
		c.gridx = 1;
		c.gridy = 1;
		requestPanel.add(dmID.get(0), c);
		dmContent.add(new JTextField(""));
		dmContent.get(0).setPreferredSize(new Dimension(200,20));
		dmContent.get(0).setEditable(false);
		c.gridx = 2;
		c.gridy = 1;
		requestPanel.add(dmContent.get(0), c);
		
		c.gridx = 0;
		c.gridy = 2;
		requestPanel.add(add, c);
		
		frame.pack();
		
	}
	
	public void addDM() {
		Integer size = dmID.size() +1 ;
		reqNo.add(new JLabel(size.toString()));
		c.gridx = 0;
		c.gridy = size;
		requestPanel.add(reqNo.get(size-1), c);
		dmID.add(new JTextField(""));
		dmID.get(size-1).setPreferredSize(new Dimension(200,20));
		if(currentReqType == "Get" || currentReqType == "Delete" || currentReqType == "Update") {
			dmID.get(size-1).setEditable(true);
		}
		else {
			dmID.get(size-1).setEditable(false);
		}
		c.gridx = 1;
		c.gridy = size;
		requestPanel.add(dmID.get(size-1), c);
		dmContent.add(new JTextField(""));
		dmContent.get(size-1).setPreferredSize(new Dimension(200,20));
		if(currentReqType == "Update") {
			dmContent.get(size-1).setEditable(true);
		}
		else {
			dmContent.get(size-1).setEditable(false);
		}
		c.gridx = 2;
		c.gridy = size;
		requestPanel.add(dmContent.get(size-1), c);
		
		c.gridx = 0;
		c.gridy = size+1;
		requestPanel.add(add, c);
		
		frame.pack();
	}

	public void setReqType() {
		currentReqType = reqType.getSelectedItem().toString();
		int size = dmID.size();
		if(currentReqType == "Get" || currentReqType == "Delete") {
			for(int i =0 ; i<size;i++) {
				dmID.get(i).setEditable(true);
				dmContent.get(i).setEditable(false);
			}
		}
		else if(currentReqType == "Update") {
			for(int i =0 ; i<size;i++) {
				dmID.get(i).setEditable(true);
				dmContent.get(i).setEditable(true);
			}
		}
		else {
			for(int i =0 ; i<size;i++) {
				dmID.get(i).setEditable(false);
				dmContent.get(i).setEditable(false);
			}
		}
		
		frame.pack();
	}
	
	public void send() {
		Map<String, Map<String, String>> req = new HashMap<>();
		Map<String, String> tmp = new HashMap<>();
		
		if(currentReqType.equals("Get")) {
			tmp.put("type", "GET");
			req.put("request", tmp);
		}
		else if(currentReqType.equals("Update")) {
			tmp.put("type", "UPDATE");
			req.put("request", tmp);
		}
		else if(currentReqType.equals("Delete")) {
			tmp.put("type", "DELETE");
			req.put("request", tmp);
		}
				
		int size = dmID.size();
		for(Integer i=0;i<size;i++) {
			if(!dmID.get(i).getText().equals("")) {
				tmp = new HashMap<>();
				tmp.put("ID", dmID.get(i).getText());
				if (dmContent.get(i).getText().equals("")) {
					tmp.put("content", "null");
				}
				else {
					tmp.put("content", dmContent.get(i).getText());
				}
			
				req.put(i.toString(), tmp);
			}
		}
		
		frame.setVisible(false);
		clearForm();
				
		setChanged();
		notifyObservers(req);	
	}
	
	public <T> void updateUIData(T t) {
		if(t == "request") {
			show();
		}
		else if(t == "clear") {
			clearForm();
		}
		else if (t == "add") {
			addDM();
		}
		else if (t == "send") {
			send();
		}
		else if (t == "comboBoxChanged") {
			setReqType();
		}
	}
}
