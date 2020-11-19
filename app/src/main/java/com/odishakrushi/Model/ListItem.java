package com.odishakrushi.Model;
/**
 * Created by Debashish on 16-09-2017.
 */
public class ListItem {

    private String qna_id;
    private String user_id;
    private String dropdown;
    private String questext;
    private String is_answered;
    private String person_in_charge;
    private String question_date;
    private String ans_date;
    private String url;

    public ListItem(String qna_id, String user_id, String dropdown, String questext, String is_answered, String person_in_charge, String question_date, String ans_date,String url) {
        this.qna_id = qna_id;
        this.user_id = user_id;
        this.dropdown = dropdown;
        this.questext = questext;
        this.is_answered = is_answered;
        this.person_in_charge = person_in_charge;
        this.question_date = question_date;
        this.ans_date = ans_date;
        this.url=url;
    }

    public String getQna_id() {
        return qna_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDropdown() {
        return dropdown;
    }

    public String getQuestext() {
        return questext;
    }

    public String getIs_answered() {
        return is_answered;
    }

    public String getPerson_in_charge() {
        return person_in_charge;
    }

    public String getQuestion_date() {
        return question_date;
    }

    public String getAns_date() {
        return ans_date;
    }

    public String getUrl() {
        return url;
    }
}

