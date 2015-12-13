package com.pressthatbutton.thirtysecondsmash.UserScore;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Score")
public class Score extends ParseObject {

    public Score() {
        setScore(0);
    }

    public void setOwner(ParseUser currentUser){
        put("owner", currentUser);
    }

    public ParseUser getOwner(){
        return getParseUser("owner");
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
