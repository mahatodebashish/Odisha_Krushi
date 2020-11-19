package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 9/20/2018.
 */

public class ListOpenForum
{
    private String url,open_forum_question_id,farmer_name,questext,q_id,is_answer,date,group,first_name,farmer_profile_img,subject;

    public ListOpenForum(String url,String open_forum_question_id, String farmer_name, String questext, String q_id, String is_answer, String date, String group, String first_name,String farmer_profile_img, String subject) {
        this.url=url;
        this.open_forum_question_id = open_forum_question_id;
        this.farmer_name = farmer_name;
        this.questext = questext;
        this.q_id = q_id;
        this.is_answer = is_answer;
        this.date = date;
        this.group = group;
        this.first_name = first_name;
        this.farmer_profile_img = farmer_profile_img;
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public String getOpen_forum_question_id() {
        return open_forum_question_id;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public String getQuestext() {
        return questext;
    }

    public String getQ_id() {
        return q_id;
    }

    public String getIs_answer() {
        return is_answer;
    }

    public String getDate() {
        return date;
    }

    public String getGroup() {
        return group;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getFarmer_profile_img() {
        return farmer_profile_img;
    }

    public String getSubject() {
        return subject;
    }
}
