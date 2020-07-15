package com.zukron.coronadataapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.zukron.coronadataapp.R;
import com.zukron.coronadataapp.activity.CountryGraphActivity;
import com.zukron.coronadataapp.model.Country;
import com.zukron.coronadataapp.model.Global;
import com.zukron.coronadataapp.model.Province;
import com.zukron.coronadataapp.networking.APICountry;
import com.zukron.coronadataapp.networking.APIGlobal;
import com.zukron.coronadataapp.networking.APIProvince;
import com.zukron.coronadataapp.tools.Util;

public class ProvinceHomeFragment extends Fragment implements APIProvince.OnResponse {
    private TextView tvTitleProvince, tvConfirmed, tvRecovered, tvDeaths;
    private LinearLayout llStatusGlobal;
    private ProgressBar pbStatusGlobal;
    private APIProvince apiProvince;

    public ProvinceHomeFragment() {
        // Required empty public constructor
    }

    public static ProvinceHomeFragment newInstance(String province) {
        ProvinceHomeFragment globalHomeFragment = new ProvinceHomeFragment();

        Bundle bundle = new Bundle();
        bundle.putString("province", province);
        globalHomeFragment.setArguments(bundle);

        return globalHomeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_province_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiProvince = new APIProvince(getContext(), this);

        tvTitleProvince = view.findViewById(R.id.tv_title_province);
        tvConfirmed = view.findViewById(R.id.tv_confirmed);
        tvRecovered = view.findViewById(R.id.tv_recovered);
        tvDeaths = view.findViewById(R.id.tv_deaths);
        llStatusGlobal = view.findViewById(R.id.ll_status);
        pbStatusGlobal = view.findViewById(R.id.pb_status);

        setData();
    }

    private void setData() {
        if (getArguments() != null) {
            String province = getArguments().getString("province");
            String provinceUnderscore = Util.replace(province);

            tvTitleProvince.setText(province);

            apiProvince.getProvinceById(Province.getProvinceId(provinceUnderscore));
        }
    }

    @Override
    public void provinceResponse(Province province) {
        pbStatusGlobal.setVisibility(View.INVISIBLE);
        llStatusGlobal.setVisibility(View.VISIBLE);

        tvConfirmed.setText(Util.formatToThousand(province.getConfirmed()));
        tvRecovered.setText(Util.formatToThousand(province.getRecovered()));
        tvDeaths.setText(Util.formatToThousand(province.getDeaths()));
    }

    @Override
    public void errorResponse(VolleyError error) {
        error.printStackTrace();
    }
}