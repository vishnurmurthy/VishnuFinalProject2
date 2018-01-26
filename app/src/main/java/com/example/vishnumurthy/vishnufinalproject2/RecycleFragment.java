package com.example.vishnumurthy.vishnufinalproject2;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by 2019vmurthy on 1/11/2018.
 */

public class RecycleFragment extends Fragment {

    private static final String TAG = "FRAGMENT MAIN";
    private static final String INTERNAL_STORAGE_FILE = "db.json";
    private PicturesContainer picturesContainer;
    private FragmentRecycleInterface mCallback;
    private View mRootView;
    private Context mContext;

    private PictureAdapter pAdapter;
    private List<PictureText> picturelist = new ArrayList<>();
    private RecyclerView recyclerview;
    public RecycleFragment()
    {
    }
    public static RecycleFragment newInstance() {
        RecycleFragment fragment = new RecycleFragment();
        return fragment;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.i(TAG, "setUserVisibleHint");
        if (isVisibleToUser) {
            Log.i(TAG, "fragment visibility: true");
            try {
                mCallback.setFragmentRecycleActive();
                Log.i(TAG, "fragment one active: yes");
            } catch (Exception e) {
                // errors callback not created yet
            }
        }
        else {
            Log.i(TAG, "fragment visibility: false");
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preparePictureData();
        pAdapter = new PictureAdapter(picturelist);
        recyclerview.setAdapter(pAdapter);
        try {
            mCallback = (FragmentRecycleInterface) context;
            mContext = context;
            if (this.getUserVisibleHint()) {
                // NOTIFY ACTIVITY THAT THIS IS THE ACTIVE FRAGMENT

                mCallback.setFragmentRecycleActive();


            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentOneInterface");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "oncreateview");
        mRootView = inflater.inflate(R.layout.recycleview, container, false);
        recyclerview = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        pAdapter = new PictureAdapter(picturelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(pAdapter);
        recyclerview.addOnItemTouchListener(new RecycleTouchListener(mContext, recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureText pt = picturelist.get(position);
                //Toast.makeText(mContext, movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                mCallback.switchToEditFragment(pt);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        FloatingActionButton fab1 = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mCallback.switchToEditFragment(new PictureText("white", "white.png"));

            }
        });


        //pAdapter = new PictureAdapter(picturelist);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(pAdapter);
        preparePictureData();
        return mRootView;
    }
    public String getStringAsset(String filename) {
        AssetManager assetManager = mContext.getAssets();
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try {
            InputStream ins = assetManager.open(filename);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = ins.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void preparePictureData() {
        picturelist= new ArrayList<PictureText>();
        Gson gson = new GsonBuilder().create();
        picturesContainer = gson.fromJson(readInternalFile(), PicturesContainer.class);

        Log.i("in try",picturesContainer.toString());

        if (picturesContainer == null)
        {
            picturesContainer = new PicturesContainer(new ArrayList<PictureText>());
        }

        for (PictureText m:picturesContainer.getList())
        {
            picturelist.add(m);
        }
        String s = ""; for (PictureText p : picturelist){s=s+p.toString()+" , ";}
        Log.i("asdf", picturesContainer.toString());

        pAdapter.notifyDataSetChanged();



        /*Log.i("TAG", "prepare Picture Data");
        String s = getStringAsset("db.json");
        if (s==null)
        {
            Log.i("TAG", "NULL S NULL S");
        }
        Log.i("TAG", s);

        Gson gson = new GsonBuilder().create();
        picturesContainer = gson.fromJson(getStringAsset("db.json"), PicturesContainer.class);
        Log.i(TAG, picturesContainer.toString());

        for (PictureText m:picturesContainer.getList())
        {
            picturelist.add(m);
        }
        pAdapter.notifyDataSetChanged();*/
    }
    // --------------------- overridden methods --------------------- //


    public String readInternalFile() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            FileInputStream fis;
            fis = mContext.openFileInput(INTERNAL_STORAGE_FILE);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("tag", "file not found");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("tag", "io exception");
        }
        return null;
    }

    public interface FragmentRecycleInterface {
        void setFragmentRecycleActive();
        void switchToEditFragment(PictureText pt);
        /*Bitmap readInternalImageFile(String nextPic);
        void writeInternalFile(String s);
        String writeInternalImageFile(Bitmap b);
        String readInternalFile();*/

    }
}
