/*
 * SignUp field where user can enter for a new account
 */
package com.example.fieldbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SignupActivity extends ActionBarActivity {
	MySQLiteHelper db = new MySQLiteHelper(this);
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_signup);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}
	
	public void submitSignData(View view){
		 
		 	Intent intent = new Intent(this, SignupDisplayActivity.class);
		 	// catches the user input on fields
		    EditText editText1 = (EditText) findViewById(R.id.edit_userid);
		    EditText editText2 = (EditText) findViewById(R.id.edit_username);
		    EditText editText3 = (EditText) findViewById(R.id.edit_password);
		    EditText editText4 = (EditText) findViewById(R.id.edit_firstname);
		    EditText editText5 = (EditText) findViewById(R.id.edit_middlename);
		    EditText editText6 = (EditText) findViewById(R.id.edit_lastname);
		    EditText editText7 = (EditText) findViewById(R.id.edit_birthdate);
		    EditText editText8 = (EditText) findViewById(R.id.edit_companyid);
		    //conversion to string
		    String message1 = editText1.getText().toString();
		    String message2 = editText2.getText().toString();
		    String message3 = editText3.getText().toString();
		    String message4 = editText4.getText().toString();
		    String message5 = editText5.getText().toString();
		    String message6 = editText6.getText().toString();
		    String message7 = editText7.getText().toString();
		    String message8 = editText8.getText().toString();
		    //displaying the user input
		    intent.putExtra("userid", message1);
		    intent.putExtra("username", message2);
		    intent.putExtra("password", message3);
		    intent.putExtra("firstname", message4);
		    intent.putExtra("middlename", message5);
		    intent.putExtra("lastname", message6);
		    intent.putExtra("birthdate", message7);
		    intent.putExtra("compayid", message8);
		    
		    startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.fragment_signup,
					container, false);
			return rootView;
		}
	}
}

