package com.example.fieldbook;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*
 * A class file where the database was initialized and defined
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserDB.db";
    //Table Name
    private static final String TABLE_USERS = "users";
    //For Database declaration
    private SQLiteDatabase db1;
    // User Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USERID = "userid";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_MIDDLENAME = "middlename";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_BIRTHDATE = "birthdate";
    private static final String KEY_COMPANYID = "companyid";

    private static final String[] COLUMNS = {KEY_ID,KEY_USERID,KEY_USERNAME,KEY_PASSWORD, KEY_FIRSTNAME,KEY_MIDDLENAME
    											,KEY_LASTNAME,KEY_BIRTHDATE,KEY_COMPANYID};
    
    public MySQLiteHelper(Context context) { // constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create user table
        String CREATE_USER_TABLE = "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "userid TEXT, "+
                "username TEXT, " +
                "password TEXT, "+
                "firstname TEXT, "+
                "middlename TEXT, "+
                "lastname TEXT, "+
                "birthdate TEXT, "+
                "companyid TEXT)";
        
        this.db1 = db;
 
        // create user table
        db.execSQL(CREATE_USER_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS users");
 
        // create fresh user table
        this.onCreate(db);
    }
    
    public SQLiteDatabase getDB(){
    	return this.db1;
    }
    public void addUser(User user){
        //for logging
    	Log.d("addUser", user.toString()); 

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();

    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, user.getUsername()); // get username 
		values.put(KEY_PASSWORD, user.getPassword()); // get password
		values.put(KEY_USERID, user.getUserID()); // get userid 
		values.put(KEY_FIRSTNAME, user.getFirstName()); // get firstname
		values.put(KEY_MIDDLENAME, user.getMiddleName()); // get middlename
		values.put(KEY_LASTNAME, user.getLastName()); // get lastname
		values.put(KEY_BIRTHDATE, user.getBirthdate()); // get birthdate 
		values.put(KEY_COMPANYID, user.getCompanyID()); // get companyid
		// 3. insert
		db.insert(TABLE_USERS, // table
		        null, //nullColumnHack
		        values); // key/value -> keys = column names/ values = column values
		
		// 4. close
		db.close(); 
    	}
    
    public User getUser(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_USERS, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        // 4. build user object
        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setUserID(cursor.getString(1));
        user.setUsername(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        user.setFirstName(cursor.getString(4));
        user.setMiddleName(cursor.getString(5));
        user.setLastName(cursor.getString(6));
        user.setBirthdate(cursor.getString(7));
        user.setCompanyID(cursor.getString(8));
        
        //log 
        Log.d("getUser("+id+")", user.toString());
     
        // 5. return book
        return user;
    }
    
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USERS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserID(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setFirstName(cursor.getString(4));
                user.setMiddleName(cursor.getString(5));
                user.setLastName(cursor.getString(6));
                user.setBirthdate(cursor.getString(7));
                user.setCompanyID(cursor.getString(8));
  
                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAllUsers()", users.toString()); //logcat
  
        // return books
        return users;
    }
    
    public int updateUser(User user) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("userid", user.getUserID()); // get userid
        values.put("password", user.getPassword()); // get author
        values.put("username", user.getUsername()); //getusername
        values.put("password", user.getPassword()); // get password
        values.put("firstname", user.getFirstName()); // get firstname
        values.put("middlename", user.getMiddleName()); //get middlename
        values.put("lastname", user.getLastName()); // get lastname
        values.put("birthdate", user.getBirthdate()); // get birthdate
        values.put("companyid", user.getCompanyID()); //get companyid
        // 3. updating row
        int i = db.update(TABLE_USERS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(user.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
    }
    
    public void deleteUser(User user) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_USERS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(user.getId()) }); //selections args
 
        // 3. close
        db.close();
 
        //log
        Log.d("deleteUser", user.toString());
    }
    
    public String getTableName(){
    	return this.TABLE_USERS;
    }
    
    public String getDatabaseName(){
    	return this.DATABASE_NAME;
    }
    
    public String[] getColHeads(){
    	return this.COLUMNS;
    }
    
    public boolean Login(String username, String password) throws SQLException{ // validation if the user input a valid account
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username=? AND password=?", new String[]{username,password});
    		if (mCursor != null) {
    			if(mCursor.getCount() > 0){
    				return true;
    			}
    		}
    		return false;
    	}
}