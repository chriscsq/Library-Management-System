package mvc;

import java.io.IOException;
import common.SecureAuthenticator;
import database.DataParser;

public class LoginModel extends CurrentModel{

	public boolean authenticated = false;
	
	
	public void checkAuthentication(String x, String y, SecureAuthenticator auth) {
		
		if(auth.verifyLogin(x, y)) {
			this.authenticated=true;
			System.out.println("authenticated");
		}
		
		/*
		try {
			if (LoginVerifier.VerifyLogin(x, y)) {
				this.authenticated = true;
				System.out.println("authenticated");
				
			}
			else {
				System.out.println("PROBLEM");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	public boolean checkAdmin(String x, String y, SecureAuthenticator auth) {
		if(auth.verifyAdmin(x, y)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkLib(String user, String pw, SecureAuthenticator auth) {
		return auth.verifyAdmin(user, pw);
	}

	
	public boolean getAuthentication() {
		return this.authenticated;
	}
	
}



