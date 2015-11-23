package com.pressthatbutton.thirtysecondsmash.User;

import java.io.Console;
import java.util.ArrayList;

public class User {
    private String userID;
    private String userName;
    private ArrayList<Integer> scores;

    public User(){
        this.userID = "";
        this.userName = "Unnamed User";
        this.scores = new ArrayList<Integer>(105);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public void addScore(int score){
        if(this.scores.size()<= 100){
            boolean added = false;
            int count = 0;
            int temp;
            do {
                temp = this.scores.get(count); //current item at current index
                if(temp < score){
                    this.scores.remove(count); //remove current item
                    this.scores.add(count,score); //add score at current item's index
                    this.scores.add(count+1,temp); //add current item to index after score (will shift everything down)
                    added = true;
                }
                ++count;
            }while (count < this.scores.size() && added == false);
        }else if (this.scores.size()>100){
            boolean replaced = false;
            int count = 0;
            int temp;
            do {
                temp = this.scores.get(count); //current item at current index
                if(temp < score){
                    this.scores.remove(count); //remove current item
                    this.scores.add(count,score); //add score at current item's index
                    this.scores.add(count+1,temp); //add current item to index after score (will shift everything down)
                    this.scores.remove(this.scores.size()-1); //remove last item in ArrayList (hopefully)
                    replaced = true;
                }
                ++count;
            }while (count < this.scores.size() && replaced == false);
        }else{
            //Do nothing, because this shouldn't happen.
        }
    }
}
