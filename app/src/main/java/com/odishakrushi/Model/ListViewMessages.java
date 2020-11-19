package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 9/24/2018.
 */

public class ListViewMessages
{
    private String message_id,first_name,description,message_text,date_time;

    public ListViewMessages(String message_id,String first_name, String description, String message_text, String date_time) {
        this.message_id=message_id;
        this.first_name = first_name;
        this.description = description;
        this.message_text = message_text;
        this.date_time = date_time;
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage_text() {
        return message_text;
    }

    public String getDate_time() {
        return date_time;
    }
}
