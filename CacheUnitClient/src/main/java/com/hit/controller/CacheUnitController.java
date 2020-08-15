package com.hit.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.hit.model.Model;
import com.hit.view.CacheUnitView;
import com.hit.view.View;

public class CacheUnitController extends Object implements Controller, ActionListener {

	View view;
	Model model;
	
	public CacheUnitController(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent ae) {
		//System.out.println(ae.getActionCommand());
		switch (ae.getActionCommand()) {
		case "Load a Request":
			view.updateUIData("request");
			break;
		case "Connect":
			view.updateUIData("connect");
			break;
		case "Login":
			view.updateUIData("login");
			break;
		case "Show Statistics":
			view.updateUIData("stat");
			break;
		default:
			break;
		}
		
	}
	
	public void update(Observable obs, Object obj) {
		if(obs instanceof CacheUnitView) {
			Map<String, Object> ud = new HashMap<>();
			ud.put("mainView", obj);
			model.updateModelData(ud);
		}
		else if (obs instanceof Model) {
			view.updateUIData(obj);
		}
	}
}
