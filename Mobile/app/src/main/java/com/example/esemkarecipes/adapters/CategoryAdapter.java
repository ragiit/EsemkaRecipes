package com.example.esemkarecipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemkarecipes._Helper;
import com.example.esemkarecipes.activities.RecipeDetailActivity;
import com.example.esemkarecipes.activities.RecipesActivity;
import com.example.esemkarecipes.databinding.CategoryListBinding;
import com.example.esemkarecipes.databinding.RecipeDetailListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {

    private final JSONArray jsonArray;

    public CategoryAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public CategoryAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(
                CategoryListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.VH holder, int position) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            _Helper.httpGetImage(holder.context, "categories/" + jsonObject.getString("icon"), holder.binding.icCategory);
            holder.binding.category.setText(jsonObject.getString("name"));

            holder.itemView.setOnClickListener(v -> {
                RecipesActivity.selectedCategory = jsonObject;
                holder.context.startActivity(new Intent(holder.context, RecipesActivity.class));
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
        private final CategoryListBinding binding;
        private final Context context;

        public VH(CategoryListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
        }
    }
}
