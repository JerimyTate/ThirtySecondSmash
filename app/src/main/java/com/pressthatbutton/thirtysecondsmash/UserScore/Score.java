package com.pressthatbutton.thirtysecondsmash.UserScore;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.UUID;

@ParseClassName("Score")
public class Score extends ParseObject {

    public void setOwner(ParseUser currentUser){
        put("owner", currentUser);
    }

    public String getOwner(){
        return getString("owner");
    }

    public void setUuidString() {
        UUID uuid = UUID.randomUUID();
        put("uuid",uuid.toString());
    }

    public String getUuidString() {
        return getString("uuid");
    }

    public void setScore(Integer score) {
        put("score", score);
    }

    public Integer getScore(){
        return getInt("score");
    }

    public static ParseQuery<Score> getQuery(){
        return ParseQuery.getQuery(Score.class);
    }
}
