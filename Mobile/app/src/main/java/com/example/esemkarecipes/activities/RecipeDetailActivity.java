package com.example.esemkarecipes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.esemkarecipes.R;
import com.example.esemkarecipes._Helper;
import com.example.esemkarecipes.adapters.CategoryAdapter;
import com.example.esemkarecipes.adapters.LikedRecipeAdapter;
import com.example.esemkarecipes.adapters.RecipeDetailAdapter;
import com.example.esemkarecipes.databinding.ActivityRecipeDetailBinding;
import com.example.esemkarecipes.databinding.FragmentCategoryBinding;
import com.example.esemkarecipes.fragments.MyProfileFragment;
import com.example.esemkarecipes.models.ResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private ActivityRecipeDetailBinding binding;
    public static JSONObject selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (selectedRecipe == null) {
            return;
        }

        if (getSupportActionBar() != null) {
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(selectedRecipe.getString("title"));
            } catch (JSONException ignored) {
            }
        }

        binding.imgLike.setOnClickListener(v -> {
            try {
                ResponseModel responseModel = _Helper.httpHelper("recipes/like-recipe?recipeId=" + selectedRecipe.getInt("id"));
                if (responseModel.code == 200) {
                    boolean check = Boolean.parseBoolean(responseModel.data);
                    if (check) {

                        binding.imgLike.setImageResource(R.drawable.liked);
                    } else {
                        binding.imgLike.setImageResource(R.drawable.like);
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        setRecipe();
//        getCategories();
    }


    private void setRecipe() {
        try {
            _Helper.httpGetImage(this, "recipes/" + selectedRecipe.getString("image"), binding.imgRecipe);
            _Helper.httpGetImage(this, "categories/" + selectedRecipe.getJSONObject("category").getString("icon"), binding.icCategory);

            binding.tvTitle.setText(selectedRecipe.getString("title"));
            binding.tvCookingTime.setText(String.format("Cooking Time Estimate: ±%s Minutes", selectedRecipe.getInt("cookingTimeEstimate")));
            binding.tvPriceEstimate.setText(String.format("Price Estimate: $%s", selectedRecipe.getInt("priceEstimate")));
            binding.tvCategory.setText(selectedRecipe.getJSONObject("category").getString("name"));
            binding.tvDescription.setText(selectedRecipe.getString("description"));

            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < selectedRecipe.getJSONArray("ingredients").length(); i++) {
                ingredients.add(selectedRecipe.getJSONArray("ingredients").getString(i));
            }

            List<String> steps = new ArrayList<>();
            for (int i = 0; i < selectedRecipe.getJSONArray("steps").length(); i++) {
                steps.add(selectedRecipe.getJSONArray("steps").getString(i));
            }

            RecipeDetailAdapter ingredientAdapter = new RecipeDetailAdapter(ingredients);
            RecipeDetailAdapter stepAdapter = new RecipeDetailAdapter(steps);

            binding.recyclerViewIngredients.setAdapter(ingredientAdapter);
            binding.recyclerViewSteps.setAdapter(stepAdapter);

            ResponseModel getLikedRecipe = _Helper.httpHelper("me/liked-recipes");
            if (getLikedRecipe.code == 200) {
                JSONArray jsonArray = new JSONArray(getLikedRecipe.data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    binding.imgLike.setImageResource(R.drawable.like);

                    if (jsonObject.getInt("id") == selectedRecipe.getInt("id")) {
                        binding.imgLike.setImageResource(R.drawable.liked);

                        break;
                    }
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCategories() {
        RecipeDetailAdapter adapter = new RecipeDetailAdapter(Arrays.asList(
                "1 Onion",
                "5 Micin",
                "200 Cabe Rawit",
                "6 Cabe garam"
        ));
        binding.recyclerViewIngredients.setAdapter(adapter);

        RecipeDetailAdapter adapter2 = new RecipeDetailAdapter(Arrays.asList(
                "Campur semua bahan jadi satu",
                "Masukkan kulkas selama 2 tahun",
                "Setelah 2 tahun, tumis semua bahannya",
                "Siap makan!"
        ));
        binding.recyclerViewSteps.setAdapter(adapter2);
    }
}