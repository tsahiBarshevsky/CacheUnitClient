package com.hit.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

public class CacheUnitClient extends Object	 {

	int port;
	String portS;
	
	String serverHostname;
	
	Socket echoSocket;
    ObjectOutputStream outC;
    ObjectInputStream inC;
    
    Object o;
    	
	public CacheUnitClient() {
		
		echoSocket = null;
        outC = null;
        inC = null;		
	}
	
	public String connect() {
		try {
			echoSocket = new Socket(serverHostname, port);
            outC = new ObjectOutputStream(echoSocket.getOutputStream());
            inC = new ObjectInputStream(echoSocket.getInputStream());
            
            System.out.println("server");
            
            o = inC.readObject();
            return (String)o.toString(); 
            
        } catch (UnknownHostException e) {
            return "unknown host " + serverHostname;
        } catch (IOException e) {
        	e.printStackTrace();
            return "unable to get stream from server";
        } catch (Exception e) {
         	e.printStackTrace();
         	return "error";
        } 
	}
	
	public String send(String request) {
		try {
			if ("quit".equals(request)) {
				outC.writeObject("stop");
				outC.flush();
				echoSocket.close();
				return "disconnected";
			}
			outC.writeObject(request);
			outC.flush();             
        
        
			o = inC.readObject();
        
			if(o == null) {
				return "login error";
			}
			else if(o instanceof Boolean) {
				if((boolean)o == true) {
					return "good";
				} 
				else {
					return "bad";
				}
			}
        
			else if(o.toString().equals("array")) {
				Integer size = (Integer)inC.readObject();
				String ret = new String("");
				for(int i=0;i<size;i++) {
					ret += "\n" + inC.readObject().toString();
				}
				return ret;
			}
        
			else {
				return o.toString();
			}
		}
        catch(EOFException e) {
			e.printStackTrace();
			return "EOF error";
		}
		catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
	}
	
	public void setData(Map<String, String> d) {
		Map<String, String> data = d;
		serverHostname = data.get("ip");
		portS = data.get("port");
		int size = portS.length();
		port = 0;
		for (int i=0;i<size;i++) {
			port *=10;
			port += portS.charAt(i) -48;
		}
	}
	
	
}
