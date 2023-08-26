package com.example.esemkarecipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.esemkarecipes._Helper;
import com.example.esemkarecipes.adapters.LikedRecipeAdapter;
import com.example.esemkarecipes.databinding.FragmentMyProfileBinding;
import com.example.esemkarecipes.models.ResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileFragment extends Fragment {

    private FragmentMyProfileBinding binding;
    private boolean shouldRefreshOnResume;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyProfileBinding.inflate(getLayoutInflater());

        getProfile();

        return binding.getRoot();
    }


    private void getProfile() {
        try {
            ResponseModel responseModel = _Helper.httpHelper("me");
            if (responseModel.code == 200) {
                JSONObject jsonObject = new JSONObject(responseModel.data);
                binding.tvFullName.setText(String.format("Hello, %s", jsonObject.getString("fullName")));
                _Helper.httpGetImage(getContext(), "profiles/" + jsonObject.getString("image"), binding.imgPerson);
            }

            ResponseModel getLikedRecipe = _Helper.httpHelper("me/liked-recipes");
            if (getLikedRecipe.code == 200) {
                JSONArray jsonArray = new JSONArray(getLikedRecipe.data);
                if (jsonArray.length() == 0) {
                } else {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.emptyLikeLayout.setVisibility(View.GONE);

                    LikedRecipeAdapter adapter = new LikedRecipeAdapter(new JSONArray(getLikedRecipe.data));
                    binding.recyclerView.setAdapter(adapter);
                }
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}