/*
 * Main Activity shows the home screen at the application start
 */
package com.example.fieldbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
//import com.example.fieldbook.R; 

public class MainActivity extends ActionBarActivity {
	MySQLiteHelper db = new MySQLiteHelper(this); // initialization for the database used
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	public void submitLogin(View view) {
		
	    EditText editText1 = (EditText) findViewById(R.id.edit_username); //handles the username field
	    EditText editText2 = (EditText) findViewById(R.id.edit_password); // handles the password field
	    
	    String message1 = editText1.getText().toString(); // conversion of the user input (username)into string
	    String message2 = editText2.getText().toString(); // conversion of the user input (password)into string

	    if (message1.length() > 0 && message2.length() > 0) { // if the user entered an input in both fields
	    	try {
	    		if (db.Login(message1, message2)) { // a toast will pop if correct username and password was entered
	    			Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show();
	    			Intent intent = new Intent(this, LoginActivity.class);
	    			intent.putExtra("username", message1);
	        		intent.putExtra("password", message2);
	    			startActivity(intent);
	    		} 
	    		else { // a toast will pop if invalid username and password was entered
	    			//Toast.makeText(this,"Invalid username or password",Toast.LENGTH_LONG).show();
	    			Intent intent = new Intent(this, LoginActivity.class);
	    			intent.putExtra("username", message1);
	        		intent.putExtra("password", message2);
	    			startActivity(intent);
	    			/*
	    			 * Naglagay muna ako ng intent dito para makapunta na dun sa class ko.
	    			 * 
	    			 * */
	    			
	    		} 
	    	} 
	    	catch (Exception e) { // a toast will pop if there was an error occured
	    		Toast.makeText(this, "Some problem occurred",Toast.LENGTH_LONG).show(); 
	    	}
	    } 
	    else { //if user didnt enter any inputs
	    	Toast.makeText(this,"Username or Password is empty", Toast.LENGTH_LONG).show();
	    }
	}

	public void submitSignup(View view){ // method for the next activity when signup link was tapped
		 Intent intent = new Intent(this, SignupActivity.class);
		 startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
