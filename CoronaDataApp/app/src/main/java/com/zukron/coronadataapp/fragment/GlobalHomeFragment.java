package com.zukron.coronadataapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.zukron.coronadataapp.R;
import com.zukron.coronadataapp.model.Global;
import com.zukron.coronadataapp.networking.APIGlobal;
import com.zukron.coronadataapp.tools.Util;

import org.threeten.bp.format.DateTimeFormatter;

public class GlobalHomeFragment extends Fragment implements APIGlobal.OnResponse {
    private TextView tvTitleGlobal, tvConfirmed, tvRecovered, tvDeaths, tvLastUpdateGlobal;
    private LinearLayout llStatus;
    private ProgressBar pbStatus, pbLastUpdateGlobal;
    private APIGlobal apiGlobal;

    public GlobalHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiGlobal = new APIGlobal(getContext(), this);

        tvTitleGlobal = view.findViewById(R.id.tv_title_global);
        tvConfirmed = view.findViewById(R.id.tv_confirmed);
        tvRecovered = view.findViewById(R.id.tv_recovered);
        tvDeaths = view.findViewById(R.id.tv_deaths);
        tvLastUpdateGlobal = view.findViewById(R.id.tv_last_update_global);
        llStatus = view.findViewById(R.id.ll_status);
        pbStatus = view.findViewById(R.id.pb_status);
        pbLastUpdateGlobal = view.findViewById(R.id.pb_last_update_global);

        setData();
    }

    private void setData() {
        tvTitleGlobal.setText(R.string.global);

        apiGlobal.getGlobal();
    }

    @Override
    public void globalResponse(Global global) {
        pbStatus.setVisibility(View.INVISIBLE);
        pbLastUpdateGlobal.setVisibility(View.INVISIBLE);
        llStatus.setVisibility(View.VISIBLE);
        tvLastUpdateGlobal.setVisibility(View.VISIBLE);

        tvConfirmed.setText(Util.formatToThousand(global.getConfirmed()));
        tvRecovered.setText(Util.formatToThousand(global.getRecovered()));
        tvDeaths.setText(Util.formatToThousand(global.getDeaths()));

        // set format date and time
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String dateStr = global.getLastUpdate().format(date);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStr = global.getLastUpdate().format(time);
        String lastUpdate = dateStr + " - " + timeStr;

        tvLastUpdateGlobal.setText(lastUpdate);
    }

    @Override
    public void errorResponse(VolleyError error) {
        error.printStackTrace();
    }
}