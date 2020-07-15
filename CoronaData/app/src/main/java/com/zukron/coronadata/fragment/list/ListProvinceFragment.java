package com.zukron.coronadata.fragment.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zukron.coronadata.R;
import com.zukron.coronadata.activity.MainActivity;
import com.zukron.coronadata.fragment.ProvinceHomeFragment;
import com.zukron.coronadata.model.Province;

import java.util.ArrayList;

public class ListProvinceFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lvList;

    public ListProvinceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvList = view.findViewById(R.id.lv_list);

        setListView();
    }

    private void setListView() {
        ArrayList<String> provinces = Province.allProvince();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provinces);

        lvList.setAdapter(arrayAdapter);
        lvList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String province = adapterView.getItemAtPosition(i).toString();

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("loadFragment", ProvinceHomeFragment.class.getSimpleName());
        intent.putExtra("province", province);

        startActivity(intent);
    }
}