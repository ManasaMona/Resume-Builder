package com.example.manasaa.resumebuilder.Model;

/**
 * Created by manasa.a on 02-03-2017.
 */

public class UserDetails {
    int userId;
    String userName, userEmail , userProfileLink;

    //constructor
    public UserDetails(){
    }
    //constructor
    public UserDetails(int id,String Name, String Email ,String ProfileLink){
        this.userId=id;
        this.userEmail=Email;
        this.userName =Name;
        this.userProfileLink = ProfileLink;
    }
    //constructor
    public UserDetails(String Name, String Email ,String ProfileLink){
        this.userEmail=Email;
        this.userName =Name;
        this.userProfileLink = ProfileLink;

    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileLink() {
        return userProfileLink;
    }

    public void setUserProfileLink(String userProfileLink) {
        this.userProfileLink = userProfileLink;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
