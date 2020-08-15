package com.hit.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.hit.model.Model;
import com.hit.view.RequestView;
import com.hit.view.View;

public class RequestController extends Object implements Controller, ActionListener {

	View view;
	Model model;
	
	public RequestController(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		//System.out.println(ae.getActionCommand());
		switch (ae.getActionCommand()) {
		case "Send":
			view.updateUIData("send");
			break;
		case "Clear":
			view.updateUIData("clear");
			break;
		case "Add":
			view.updateUIData("add");
			break;
		case "comboBoxChanged":
			view.updateUIData("comboBoxChanged");
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
				if(ud.containsKey("mainView") && ud.get("mainView").equals("request")) {
					view.updateUIData("request");
				}
			}
		}
		else if (obs instanceof RequestView) {
			Map<String, Map<String, Map<String, String>>> request = new HashMap<>();
			request.put("requestController", (Map<String, Map<String, String>>) obj);			
			model.updateModelData(request);
		}
		
	}

}
