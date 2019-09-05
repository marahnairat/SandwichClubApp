package com.example.sandwichclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandwichclubapp.model.sandwich;
import com.example.sandwichclubapp.utils.JsonUtils;
import com.squareup.picasso.Picasso;

public class ShowActivity extends AppCompatActivity {
    public static final String ex_pos="extra_position";
    private static final int def_pos=-1;
    ImageView imageView;
    TextView mTextView;
    TextView mMoreDetails;
    TextView componentText;
    TextView descriptionText;
    private sandwich mSandwich;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        imageView = findViewById(R.id.img_id);
        mTextView = findViewById(R.id.origin_id);
        mMoreDetails =findViewById(R.id.details_id);
        componentText=findViewById(R.id.comp_id);
        descriptionText =findViewById(R.id.desc_id);
        Intent intent = getIntent();
        if (intent==null)
        {
            closeOnError();
        }
        int pos=intent.getIntExtra(ex_pos,def_pos);
        if (pos==def_pos)
        {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        String json =sandwiches[pos];
        mSandwich= JsonUtils.parseSandwichJson(json);
        if (mSandwich==null)
        {
            closeOnError();
            return;
        }

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,R.string.show_error_mess,Toast.LENGTH_SHORT).show();

    }


    private void populateUI() {
        Picasso.get()
                .load(mSandwich.getImage())
                .into(imageView);

        setTitle(mSandwich.getMainName());
        mTextView.setText(mSandwich.getPlaceOfOrigin());
        mMoreDetails.setText(TextUtils.join(", ",mSandwich.getAlsoKnownAs
                ()));
        componentText.setText(TextUtils.join(" , ",mSandwich.getIngredients()));
        mMoreDetails.setText(mSandwich.getDescription());
    }




}
