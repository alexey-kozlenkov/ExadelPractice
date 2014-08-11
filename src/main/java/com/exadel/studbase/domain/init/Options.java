package com.exadel.studbase.domain.init;

public class Options {
    final String[] roles = {"student", "employee"};
    final String[] states = {"training", "working"};

    public String[] getStates() {
        return states;
    }

    public String[] getRoles() {
        return roles;
    }
}
