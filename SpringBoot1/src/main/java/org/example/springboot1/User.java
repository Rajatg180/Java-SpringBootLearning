package org.example.springboot1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    // we are using this annotation because the username coming from body has key as (user_name) so we need to map that to name
    @JsonProperty("user_name")
    public  String name;
    public  int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
