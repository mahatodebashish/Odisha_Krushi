package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 9/20/2018.
 */

public class ListOpenFanswerers {
    private String answer_text,group,first_name,ans_date;

    public ListOpenFanswerers(String answer_text, String group, String first_name, String ans_date) {
        this.answer_text = answer_text;
        this.group = group;
        this.first_name = first_name;
        this.ans_date = ans_date;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public String getGroup() {
        return group;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getAns_date() {
        return ans_date;
    }



}
