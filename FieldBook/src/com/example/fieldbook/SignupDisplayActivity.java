/*
 * Activity after the signup link where user can input the fields
 */
package com.example.fieldbook;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SignupDisplayActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MySQLiteHelper db = new MySQLiteHelper(this); // database 
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		//catches the field inputs
	    String username = intent.getStringExtra("username");
	    String password = intent.getStringExtra("password");
	    String userid = intent.getStringExtra("userid");
	    String firstname = intent.getStringExtra("firstname");
	    String middlename = intent.getStringExtra("middlename");
	    String lastname = intent.getStringExtra("lastname");
	    String birthdate = intent.getStringExtra("birthdate");
	    String companyid = intent.getStringExtra("companyid");
	    //setting the display style
	    TextView textView = new TextView(this);
	    textView.setTextSize(11);
	  /*  textView.setText("Username:"+ username + "\nPassword: " + password +
	    		"\nUser ID: " + userid + "\nFirst Name" + firstname + "\nMiddle Name: " 
	    		+ middlename + "\nLast Name: " + lastname + "\nBirthdate: " + birthdate + 
	    		"\nCompany ID: " + companyid);*/
	    
	    try{//adding the new user info
	    	db.addUser(new User(userid, username, password, firstname, middlename, lastname, birthdate, companyid));
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
        // get all users
        List<User> a = db.getAllUsers();
        String b= a.toString();
        textView.setText(b);
	    setContentView(textView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup_display, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_signup_display,
					container, false);
			return rootView;
		}
	}
}