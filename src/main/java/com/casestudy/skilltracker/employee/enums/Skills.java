package com.casestudy.skilltracker.employee.enums;

public enum Skills {
    SPOKEN("SPOKEN"),
    COMMUNICATION("COMMUNICATION"),
    APTITUDE("APTITUDE"),
    HTMLCSSJAVASCRIPT("HTML-CSS-JAVASCRIPT"),
    ANGULAR("ANGULAR"),
    REACT("REACT"),
    SPRING("SPRING"),
    RESTFUL("RESTFUL"),
    HIBERNATE("HIBERNATE"),
    GIT("GIT"),
    DOCKER("DOCKER"),
    JENKIN("JENKIN"),
    AWS("AWS");

    Skills(String name) {
        this.name = name;
    }

    String name;

    public String getName() {
        return this.name;
    }


}
