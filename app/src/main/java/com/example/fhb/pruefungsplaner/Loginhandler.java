package com.example.fhb.pruefungsplaner;
//////////////////////////////
// Loginhandler
//
//
//
// autor:
// inhalt:  übeprüfen und bearbeiten des loginprozess
// zugriffsdatum: 15.05.19
//
//
//
//
//
//
//////////////////////////////



public class Loginhandler {

    private String user;
    private String passwort;

    protected boolean checkUsername(String user){
         this.user = user;
         if (user.length() < 2){}
         else{return  false;}
         return  true;
        }


  public boolean checkPasswort(String passwort){
        this.passwort = passwort;
      if (passwort.length() < 2){}
      else{return  false;}
      return  true;
  }
}
