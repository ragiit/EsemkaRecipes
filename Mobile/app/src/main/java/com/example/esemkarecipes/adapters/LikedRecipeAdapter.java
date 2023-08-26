package com.example.esemkarecipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemkarecipes._Helper;
import com.example.esemkarecipes.activities.RecipeDetailActivity;
import com.example.esemkarecipes.activities.RecipesActivity;
import com.example.esemkarecipes.databinding.CategoryListBinding;
import com.example.esemkarecipes.databinding.LikedRecipeListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LikedRecipeAdapter extends RecyclerView.Adapter<LikedRecipeAdapter.VH> {

    private final JSONArray jsonArray;

    public LikedRecipeAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public LikedRecipeAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(
                LikedRecipeListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LikedRecipeAdapter.VH holder, int position) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            _Helper.httpGetImage(holder.context, "recipes/" + jsonObject.getString("image"), holder.binding.imgRecipe);

            holder.itemView.setOnClickListener(v -> {
                RecipeDetailActivity.selectedRecipe = jsonObject;
                holder.context.startActivity(new Intent(holder.context, RecipeDetailActivity.class));
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class VH extends RecyclerView.ViewHolder {
        private LikedRecipeListBinding binding;
        private Context context;

        public VH(LikedRecipeListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
        }
    }
}
