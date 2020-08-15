package com.hit.model;

import java.util.Map;
import java.util.Observable;

public class CacheUnitModel extends Observable implements Model {
	
	CacheUnitClient connect;
    String sessionID;

	public CacheUnitModel() {
		connect = new CacheUnitClient();
		sessionID = "";
	}
	
	@SuppressWarnings("unchecked")
	public <T> void updateModelData(T t) {
		if(t instanceof Map<?,?>) {
			Map<String, Object> update = (Map<String, Object>) t;
			if(update.containsKey("mainView")) {
				setChanged();
				notifyObservers(t);
			}
			else if(update.containsKey("connectController")) {
				Map<String, Map<String, String>> data = (Map<String, Map<String, String>>) t;
				
				String print = "connecting";
				update.put("server", print);
				setChanged();
				notifyObservers(update);
				
				connect.setData(data.get("connectController"));
				print = connect.connect();
				update.put("server", print);
				setChanged();
				notifyObservers(update);
			}
			else if(update.containsKey("loginController")) {
				Map<String, String> data = (Map<String, String>) update.get("loginController");
				
				String login = "{\"headers\":{\"action\":\"LOGIN\",\"username\":\"" + data.get("username") + "\",\"password\":\"" + data.get("password") + "\"},\"body\":[]}";
				String print;
				print = connect.send(login);
				if(!print.equals("login error")) {
					sessionID = print;
					print = "login OK";
				}
				update.put("server", print);
				setChanged();
				notifyObservers(update);
			}
			else if(update.containsKey("requestController")) {
				Map<String, Map<String, String>> data = (Map<String, Map<String, String>>) update.get("requestController");
								
				String request = "{\"headers\":{\"action\":\"" + data.get("request").get("type") + "\", \"sessionId\": \"" + sessionID + "\"},\"body\":[{\"dataModelId\":";
				
				Integer size = data.size()-1;
				for(Integer i =0;i<size-1;i++) {
					request+=data.get(i.toString()).get("ID") + ",\"content\":\"" + data.get(i.toString()).get("content") + "\"},{\"dataModelId\":";
				}
				size--;
				request+=data.get(size.toString()).get("ID") + ",\"content\":\"" + data.get(size.toString()).get("content") + "\"}]}";
				
				String print;
				print = connect.send(request);
				print = data.get("request").get("type") + " request: " + print;
				update.put("server", print);
				setChanged();
				notifyObservers(update);
			}
		}
	}
}
