package com.wolkabout.hexiwear.activity.DatabaseObjects;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public Boolean isCoach;
    public String coachEmail;
    public String userEmail;
    // athleteEmail will be filled upon user registering to a coach
    public String athleteEmail;
    // Message for an athlete to hold
    public String message;
    // Notification if a message response was made for a coach
    public Boolean messageNotification;

    public User(){

    }
    /**
     * Used for creating a new athlete
     * @param isCoach True if this user is a coach false if this user is an athlete
     * @param coachEmail If the user is an athlete coachEmail should be put in
     * @param userEmail This is the email for the user
     * */
    public User(Boolean isCoach, String coachEmail, String userEmail){
        this.isCoach = isCoach;
        this.coachEmail = coachEmail;
        this.userEmail = userEmail;
        this.message = "";
    }
    /**
     * Used for creating a new coach
     * @param isCoach True if this user is a coach false if this user is an athlete
     * @param userEmail This is the email for the user
     * */
    public User(Boolean isCoach, String userEmail){
        this.isCoach = isCoach;
        this.userEmail = userEmail;
        this.athleteEmail = "";
        this.messageNotification = false;
    }
    @Exclude
    public Boolean getCoach() {
        return isCoach;
    }
    @Exclude
    public String getCoachEmail() {
        return coachEmail;
    }
    @Exclude
    public String getUserEmail() {
        return userEmail;
    }
    @Exclude
    public String getAtheleteEmail() {
        return athleteEmail;
    }
    @Exclude
    public String getMessage(){
        return message;
    }
    @Exclude
    public Boolean getMessageNotification(){
        return messageNotification;
    }
    @Exclude
    public void setCoach(Boolean coach) {
        isCoach = coach;
    }
    @Exclude
    public void setCoachEmail(String coachEmail) {
        this.coachEmail = coachEmail;
    }
    @Exclude
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    @Exclude
    public void setAthleteEmail(String atheleteEmail) {
        this.athleteEmail = atheleteEmail;
    }
    @Exclude
    public void setMessage(String message){
        this.message = message;
    }
    @Exclude
    public void setMessageNotification(Boolean messageNotification){
        this.messageNotification = messageNotification;
    }
}
