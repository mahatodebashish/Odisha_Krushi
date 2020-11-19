package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 8/30/2018.
 */

public class ListViewStory {

    private String dropdown,questext,url,category_name,sub_category_name;

    public ListViewStory(String dropdown, String questext, String url, String category_name, String sub_category_name) {
        this.dropdown = dropdown;
        this.questext = questext;
        this.url = url;
        this.category_name = category_name;
        this.sub_category_name = sub_category_name;
    }

    public String getDropdown() {
        return dropdown;
    }

    public String getQuestext() {
        return questext;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }
}
