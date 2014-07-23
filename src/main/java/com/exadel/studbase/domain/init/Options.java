package com.exadel.studbase.domain.init;

/**
 * Created by Administrator on 21.07.2014.
 */
public class Options {
    final  String [] roles  = {"student","employee"};
    final  String [] states  = {"practice","working"};

    public String[] getStates() {
        return states;
    }

    public String[] getRoles() {
        return roles;
    }
}
