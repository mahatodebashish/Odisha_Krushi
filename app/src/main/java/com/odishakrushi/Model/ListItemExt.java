package com.odishakrushi.Model;
/**
 * Created by Debashish on 16-09-2017.
 */
public class ListItemExt {

    private String questext;
    private String question_date;
    private String ans_dt;
    private String anstext;
    private String dropdown;

    public ListItemExt(String questext, String question_date, String ans_dt, String anstext, String dropdown) {
        this.questext = questext;
        this.question_date = question_date;
        this.ans_dt = ans_dt;
        this.anstext = anstext;
        this.dropdown = dropdown;
    }

    public String getQuestext() {
        return questext;
    }

    public String getQuestion_date() {
        return question_date;
    }

    public String getAns_dt() {
        return ans_dt;
    }

    public String getAnstext() {
        return anstext;
    }

    public String getDropdown() {
        return dropdown;
    }
}

