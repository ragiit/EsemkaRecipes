package com.example.esemkarecipes.models;

import org.json.JSONObject;

public class RecipeModel {
    public  String name;
    public JSONObject jsonObject;

    public RecipeModel(String name, JSONObject jsonObject) {
        this.name = name;
        this.jsonObject = jsonObject;
    }
}
