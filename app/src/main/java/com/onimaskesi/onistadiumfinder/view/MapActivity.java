package com.onimaskesi.onistadiumfinder.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.onimaskesi.onistadiumfinder.R;
import com.onimaskesi.onistadiumfinder.model.Stadium;
import com.onimaskesi.onistadiumfinder.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapViewModel viewModel;

    List<Stadium> stadiumList = new ArrayList<Stadium>();

    ProgressBar loadingPB;
    TextView errorTV;

    private final String MAPVIEW_BUNDLE_KEY = "F3C8B84DA5F570927CA26074B582B89B:E548515589CC3F1E6A76E54808326CFA0D28D1D06E19010B1EFAA5972C201D7AB9D86199E86E0407F393757DB6A629DC";
    private final String API_KEY = "CgB6e3x9uWWZtXoGYW6Ee8vRu4fqCIm86+7NuVUIdocvSrGezVvmm/xGfihBmA+XpwO938YyVmqkId31pmKK21Zo";

    private MapView mMapView;
    private HuaweiMap hMap;

    private Marker mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapsInitializer.setApiKey(API_KEY);
        setContentView(R.layout.activity_map);

        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        viewModel.getData(getApplication());

        loadingPB = findViewById(R.id.loadingPB);
        errorTV = findViewById(R.id.errorTV);
        mMapView = findViewById(R.id.mapView);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

    }

    void observeLiveData(){

        loadingView(true);

        final Observer<List<Stadium>> stadiumListObserver = new Observer<List<Stadium>>() {
            @Override
            public void onChanged(@Nullable final List<Stadium> stadiums) {
                // Update the UI, in this case, a TextView.
                stadiumList = stadiums;
                addMarkerToStadiums();
                loadingView(false);
            }
        };

        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean isError) {
                // Update the UI, in this case, a TextView.
                if(isError){
                    errorTV.setVisibility(View.VISIBLE);
                    mMapView.setVisibility(View.GONE);
                    loadingPB.setVisibility(View.GONE);
                }
            }
        };

        viewModel.stadiumList.observe(this, stadiumListObserver);
        viewModel.isError.observe(this, errorObserver);

    }

    void loadingView(Boolean isLoading){
        if(isLoading){
            mMapView.setVisibility(View.GONE);
            loadingPB.setVisibility(View.VISIBLE);
        } else {
            mMapView.setVisibility(View.VISIBLE);
            loadingPB.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        hMap = huaweiMap;

        observeLiveData();

        hMap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                int index = Integer.parseInt(marker.getTitle());
                Stadium stadium = stadiumList.get(index);

                marker.setSnippet(stadium.getName());

                CharSequence name = stadium.getName();
                CharSequence capacity = stadium.getCapacity();

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("capacity",capacity);
                intent.putExtra("lat",stadium.getLatitude());
                intent.putExtra("lng",stadium.getLongitude());
                startActivity(intent);

                return false;
            }
        });
    }

    void addMarkerToStadiums(){
        if(stadiumList != null){

            for (int i = 0; i < stadiumList.size() ; i++) {

                Stadium stadium = stadiumList.get(i);
                LatLng stadiumLatLng = new LatLng(stadium.getLatitude(), stadium.getLongitude());
                addMarker(stadiumLatLng, i);

            }
        }

    }



    public void addMarker(LatLng latlng, int index) {

        MarkerOptions options = new MarkerOptions()
                .position(latlng)
                .title(String.valueOf(index));
        mMarker = hMap.addMarker(options);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


}