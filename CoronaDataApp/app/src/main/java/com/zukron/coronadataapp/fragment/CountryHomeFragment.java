package com.zukron.coronadataapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zukron.coronadataapp.R;
import com.zukron.coronadataapp.activity.CountryGraphActivity;
import com.zukron.coronadataapp.model.Country;
import com.zukron.coronadataapp.networking.APICountry;

import com.zukron.coronadataapp.tools.Util;

import org.threeten.bp.format.DateTimeFormatter;

public class CountryHomeFragment extends Fragment implements APICountry.OnResponse, View.OnClickListener {
    private TextView tvTitleCountry, tvConfirmed, tvRecovered, tvDeaths, tvLastUpdateCountry;
    private Button btnGraphCountry;
    private LinearLayout llStatus;
    private ProgressBar pbStatus, pbLastUpdateCountry;
    private APICountry apiCountry;
    private String country;

    public CountryHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_home, container, false);
    }

    public static CountryHomeFragment newInstance(String country) {
        CountryHomeFragment countryHomeFragment = new CountryHomeFragment();

        Bundle bundle = new Bundle();
        bundle.putString("country", country);
        countryHomeFragment.setArguments(bundle);

        return countryHomeFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiCountry = new APICountry(getContext(), this);

        tvTitleCountry = view.findViewById(R.id.tv_title_country);
        tvConfirmed = view.findViewById(R.id.tv_confirmed);
        tvRecovered = view.findViewById(R.id.tv_recovered);
        tvDeaths = view.findViewById(R.id.tv_deaths);
        tvLastUpdateCountry = view.findViewById(R.id.tv_last_update_country);
        pbStatus = view.findViewById(R.id.pb_status);
        pbLastUpdateCountry = view.findViewById(R.id.pb_last_update_country);
        llStatus = view.findViewById(R.id.ll_status);
        btnGraphCountry = view.findViewById(R.id.btn_graph_country);
        btnGraphCountry.setOnClickListener(this);

        setData();
    }

    private void setData() {
        if (getArguments() != null) {
            country = getArguments().getString("country");

            tvTitleCountry.setText(country);

            apiCountry.getCountryByIso(Country.getCountryIso(country));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), CountryGraphActivity.class);
        intent.putExtra("country", country);
        startActivity(intent);
    }

    @Override
    public void countryResponse(Country country) {
        pbStatus.setVisibility(View.INVISIBLE);
        pbLastUpdateCountry.setVisibility(View.INVISIBLE);
        llStatus.setVisibility(View.VISIBLE);
        tvLastUpdateCountry.setVisibility(View.VISIBLE);

        tvConfirmed.setText(Util.formatToThousand(country.getConfirmed()));
        tvRecovered.setText(Util.formatToThousand(country.getRecovered()));
        tvDeaths.setText(Util.formatToThousand(country.getDeaths()));

        // set format date and time
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String dateStr = country.getLastUpdate().format(date);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStr = country.getLastUpdate().format(time);
        String lastUpdate = dateStr + " - " + timeStr;

        tvLastUpdateCountry.setText(lastUpdate);
    }

    @Override
    public void errorResponse(VolleyError error) {
        error.printStackTrace();
    }
}