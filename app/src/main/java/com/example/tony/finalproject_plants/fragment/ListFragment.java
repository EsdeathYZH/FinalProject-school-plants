package com.example.tony.finalproject_plants.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.tony.finalproject_plants.R;
import com.example.tony.finalproject_plants.activity.PlantActivity;
import com.example.tony.finalproject_plants.model.Plant;
import com.example.tony.finalproject_plants.database.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SHIYONG on 2017/10/13.
 */

public class ListFragment extends Fragment {
    private ListView mListView;
    private List<Plant> plants;
    private PlantDatabase database;
    private List<Map<String,Object>>items;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database=PlantDatabase.getPlantDatabase(getActivity());
        items=new ArrayList<Map<String, Object>>();
        plants=database.loadPlants();
        for(Plant plant:plants){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("plant_name",plant.getPlant_name());
            map.put("plant_id",plant.getPlant_id());
            map.put("image_id",plant.getImage_id());
            map.put("short_info",plant.getShort_info());
            items.add(map);
        }
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view=layoutInflater.inflate(R.layout.fragment_total,null);
        mListView=(ListView)view.findViewById(R.id.mListView);
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),items,R.layout.plant_item,
                new String[]{"image_id","plant_name","short_info"},
                new int[]{R.id.plantImage,R.id.plantName,R.id.plantInfo});
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?>parent, View view, int position, long arg3){
            Intent intent=new Intent(getActivity(), PlantActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("plant",plants.get(position));
            intent.putExtra("plant_bundle",bundle);
            startActivity(intent);
        }
    }
}
