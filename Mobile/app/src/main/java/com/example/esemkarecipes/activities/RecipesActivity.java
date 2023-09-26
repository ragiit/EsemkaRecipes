package com.example.esemkarecipes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.esemkarecipes._Helper;
import com.example.esemkarecipes.adapters.RecipeAdapter;
import com.example.esemkarecipes.databinding.ActivityRecipesBinding;
import com.example.esemkarecipes.models.RecipeModel;
import com.example.esemkarecipes.models.ResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipesActivity extends AppCompatActivity {

    private ActivityRecipesBinding binding;
    public static JSONObject selectedCategory;
    private List<RecipeModel> recipeModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (selectedCategory == null) {
            return;
        }

        if (getSupportActionBar() != null) {
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(selectedCategory.getString("name"));
            } catch (JSONException ignored) {
            }
        }

        binding.edtSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               List<RecipeModel> search = recipeModels.stream().filter(x -> x.name.trim().toLowerCase().contains(binding.edtSearch.getEditText().getText().toString().trim().toLowerCase())).collect(Collectors.toList());

                               RecipeAdapter adapter = new RecipeAdapter(search);
                               binding.recyclerView.setAdapter(adapter);
                           }
                       });
                   }
               }).start();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        getRecipes();
    }

    private void getRecipes() {
        try {
            ResponseModel responseModel = _Helper.httpHelper("recipes?categoryId=" + selectedCategory.getInt("id"));
            if (responseModel.code == 200) {
                JSONArray jsonArray = new JSONArray(responseModel.data);
                if (jsonArray.length() == 0) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.edtSearch.setVisibility(View.GONE);
                    binding.emptyRecileLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.edtSearch.setHint("Search " + jsonArray.length() + " recipes");
                    binding.edtSearch.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.emptyRecileLayout.setVisibility(View.GONE);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        recipeModels.add(new RecipeModel(jsonObject.getString("title"), jsonObject));
                    }

                    RecipeAdapter adapter = new RecipeAdapter(recipeModels);
                    binding.recyclerView.setAdapter(adapter);


//                    RecipeAdapter adapter = new RecipeAdapter(jsonArray);
//                    binding.recyclerView.setAdapter(adapter);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}