package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 10/3/2018.
 */

public class ListAnswers {

    public String qna_id, questext,question_date,anstext,ans_dt,dropdown,is_answered,url,rating;

    public ListAnswers(String qna_id, String questext, String question_date, String anstext, String ans_dt, String dropdown, String is_answered, String url,String rating) {
        this.qna_id = qna_id;
        this.questext = questext;
        this.question_date = question_date;
        this.anstext = anstext;
        this.ans_dt = ans_dt;
        this.dropdown = dropdown;
        this.is_answered = is_answered;
        this.url = url;
        this.rating=rating;
    }

    public String getQna_id() {
        return qna_id;
    }

    public String getQuestext() {
        return questext;
    }

    public String getQuestion_date() {
        return question_date;
    }

    public String getAnstext() {
        return anstext;
    }

    public String getAns_dt() {
        return ans_dt;
    }

    public String getDropdown() {
        return dropdown;
    }

    public String getIs_answered() {
        return is_answered;
    }

    public String getUrl() {
        return url;
    }

    public String getRating() {
        return rating;
    }
}
