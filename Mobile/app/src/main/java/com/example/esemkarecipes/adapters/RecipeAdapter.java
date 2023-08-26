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
import com.example.esemkarecipes.databinding.RecipesListBinding;
import com.example.esemkarecipes.models.RecipeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {

//    private final JSONArray jsonArray;
//
//
//    public RecipeAdapter(JSONArray jsonArray) {
//        this.jsonArray = jsonArray;
//    }

    private final List<RecipeModel> recipeModels;

    public RecipeAdapter(List<RecipeModel> recipeModels) {
        this.recipeModels = recipeModels;
    }

    @NonNull
    @Override
    public RecipeAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(
                RecipesListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.VH holder, int position) {
        try {
            JSONObject jsonObject = recipeModels.get(position).jsonObject;
            holder.binding.tvRecipe.setText(jsonObject.getString("title"));
            holder.binding.tvDescription.setText(jsonObject.getString("description"));
            _Helper.httpGetImage(holder.context, "recipes/" + jsonObject.getString("image"), holder.binding.imgRecipe);

            holder.itemView.setOnClickListener(v -> {
                RecipeDetailActivity.selectedRecipe = jsonObject;
                holder.context.startActivity(new Intent(holder.context, RecipeDetailActivity.class));
            });
        } catch (JSONException ignored) {

        }


    }

    @Override
    public int getItemCount() {
        return recipeModels.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private RecipesListBinding binding;
        private Context context;

        public VH(RecipesListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
        }
    }
}
