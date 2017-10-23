package com.example.tony.finalproject_plants.fragment;

/**
 * Created by SHIYONG on 2017/10/22.
 */

public class FragmentFactory {
    private static MapFragment mapFragment=new MapFragment();
    private static FindFragment findFragment=new FindFragment();
    private static SearchFragment searchFragment=new SearchFragment();
    private static ListFragment listFragment=new ListFragment();
    public static MapFragment getMapFragment(){
        return mapFragment;
    }
    public static FindFragment getFindFragment(){
        return findFragment;
    }
    public static SearchFragment getSearchFragment(){
        return searchFragment;
    }
    public static ListFragment getListFragment(){
        return listFragment;
    }
}
