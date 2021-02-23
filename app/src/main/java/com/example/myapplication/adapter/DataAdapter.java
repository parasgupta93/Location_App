package com.example.myapplication.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.model.LocationStructure;
import com.example.myapplication.R;
import com.example.myapplication.model.Constants;
import com.example.myapplication.view.LocationDetailedActivity;
import java.util.ArrayList;

/*
** This Class is Used to fetch the data from the POJO Location and bind them to the views.
**/
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<LocationStructure> locations;
    private Context mContext;

    public DataAdapter(Context mContext, ArrayList<LocationStructure> locations) {
        this.mContext = mContext;
        this.locations = locations;
    }

    /*
    ** inflating the cardView.
    **/
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {

        String place = locations.get(position).getPlace();
        String date = locations.get(position).getDate();
        holder.tv_card_main_title.setText(place);
        holder.tv_date.setText(date);

        Glide.with(mContext)
                .load(locations.get(position).getUrl())
                .thumbnail(0.1f)
                .centerCrop()
                .error(R.drawable.ic_placeholder)
                .into(holder.img_card_main);
    }

    /*
    ** Last parameter for binding the locations in OnBindViewHolder.
    **/
    @Override
    public int getItemCount() {
        return locations.size();
    }

    /*
    ** ViewHolder class which holds the different views in the recyclerView .
    **/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_card_main_title;
        private ImageView img_card_main;
        private TextView tv_date;

        public ViewHolder(View view) {
            super(view);
            tv_card_main_title = view.findViewById(R.id.tv_card_main_title);
            img_card_main = view.findViewById(R.id.img_card_main);
            tv_date = view.findViewById(R.id.date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String description = locations.get(getAdapterPosition()).getDescription();
            String date = locations.get(getAdapterPosition()).getDate();
            String url = locations.get(getAdapterPosition()).getUrl();
            String rate = locations.get(getAdapterPosition()).getRate();
            String place = locations.get(getAdapterPosition()).getPlace();
            Intent intent = new Intent(mContext, LocationDetailedActivity.class);

            intent.putExtra(Constants.INTENT_DESCRIPTION, description);
            intent.putExtra(Constants.INTENT_DATE, date);
            intent.putExtra(Constants.INTENT_URL, url);
            intent.putExtra(Constants.INTENT_RATE, rate);
            intent.putExtra(Constants.INTENT_PLACE, place);

            mContext.startActivity(intent);

        }
    }
}
