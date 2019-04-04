package mvc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.SecureAuthenticator;
import database.DataParser;
import database.User;

public class systemController implements ControllerInterface{
	
	private LoginView view;
	private LoginModel model;
	private MainFrame frame;
	private DataParser d;

	/* Constructor */
	public systemController(LoginView view, LoginModel model, MainFrame frame) {
		this.view = view;
		this.model = model;
		
		this.frame=frame;
		
		this.view.addLoginListener(new LoginListener());
		this.view.addRegisterScreenListener(new RegisterScreenListener());
	}
	
	
	/**
	 * 
	 * Switches screens to the registration page
	 *
	 */
	class RegisterScreenListener{
		public void SwitchView() {
			RegisterView newView= new RegisterView();
			RegisterModel regMod= new RegisterModel();
			RegisterController regCont= new RegisterController(newView, regMod, frame);
			
			setPanel(view , newView, frame);
			
			
		}
		
	}
	
	/**
	 * 
	 * Verifies the user when he presses the login button
	 *
	 */
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("button");
			String user = view.getUser();
			
			
			
			String pw = view.getPW();
			model.checkAuthentication(user, pw, auth);
			
			if (model.getAuthentication()) {
				
				// Check if current user is the admin, if he is then switch to admin window
				if(user.equals("admin") || model.checkAdmin(user, pw, auth)) {
					AdminView authView = new AdminView();
					AdminModel authModel = new AdminModel();
					AdminController authCont = new AdminController(authView, authModel);
					
					setPanel(view, authView, frame);
					
					authView.setName(user);
					current_user.setCurrentUserName(user);
					ResultSet ID = d.performQuery("SELECT ID FROM users_and_passwords");
					int id;
					try {
						ID.next();
						id = (int) ID.getObject("ID");
						//current_user.setUserID(id);
						//return (byte[])r.getObject("salt");
					}catch(SQLException e) {
						
					}
				}
				else {
					HomeView authView = new HomeView();
					HomeModel authModel = new HomeModel();
					HomeController authCont= new HomeController(authView, authModel,frame);
					
					setPanel(view, authView, frame);
					
					authView.setName(user);
					current_user.setCurrentUserName(user);
					
					
				}
				
				
			} else {
				JOptionPane.showMessageDialog(view, "Login unsuccessful");
			}
		}
	}
	
	

}
