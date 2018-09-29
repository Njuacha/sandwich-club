package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwich;
    ActivityDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(detailBinding.imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        setOtherNames();
        setIngredients();
        setPlaceOfOrigin();
        setDescription();

    }
    // Sets the other names on the UI
    private void setOtherNames(){
        // Gets the other names
        List<String> otherNames = sandwich.getAlsoKnownAs();

        if( otherNames.size() == 1) {

            detailBinding.alsoKnownTv.setText(otherNames.get(0));

        }else if(otherNames.size() > 1){
            int lastIndex = otherNames.size() - 1;
            // Gets the last name and last but one name so it can be appended differently from the other names.
            String lastName = otherNames.get(lastIndex);
            String lastButOneName = otherNames.get(lastIndex-1);
            // Removes the last name and last but one name
            otherNames.remove(lastIndex);
            otherNames.remove(lastIndex-1);

            // Removes the default text which is "Data not available"
            detailBinding.alsoKnownTv.setText("");
            // Appends the names with a comma after except for the last name which has been removed already
            for (String name : otherNames) {
                detailBinding.alsoKnownTv.append(name + ", ");
            }
            // Appends the last and last but one name
            detailBinding.alsoKnownTv.append(lastButOneName + " and " + lastName);
        }
    }
    // Sets the ingredients on the UI
    private void setIngredients(){
        // Gets the other names
        List<String> otherNames = sandwich.getIngredients();

        if( otherNames.size() == 1) {

            detailBinding.ingredientsTv.setText(otherNames.get(0));

        }else if(otherNames.size() > 1){
            int lastIndex = otherNames.size() - 1;
            // Gets the last name and last but one name so it can be appended differently from the other names.
            String lastName = otherNames.get(lastIndex);
            String lastButOneName = otherNames.get(lastIndex-1);
            // Removes the last name and last but one name
            otherNames.remove(lastIndex);
            otherNames.remove(lastIndex-1);
            // Removes the default text which is "Data not available"
            detailBinding.ingredientsTv.setText("");
            // Appends the names with a comma after except for the last name which has been removed already
            for (String name : otherNames) {
                detailBinding.ingredientsTv.append(name + ", ");
            }
            // Appends the last and last but one name
            detailBinding.ingredientsTv.append(lastButOneName + " and " + lastName);
        }
    }

    private void setDescription(){
        if(!sandwich.getDescription().isEmpty()) detailBinding.descriptionTv.setText(sandwich.getDescription());
    }

    private void setPlaceOfOrigin() {
        if (!sandwich.getPlaceOfOrigin().isEmpty()) detailBinding.originTv.setText(sandwich.getPlaceOfOrigin());
    }
}
