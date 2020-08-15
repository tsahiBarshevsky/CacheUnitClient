package com.hit.driver;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import com.hit.controller.CacheUnitController;
import com.hit.controller.ConnectController;
import com.hit.controller.Controller;
import com.hit.controller.LoginController;
import com.hit.controller.RequestController;
import com.hit.model.CacheUnitModel;
import com.hit.model.Model;
import com.hit.view.CacheUnitView;
import com.hit.view.ConnectView;
import com.hit.view.LoginView;
import com.hit.view.RequestView;
import com.hit.view.View;


public class Driver extends Object {

	int port;
	
	public Driver() {
		port = 12345;
	}
	
	public static void main(String[] args) {
		Model model = new CacheUnitModel();
		View mainView = new CacheUnitView();
		Controller controller = new CacheUnitController(mainView, model);
		
		View connectView = new ConnectView();
		View requestView = new RequestView();
		View loginView = new LoginView();
		
		Controller connectController = new ConnectController(connectView, model);
		Controller loginController = new LoginController(loginView, model);
		Controller requestcontroller = new RequestController(requestView, model);
		
		connectView.addController((ActionListener) connectController);
		loginView.addController((ActionListener) loginController);
		requestView.addController((ActionListener) requestcontroller);
		
		((ConnectView)connectView).addObserver(connectController);
		((LoginView)loginView).addObserver(loginController);
		((RequestView)requestView).addObserver(requestcontroller);
		
		((CacheUnitModel)model).addObserver(connectController);
		((CacheUnitModel)model).addObserver(loginController);
		((CacheUnitModel)model).addObserver(requestcontroller);
		
		mainView.addController((ActionListener) controller);
		((CacheUnitModel)model).addObserver(controller);
		((CacheUnitView)mainView).addObserver(controller);
		mainView.start();
	}
	
	public void run() {
		
			try {
	            String serverHostname = new String("127.0.0.1");
	 
	            System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
	 
	            Socket echoSocket = null;
	            ObjectOutputStream outC = null;
	            ObjectInputStream inC = null;
	            
	 
	            try {
	                echoSocket = new Socket(serverHostname, port);
	                outC = new ObjectOutputStream(echoSocket.getOutputStream());
	                inC = new ObjectInputStream(echoSocket.getInputStream());
	            } catch (UnknownHostException e) {
	                System.err.println("Unknown host: " + serverHostname);
	                System.exit(1);
	            } catch (IOException e) {
	                System.err.println("Unable to get streams from server");
	                System.exit(1);
	            }
	 
	        	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

	 
	            while (true) {            	
	                System.out.print("client: ");
	                String userInput = stdIn.readLine();
	                if ("q".equals(userInput)) {
	                	//outC.writeObject(" ");
	                    outC.flush();
	                	echoSocket.close();
	                    break;
	                }
	                outC.writeObject(userInput);
	                outC.flush();
	               
	                try {
	        			TimeUnit.SECONDS.sleep(1);
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	                
	                Object o;
	                o = inC.readObject();
	                
	                if(o instanceof Boolean) {
	                	if((boolean)o == true) {
	                		System.out.println("good");
	                	}
	                	else {
	                		System.out.println("bad");
	                	}
	                }
	                
	                else {
	                	System.out.println("body: " + o.toString());
	                }
	            }
	 
	            outC.close();
	            inC.close();
	            stdIn.close();           
	        }
			catch(EOFException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	
}
