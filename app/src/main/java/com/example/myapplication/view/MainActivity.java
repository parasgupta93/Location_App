package com.example.myapplication.view;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.myapplication.R;
import com.example.myapplication.model.LocationStructure;
import com.example.myapplication.adapter.DataAdapter;
import com.example.myapplication.model.Constants;
import com.example.myapplication.model.TravelResponse;
import com.example.myapplication.network.ApiClient;
import com.example.myapplication.network.ApiInterface;
import com.example.myapplication.roomdatabase.LocationModel;
import com.example.myapplication.roomdatabase.LocationRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener , LocationRepository.LocationRepositoryListener {

    private ArrayList<LocationStructure> locationStructure = new ArrayList<>();
    private ArrayList<LocationModel> mDbList = new ArrayList<>();
    private DataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Parcelable listState;
    private TextView mCustomer;
    private TextView welcome;
    private LocationRepository mRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomer = findViewById(R.id.name);
        welcome = findViewById(R.id.welcome);
        mRepository = new LocationRepository(this,this);
        createRecyclerView();
        onLoadingSwipeRefreshLayout();
    }

    private void createRecyclerView() {
        adapter = new DataAdapter(MainActivity.this, locationStructure);
        recyclerView = findViewById(R.id.card_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void loadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
       /* httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                return null;
            }
        });*/

        ApiInterface request = ApiClient.getClient(httpClient).create(ApiInterface.class);
        Log.d("Paras","request");
        Call<TravelResponse> call = request.getTravelDetail();
        call.enqueue(new Callback<TravelResponse>() {

            @Override
            public void onResponse(@NonNull Call<TravelResponse> call, @NonNull Response<TravelResponse> response) {
                Log.d("Paras","onresponse");

                if (response.isSuccessful() && response.body() !=null && response.body().getLocations() != null) {

                    if (!locationStructure.isEmpty()) {
                        locationStructure.clear();
                    }
                    locationStructure.addAll(response.body().getLocations());
                    Log.d("Paras","size: "+locationStructure.size());
                    String customer = "Hi "+response.body().getCust_name()+",";
                    mCustomer.setText(customer);
                    SharedPreferences mPreferences = getSharedPreferences("com.example.location",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("Customer_Name",customer);
                    editor.apply();
                    mCustomer.setVisibility(View.VISIBLE);
                    welcome.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    mRepository.insertItem(locationStructure);

                }
            }


            @Override
            public void onFailure(@NonNull Call<TravelResponse> call, @NonNull Throwable t) {
                Log.d("Paras","onfailure");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadJSON();
    }


    private void onLoadingSwipeRefreshLayout() {
        if (!isNetworkAvailable()) {
            mRepository.getItem();
        }
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        loadJSON();
                    }
                }
        );
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        listState = recyclerView.getLayoutManager().onSaveInstanceState();
        bundle.putParcelable(Constants.RECYCLER_STATE_KEY, listState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(Constants.RECYCLER_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        if (listState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
        super.onResume();

    }

    @Override
    public void onLocalRepoData(final ArrayList<LocationStructure> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                locationStructure.clear();
                locationStructure.addAll(list);
                adapter.notifyDataSetChanged();
                welcome.setVisibility(View.VISIBLE);
                SharedPreferences mPreferences = getSharedPreferences("com.example.location",Context.MODE_PRIVATE);
                String customer = mPreferences.getString("Customer_Name", "");
                mCustomer.setText(customer);
                mCustomer.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Please turn on the Internet for latest update.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void pnLocalRePoFail(String msg) {

    }
}
