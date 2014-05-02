/*
 * Definition for the User table
 */
package com.example.fieldbook;

public class User {

	private int id;
    private String username;
    private String password;
    private String userid;
    private String firstname;
    private String middlename;
    private String lastname;
    private String birthdate;
    private String companyid;
 
    //constructors
    public User(){}
 
    public User(String userid, String username, String password, String firstname, String middlename, String lastname, String birthdate, String companyid) {
        super();
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.companyid = companyid;   
    }
 
    //getters & setters
    public long getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public String getUsername() {
          return username;
        }

        public void setUsername(String username) {
          this.username = username;
        }
      
      public String getUserID() {
        return userid;
      }

      public void setUserID(String userid) {
        this.userid = userid;
      }
      
      public String getFirstName() {
         return firstname;
      }

      public void setFirstName(String firstname) {
         this.firstname= firstname;
      }
      
      public String getMiddleName() {
          return middlename;
        }

      public void setMiddleName(String middlename) {
          this.middlename = middlename;
      }
        
      public String getLastName(){
           return lastname;
      }

      public void setLastName(String lastname) {
           this.lastname= lastname;
      }
      
      public String getBirthdate() {
          return birthdate;
      }

      public void setBirthdate(String birthdate) {
          this.birthdate= birthdate;
      }
      
      public String getCompanyID() {
          return companyid;
      }

      public void setCompanyID(String companyid) {
          this.companyid= companyid;
      }

      public String getPassword() {
          return password;
      }

      public void setPassword(String password) {
          this.password= password;
      }
 
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", userid=" +userid+ ", firstname" + firstname+ ", middlename" + middlename + 
        		", lastname" + lastname + ", birthdate" + birthdate + ", companyid" + companyid + " ]";
    }
}