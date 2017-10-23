package com.example.tony.finalproject_plants.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tony.finalproject_plants.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by SHIYONG on 2017/10/15.
 */

public class FindFragment extends Fragment {
    public static final int TAKE_PHOTO=1;
    public static final int CROP_PHOTO=2;
    private Button take_photo;
    private ImageView new_image;
    private EditText new_name;
    private EditText new_info;
    public Uri imageUri;
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view=layoutInflater.inflate(R.layout.fragment_find,null);
        take_photo=(Button) view.findViewById(R.id.takephoto);
        new_image=(ImageView)view.findViewById(R.id.new_plant_image);
        new_name=(EditText) view.findViewById(R.id.new_plant_name);
        new_info=(EditText)view.findViewById(R.id.new_plant_info);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=new_name.getText().toString();
                File outputImage=new File(Environment.getExternalStorageDirectory(),name+".jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
                imageUri=Uri.fromFile(outputImage);
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    Intent intent=new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO);//启动裁剪
                }
                break;
            case CROP_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap= BitmapFactory.decodeStream(getActivity().
                                getContentResolver().openInputStream(imageUri));
                        new_image.setImageBitmap(bitmap);
                        new_image.setImageResource(R.drawable.camera);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
