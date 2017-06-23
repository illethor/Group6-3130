package com.example.david.myapplication.DatabaseObjects;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    Boolean isCoach;
    String coachEmail;
    String userEmail;
    // athleteEmail will be filled upon user registering to a coach
    String athleteEmail;

    public User(){

    }
    /**
     * Used for creating a new athlete
     * @param isCoach True if this user is a coach false if this user is an athlete
     * @param coachEmail If the user is an athelete coachEmail should be put in
     * @param userEmail This is the email for the user
     * */
    public User(Boolean isCoach, String coachEmail, String userEmail){
        this.isCoach = isCoach;
        this.coachEmail = coachEmail;
        this.userEmail = userEmail;
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
}
