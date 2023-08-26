package com.example.esemkarecipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemkarecipes.activities.RecipesActivity;
import com.example.esemkarecipes.databinding.RecipeDetailListBinding;
import com.example.esemkarecipes.databinding.RecipesListBinding;

import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.VH> {

    private List<String> strings;

    public  RecipeDetailAdapter(List<String> strings) {
        this.strings = strings;
    }

    @NonNull
    @Override
    public RecipeDetailAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(
                RecipeDetailListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailAdapter.VH holder, int position) {
        String string = strings.get(position);

        holder.binding.tvIngredient.setText(string);

        holder.itemView.setOnClickListener(v -> {
            holder.context.startActivity(new Intent(holder.context, RecipesActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private RecipeDetailListBinding binding;
        private Context context;

        public VH(RecipeDetailListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
        }
    }
}
