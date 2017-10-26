package com.example.tony.finalproject_plants.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.tony.finalproject_plants.R;
import com.baidu.mapapi.*;
import com.example.tony.finalproject_plants.activity.PlantActivity;
import com.example.tony.finalproject_plants.database.PlantDatabase;
import com.example.tony.finalproject_plants.model.Plant;

import java.util.List;

/**
 * Created by SHIYONG on 2017/10/13.
 */

public class MapFragment extends Fragment {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationManager locationManager;
    private BDAbstractLocationListener locationListener;
    private LocationListener mlocationListener;
    private LocationClient locationClient;
    private PlantDatabase database;
    private List<Plant> plants;
    private String provider;
    private boolean isFirstLocate=true;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view=layoutInflater.inflate(R.layout.fragment_map,null);
        mapView=(MapView)view.findViewById(R.id.map_view);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database=PlantDatabase.getPlantDatabase(getActivity());
        plants=database.loadPlants();
        locationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        //获取所有有用的位置提供器
        List<String> providerList=locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            //设置提供器为GPS
            provider=LocationManager.GPS_PROVIDER;
        }else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
            //设置提供器为network
            provider=LocationManager.NETWORK_PROVIDER;
        }else{
            //没有可用的位置提供器
            Toast.makeText(getActivity(),"无法获取位置！",Toast.LENGTH_SHORT).show();
            return ;
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        /*第一行代码中的定位方法
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            Location location=locationManager.getLastKnownLocation(provider);
            if(location!=null){
                navigateTo(location);
            }
            mlocationListener=new MyLocationListener();
            locationManager.requestLocationUpdates(provider,3000,1,mlocationListener);
        }*/

        //百度地图API给出的方法
        locationListener=new BDLocationListener();
        locationClient=new LocationClient(getActivity().getApplicationContext());
        LocationClientOption locationClientOption=new LocationClientOption();
        locationClientOption.disableCache(true);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //高精度定位模式
        locationClientOption.setScanSpan(1000);
        //设置定位间隔
        locationClientOption.setOpenGps(true);//打开GPS
        locationClient.setLocOption(locationClientOption);
        locationClient.registerLocationListener(locationListener);
        locationClient.start();

        drawPlant();//画出植物标记
    }
    private void drawPlant(){
        for(Plant plant:plants){
            LatLng point = new LatLng(Double.parseDouble(plant.getLatitude()),
                    Double.parseDouble(plant.getLongitude()));
            BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.drawable.find);
            OverlayOptions options=new MarkerOptions()
                    .position(point)
                    .draggable(false)
                    .icon(bitmapDescriptor);
            Marker marker=(Marker)baiduMap.addOverlay(options);
            Bundle bundle=new Bundle();
            bundle.putSerializable("plant",plant);
            marker.setExtraInfo(bundle);//将植物的信息传递给这个marker
        }
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle=marker.getExtraInfo();
                Plant plant=(Plant) bundle.getSerializable("plant");
                Intent intent=new Intent(getActivity(), PlantActivity.class);
                intent.putExtra("plant_bundle",bundle);
                startActivity(intent);
                return true;
            }
        });
    }
    private void navigateTo(Location location){
        if(isFirstLocate){
            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        MyLocationData locationData=locationBuilder.latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locationData);

    }
    private class BDLocationListener extends BDAbstractLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location){
            navigate(location);
        }
        private void navigate(BDLocation location){
            if(isFirstLocate){
                //获取经纬度
                LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                //mBaiduMap.setMapStatus(status);//直接到中间
                baiduMap.animateMapStatus(status);//动画的方式到中间
                status=MapStatusUpdateFactory.zoomTo(18f);
                baiduMap.animateMapStatus(status);
                isFirstLocate = false;
            }
            MyLocationData locationData=new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(1000)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locationData);
        }
    }
    private class MyLocationListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            if(location!=null){
                navigateTo(location);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        if(locationClient.isStarted()){
            locationClient.stop();
        }
    }
}
