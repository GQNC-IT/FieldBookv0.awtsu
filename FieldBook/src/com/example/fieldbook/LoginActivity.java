/*
 * The activity executed after the user clicked/tapped the login button
 */

package com.example.fieldbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
  
public class LoginActivity extends ActionBarActivity {
	
	List<Map<String,String>> data = new ArrayList<Map<String,String>>();
	Map<String, String> datum = new HashMap<String, String>(2);
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	private SimpleAdapter sa;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_fieldbook_display);

		loadActivity();
		
		
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}

	public void loadActivity(){
		File dir = new File("data/data/com.example.fieldbook/fieldbooks"); //Not dynamic yet
		File[] filelist = dir.listFiles();
		String[] namesOfFiles = new String[filelist.length];
		String temp1;
		
		HashMap<String,String> item;

		
		String md5 = "";
		boolean inTry = false;
		for (int i = 0; i < namesOfFiles.length; i++) {
			   temp1 = filelist[i].getName();
			   
			  
			   FileInputStream fis;
			try {
				fis = new FileInputStream(new File("data/data/com.example.fieldbook/fieldbooks" + "/" + temp1));
				md5 = new String(Hex.encodeHex(DigestUtils.md5(temp1)));
				inTry = true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
			   
			   namesOfFiles[i] = temp1.replaceAll(".db$","");
			  // if(inTry){
			//	   namesOfFiles[i] = namesOfFiles[i] + "\n" + md5;
			  // }
			   
			   item = new HashMap<String,String>();
			   item.put( "line1", namesOfFiles[i]);
			   item.put( "line2", md5);
			   list.add( item );

			   
			   
			   inTry = false;
			   //datum.put("First Line",namesOfFiles[i]);
			   //datum.put("Second Line",md5);
			   //data.add(datum);
		}
		
		
		final ListView listView = (ListView) findViewById(R.id.listView1);
		
		sa = new SimpleAdapter(this, list,
		R.layout.listview_twolines,
		new String[] { "line1","line2" },
		new int[] {R.id.line_a, R.id.line_b});
		listView.setAdapter( sa );
		//listView.setTextAlignment(4);

		
		
		
		//String[] values = new String[]{"A","B","C","D","E","F"};
		
		//int a = 1;
		/*for (a = 1; a < values.length; a++){
			values[a-1] = "Fieldbook" + a;
			
		}*/
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1, namesOfFiles){};

		

		//listView.setAdapter(adapter);
		
		 listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			 
           //  @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view,
                int position, long id) {
               
              // ListView Clicked item index
              //int itemPosition     = position;
              
              // ListView Clicked item value
             //	 TextView clickedView = (TextView) view;
            //	 String itemValue = (String) clickedView.getText();
             TextView textView = (TextView) view.findViewById(R.id.line_a);
             String itemValue = (String) textView.getText();
             //String  itemValue    = (String) listView.getItemAtPosition(position);
                 
               // Show Alert 
              
              renamePrompt(itemValue);
              
              return true;
            
             }

        }); 
		
	}
	
	
	public void logOut(){
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void renamePrompt(final String oldstring){
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Rename Database");
		builder.setMessage("Name: ");
		
		final EditText input = new EditText(this);
		builder.setView(input);
		
		builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  reNaming(oldstring,value);
			  // Do something with value!
			  }
			});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		//loadActivity();
		
	}
	
	public void reNaming(String oldstring, String newstring){
		File file = new File("data/data/com.example.fieldbook/fieldbooks/" + oldstring + ".db");
		File file2 = new File("data/data/com.example.fieldbook/fieldbooks/" + newstring + ".db");
		boolean success = file.renameTo(file2);
		if(success){
			Toast.makeText(getApplicationContext(),
	                 "Sucessfully changed name from " + oldstring + " to " + newstring, Toast.LENGTH_LONG)
	                 .show();
		}
		finish();
		reappear();
		//loadActivity();
		
	}
	
	public void createNew(View view){
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Create New Database");
		builder.setMessage("Name: ");
		
		final EditText input = new EditText(this);
		builder.setView(input);
		
		builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  File file = new File("data/data/com.example.fieldbook/fieldbooks/" + value + ".db");
			  try {
				if (file.createNewFile()){
				        System.out.println("File is created!");
				        finish();
						reappear();
				      }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  // Do something with value!
			  }
			});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		//loadActivity();
		
		
	}
	
	public void generateXLS(View view){
		
		Toast toast = Toast.makeText(getApplicationContext(), "Excel file has been made", Toast.LENGTH_SHORT);
		toast.show();
		String sdCard = Environment.getExternalStorageDirectory().getPath();
		File directory = new File (sdCard + "/excelfiles");
		
		Log.i("Excel",sdCard+ "/excelfiles");
		
		directory.mkdirs();
		
		MySQLiteHelper dbHelper = new MySQLiteHelper(this);
		SQLiteDatabase db = openOrCreateDatabase("UserDB.db", MODE_PRIVATE, null);
		Cursor cursor = db.rawQuery("select * from users", null);
		int rowCount = cursor.getCount();
		if(cursor != null)
			cursor.moveToFirst();
		
		String[] colHeads = dbHelper.getColHeads(); 
		
		try{
			WritableWorkbook workbook = Workbook.createWorkbook(new File(directory, "ExcelFile-0.1.xls"));
			WritableSheet worksheet = workbook.createSheet("users", 0);
			int colCount = cursor.getColumnCount();
			for(int i = 0; i < colCount; i++){
				Label label = new Label(i, 0, colHeads[i]);
				worksheet.addCell(label);
			}
			
			int rows = 0;
			cursor.moveToFirst();
			while(cursor.isAfterLast() == false){
				rows++;
				for(int i = 0; i < colCount; i++){
					Label label = new Label(i, rows, cursor.getString(i));
					
					worksheet.addCell(label);
				}
				cursor.moveToNext();
			}
			
			
			cursor.close();
			workbook.write();
			workbook.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void generateTSV(View view){

		String sdCard = Environment.getExternalStorageDirectory().getPath();
		File directory = new File (sdCard + "/tsvfiles");
		
		Log.i("Excel",sdCard+ "/tsvfiles");
		
		directory.mkdirs();
		
		MySQLiteHelper dbHelper = new MySQLiteHelper(this);
		SQLiteDatabase db = openOrCreateDatabase("UserDB.db", MODE_PRIVATE, null);
		Cursor cursor = db.rawQuery("select * from users", null);
		int rowCount = cursor.getCount();
		if(cursor != null)
			cursor.moveToFirst();
		
		String[] colHeads = dbHelper.getColHeads(); 
		
		try{
			File tsvFile = new File(directory, "TSVFile-0.1.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(tsvFile));
			
			int colCount = cursor.getColumnCount();
			for(int i = 0; i < colCount; i++){
				bw.write(colHeads[i]+"\t");
			}
			bw.write("\n");
			cursor.moveToFirst();
			while(cursor.isAfterLast() == false){
				for(int i = 0; i < colCount; i++){
					bw.write(cursor.getString(i)+"\t");
				}
				bw.write("\n");
				cursor.moveToNext();
			}
			cursor.close();
			bw.flush();
			bw.close();
			
			Toast toast = Toast.makeText(getApplicationContext(), "TSV File made", Toast.LENGTH_SHORT);
			toast.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void reappear(){
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.login, menu);
		//return true;
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_login_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (item.getItemId()) {
		case R.id.action_share:
			final String [] items=new String []{"Excel","Tab Separated File"};
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("Items alert");

			builder.setItems(items, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
				if(which == 0){
					generateXLS(new View(getApplicationContext()));
				}
				else if(which == 1){
					generateTSV(new View(getApplicationContext()));
				}
			}
			});

			builder.show();
            return true;
		case R.id.action_import_export:
            //logout();
            return true;
        case R.id.action_logout:
            logOut();
            return true;
        case R.id.action_settings:
            //openSettings();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
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
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			return rootView;
		}
	}

}
