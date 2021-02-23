package com.example.myapplication.view;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Constants;

public class LocationDetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detailed_new);
        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setData();
    }

    private void setData() {
        String place = getIntent().getStringExtra(Constants.INTENT_PLACE);
        String description = getIntent().getStringExtra(Constants.INTENT_DESCRIPTION);
        String date = getIntent().getStringExtra(Constants.INTENT_DATE);
        String imgURL = getIntent().getStringExtra(Constants.INTENT_URL);
        String rate = getIntent().getStringExtra(Constants.INTENT_RATE);

        TextView content_Location = findViewById(R.id.tv_Title);
        content_Location.setText(place);

        TextView content_Description = findViewById(R.id.tv_Description);
        content_Description.setText(description);

        TextView content_date = findViewById(R.id.tv_Date);
        content_date.setText(date);

        TextView content_rate = findViewById(R.id.tv_Price);
        content_rate.setText("₹ "+rate);

        ImageView collapsingImage = findViewById(R.id.img_Header);
        Glide.with(this)
                .load(imgURL)
                .centerCrop()
                .error(R.drawable.ic_placeholder)
                .crossFade()
                .into(collapsingImage);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
