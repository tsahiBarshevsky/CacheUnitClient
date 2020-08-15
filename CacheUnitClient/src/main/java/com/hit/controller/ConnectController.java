package com.hit.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.hit.model.Model;
import com.hit.view.ConnectView;
import com.hit.view.View;

public class ConnectController extends Object implements Controller, ActionListener {

	View view;
	Model model;
	
	public ConnectController(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		//System.out.println(ae.getActionCommand());
		switch (ae.getActionCommand()) {
		case "Reset to Default":
			view.updateUIData("reset");
			break;
		case "Connect":
			view.updateUIData("connect_button");
			break;
		default:
			break;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof Model) {
			if(obj instanceof Map<?,?>) {
				Map<String, Object> ud = (Map<String, Object>) obj;
				if(ud.containsKey("mainView") && ud.get("mainView").equals("connect")) {
					view.updateUIData("connect");
				}
			}
		}
		else if (obs instanceof ConnectView) {
			Map<String, Map<String,String>> connect = new HashMap<>();
			connect.put("connectController", (Map<String, String>) obj);			
			model.updateModelData(connect);
		}
	}

}
