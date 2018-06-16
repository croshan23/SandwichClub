package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = (ImageView) findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Setting origin data
        TextView originLabelTV = (TextView) findViewById(R.id.origin_tv);
        originLabelTV.setText(sandwich.getPlaceOfOrigin());

        //Setting AKA
        TextView alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);
        List<String> akaList = sandwich.getAlsoKnownAs();
        String akaString = "";
        for (String aka : akaList) {
            if (akaString.isEmpty())
                akaString += aka;
            else
                akaString += ", " + aka;
        }
        alsoKnownAsTV.setText(akaString);

        //Setting Description
        TextView description = (TextView) findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        //Setting Ingredients
        TextView ingredients = (TextView) findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        String ingredientsString = "";
        for (String ingredient : ingredientsList) {
            if (ingredientsString.isEmpty())
                ingredientsString += ingredient;
            else
                ingredientsString += ", " + ingredient;
        }
        ingredients.setText(ingredientsString);
    }
}
