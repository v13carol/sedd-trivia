package com.cejj.sedd.model;

public class Player {
    
    private String uid, name, email, password, exp, rank, lvls, correct, wrong, level, gender, logro1, logro2, logro3, logro4, logro5, timestamp;

    public Player (){
        
    }

    public Player (String uid , String name , String email , String password , String exp , String rank , String lvls , String correct , String wrong , String level , String gender , String logro1 , String logro2 , String logro3 , String logro4 , String logro5 , String timestamp){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.exp = exp;
        this.rank = rank;
        this.lvls = lvls;
        this.correct = correct;
        this.wrong = wrong;
        this.level = level;
        this.gender = gender;
        this.logro1 = logro1;
        this.logro2 = logro2;
        this.logro3 = logro3;
        this.logro4 = logro4;
        this.logro5 = logro5;
        this.timestamp = timestamp;
    }

    public String getUid (){
        return uid;
    }

    public void setUid (String uid){
        this.uid = uid;
    }

    public String getName (){
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getEmail (){
        return email;
    }

    public void setEmail (String email){
        this.email = email;
    }

    public String getPassword (){
        return password;
    }

    public void setPassword (String password){
        this.password = password;
    }

    public String getExp (){
        return exp;
    }

    public void setExp (String exp){
        this.exp = exp;
    }

    public String getRank (){
        return rank;
    }

    public void setRank (String rank){
        this.rank = rank;
    }

    public String getLvls (){
        return lvls;
    }

    public void setLvls (String lvls){
        this.lvls = lvls;
    }

    public String getCorrect (){
        return correct;
    }

    public void setCorrect (String correct){
        this.correct = correct;
    }

    public String getWrong (){
        return wrong;
    }

    public void setWrong (String wrong){
        this.wrong = wrong;
    }

    public String getLevel (){
        return level;
    }

    public void setLevel (String level){
        this.level = level;
    }

    public String getGender (){
        return gender;
    }

    public void setGender (String gender){
        this.gender = gender;
    }

    public String getLogro1 (){
        return logro1;
    }

    public void setLogro1 (String logro1){
        this.logro1 = logro1;
    }

    public String getLogro2 (){
        return logro2;
    }

    public void setLogro2 (String logro2){
        this.logro2 = logro2;
    }

    public String getLogro3 (){
        return logro3;
    }

    public void setLogro3 (String logro3){
        this.logro3 = logro3;
    }

    public String getLogro4 (){
        return logro4;
    }

    public void setLogro4 (String logro4){
        this.logro4 = logro4;
    }

    public String getLogro5 (){
        return logro5;
    }

    public void setLogro5 (String logro5){
        this.logro5 = logro5;
    }

    public String getTimestamp (){
        return timestamp;
    }

    public void setTimestamp (String timestamp){
        this.timestamp = timestamp;
    }
}
