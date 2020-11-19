package com.odishakrushi.Model;

public class GiveUrOpinionModel {

    private String question,opt_1,opt_2,opt_3,opt_4,opt_5,id;

    public GiveUrOpinionModel(String question, String opt_1, String opt_2, String opt_3, String opt_4, String opt_5, String id) {
        this.question = question;
        this.opt_1 = opt_1;
        this.opt_2 = opt_2;
        this.opt_3 = opt_3;
        this.opt_4 = opt_4;
        this.opt_5 = opt_5;
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOpt_1() {
        return opt_1;
    }

    public String getOpt_2() {
        return opt_2;
    }

    public String getOpt_3() {
        return opt_3;
    }

    public String getOpt_4() {
        return opt_4;
    }

    public String getOpt_5() {
        return opt_5;
    }

    public String getId() {
        return id;
    }
}
