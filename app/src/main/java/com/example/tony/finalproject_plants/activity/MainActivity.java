package com.example.tony.finalproject_plants.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.tony.finalproject_plants.R;
import com.example.tony.finalproject_plants.fragment.*;

/**
 * Created by SHIYONG on 2017/10/13.
 * This is the main activity.
 */

public class MainActivity extends Activity {
    private TabHost mTabHost;//选项卡
    private String currentTag;//当前tab
    private FragmentManager fragmentManager;
    private String[] titles=new String[]{"汇总","附近","发现","搜索"};//各选项名称
    private Fragment[] fragments=new Fragment[]{new ListFragment(),new MapFragment(),new FindFragment(),new SearchFragment()};
    private String[] tags=new String[]{"Total","Map","Find","Search"};//各选项标记
    private int[] icons=new int[]{R.drawable.total,R.drawable.map,R.drawable.find,R.drawable.search};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initTab();
    }
    @Override
    public void onResume(){
        super.onResume();

    }
    private void initTab(){
        mTabHost=(TabHost)findViewById(R.id.mTabHost);
        mTabHost.setup();
        for(int i=0;i<titles.length;i++){
            TabHost.TabSpec tabSpec=mTabHost.newTabSpec(tags[i]);
            View view=getLayoutInflater().inflate(R.layout.tab,null);
            TextView tabTitle=(TextView)view.findViewById(R.id.tabTitle);
            ImageView tabicon=(ImageView)view.findViewById(R.id.tabIcon);
            tabTitle.setText(titles[i]);
            tabicon.setImageResource(icons[i]);
            tabSpec.setIndicator(view);
            tabSpec.setContent(R.id.realcontent);
            mTabHost.addTab(tabSpec);
        }
        fragmentManager=getFragmentManager();
        addFragment();
        currentTag="Total";
        mTabHost.setOnTabChangedListener(new MyTabChangedListener());
        mTabHost.setCurrentTabByTag("Total");
        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("Total")).commit();
    }
    private void addFragment(){
        fragmentManager.beginTransaction().add(R.id.realcontent,FragmentFactory.getMapFragment(),"Map").commit();
        fragmentManager.beginTransaction().add(R.id.realcontent,FragmentFactory.getFindFragment(),"Find").commit();
        fragmentManager.beginTransaction().add(R.id.realcontent,FragmentFactory.getSearchFragment(),"Search").commit();
        fragmentManager.beginTransaction().add(R.id.realcontent,FragmentFactory.getListFragment(),"Total").commit();
        fragmentManager.executePendingTransactions();
        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Map")).commit();
        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Find")).commit();
        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("Search")).commit();

    }
    class MyTabChangedListener implements TabHost.OnTabChangeListener{
        public void onTabChanged(String tag){
            if(tag!=currentTag){
                fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(currentTag)).commit();
                currentTag = tag;
                fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(currentTag)).commit();
                fragmentManager.executePendingTransactions();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        getFragmentManager().findFragmentByTag("Find").onActivityResult(requestCode,resultCode,data);
    }
}
