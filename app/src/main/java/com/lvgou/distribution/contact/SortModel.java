package com.lvgou.distribution.contact;

public class SortModel {

    private String name;
    private String sortLetters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public SortModel(String name, String sortLetters) {
        this.name = name;
        this.sortLetters = sortLetters;
    }

}
