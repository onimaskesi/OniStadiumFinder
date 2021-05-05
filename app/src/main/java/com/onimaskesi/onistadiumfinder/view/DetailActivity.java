package com.onimaskesi.onistadiumfinder.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onimaskesi.onistadiumfinder.R;
import com.onimaskesi.onistadiumfinder.model.wikidesc.Page;
import com.onimaskesi.onistadiumfinder.viewmodel.DetailViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "OniDetail";

    TextView nameTV;
    TextView addressTV;
    TextView capacityTV;
    TextView yearTV;
    ProgressBar descLoadingPB;
    TextView descTitle;
    TextView descText;

    DetailViewModel viewModel;
    Page wikiPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTV = findViewById(R.id.nameTV);
        addressTV = findViewById(R.id.addressTV);
        capacityTV = findViewById(R.id.capacityTV);
        yearTV = findViewById(R.id.yearTV);
        descLoadingPB = findViewById(R.id.descLoadingPB);
        descTitle = findViewById(R.id.descTitle);
        descText = findViewById(R.id.descText);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        observeLiveData();

        Bundle intentExtras = getIntent().getExtras();

        String name = intentExtras.getCharSequence("name").toString();
        //String address = getIntent().getExtras().getCharSequence("address").toString();
        String capacity = intentExtras.getCharSequence("capacity").toString();
        Float lat = intentExtras.getFloat("lat");
        Float lng = intentExtras.getFloat("lng");

        viewModel.getData(name);

        nameTV.setText(name);

        setTextView(capacityTV, getString(R.string.capacity), capacity);

        String address = getAddressFromCoordinates(lat, lng);
        setTextView(addressTV, getString(R.string.address), address);

        setTextView(yearTV, getString(R.string.year), "-");

    }

    void observeLiveData(){

        descLoadingPB.setVisibility(View.VISIBLE);

        final Observer<Page> wikiPageObserver = new Observer<Page>() {
            @Override
            public void onChanged(@Nullable final Page page) {
                // Update the UI, in this case, a TextView.
                wikiPage = page;
                descTitle.setText(wikiPage.getTitle());
                descText.setText(wikiPage.getDescription());
                descText.setVisibility(View.VISIBLE);
                descLoadingPB.setVisibility(View.GONE);
            }
        };
        viewModel.wikiPage.observe(this, wikiPageObserver);

        final Observer<Boolean> isEmptyObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean isEmpty) {
                // Update the UI, in this case, a TextView.
                descLoadingPB.setVisibility(View.GONE);
                descText.setText("There is no information about " + nameTV.getText());
                descText.setVisibility(View.VISIBLE);
            }
        };
        viewModel.isEmpty.observe(this, isEmptyObserver);

        final Observer<Boolean> isErrorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean isError) {
                // Update the UI, in this case, a TextView.
                descLoadingPB.setVisibility(View.GONE);
                descText.setText("Error! Try Again Later!");
                descText.setVisibility(View.VISIBLE);
            }
        };
        viewModel.isError.observe(this, isErrorObserver);
    }

    void setTextView(TextView textView, String boldText, String text){
        String fullText =  "<b>" + boldText + "</b> " + text;
        textView.setText(Html.fromHtml(fullText));
    }

    String getAddressFromCoordinates(Float latitude, Float longitude){

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String addressText = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                    sb.append(address.getAddressLine(i)).append("\n");
                }

                if(address.getLocality() != null && address.getLocality() != "null"){
                    sb.append(address.getLocality()).append(", ");
                }
                if(address.getPostalCode() != null && address.getPostalCode() != "null"){
                    sb.append(address.getPostalCode()).append(", ");
                }
                if(address.getAdminArea() != null && address.getAdminArea() != "null"){
                    sb.append(address.getAdminArea()).append("/");
                }
                if(address.getCountryName() != null && address.getCountryName() != "null"){
                    sb.append(address.getCountryName());
                }

                addressText = sb.toString();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable connect to Geocoder", e);
        }

        return addressText;
    }

    public void closeBtnClicked(View view) {
        /*
        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        startActivity(intent);
        */
        finish();
    }
}