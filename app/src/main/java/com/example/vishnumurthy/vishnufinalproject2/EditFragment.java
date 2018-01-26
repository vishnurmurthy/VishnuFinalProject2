package com.example.vishnumurthy.vishnufinalproject2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditFragment extends Fragment implements View.OnClickListener{
    static final String INTERNAL_STORAGE_FILE = "db.json";
    private EditText editText;
    private Button save;
    private static final String TAG = "FRAGMENT EDIT";
    private FragmentEditInterface mCallback;
    private View mRootView;
    private Context mContext;
    private View drawingView;
    private PicturesContainer picturesContainer;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Gson gson;

    public static EditFragment newInstance() {
        EditFragment fragment = new EditFragment();
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "oncreateview");
        mRootView = inflater.inflate(R.layout.fragment_edit, container, false);
        drawingView = (View)mRootView.findViewById(R.id.simpleDrawingView1);
        save = (Button)mRootView.findViewById(R.id.save);
        editText = (EditText)mRootView.findViewById(R.id.edittext);
        save.setOnClickListener(this);
        gson = new GsonBuilder().create();

        return mRootView;
    }
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(TAG, "onasdfsadfasdfAttach");

        try {
            Log.i(TAG, "asdfasdfsadfdsafsadf");
            mCallback = (FragmentEditInterface) context;
            mContext = context;
            Log.i(TAG, "asdfasdfsadfdsafsadf");
            if (this.getUserVisibleHint()) {
                // NOTIFY ACTIVITY THAT THIS IS THE ACTIVE FRAGMENT

                mCallback.setFragmentEditActive(new PictureText("white", "white.png"));


            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentOneInterface");
        }
    }
    public String writeInternalImageFile(Bitmap b) {
        Log.i("bitmap", b.toString());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        Log.i("filename",imageFileName);

        Context context = mContext;
        try {
            FileOutputStream fos;
            fos = context.openFileOutput(imageFileName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return imageFileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UNTESTED!!
    public Bitmap readInternalImageFile(String fname)
    {
        Context context = mContext;
        try {
            FileInputStream fis;
            fis = context.openFileInput(fname);
            Bitmap b = BitmapFactory.decodeStream(fis);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void writeInternalFile(String s) {
        Context context = mContext;
        try {
            FileOutputStream fos;
            fos = context.openFileOutput(INTERNAL_STORAGE_FILE, Context.MODE_PRIVATE);
            fos.write(s.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String readInternalFile() {
        Context context = mContext;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            FileInputStream fis;
            fis = context.openFileInput(INTERNAL_STORAGE_FILE);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.save:

                picturesContainer = gson.fromJson(readInternalFile(), PicturesContainer.class);
                Log.i("in try","in try");

                if (picturesContainer == null)
                {
                    picturesContainer = new PicturesContainer(new ArrayList<PictureText>());
                }
                Log.i("asdf",picturesContainer.toString());
                PictureText p;
                if(editText.getText().toString().equals("")) {
                    String s = writeInternalImageFile(loadBitmapFromView(drawingView));
                    Log.i("s",s);
                    p = new PictureText("untitled-1", s);
                    Log.i("picturetext", p.toString());
                }
                else {
                    String s = writeInternalImageFile(loadBitmapFromView(drawingView));
                    Log.i("s",s+"");
                    p = new PictureText(editText.getText().toString(), s);
                    Log.i("picturetext", p.toString());
                }
                Log.i("picturetext",p.toString());

                picturesContainer.add(p);
                String json = gson.toJson(picturesContainer);
                writeInternalFile(json);
                Log.i(TAG, json);
                mCallback.switchFragmentRecycle();

        }
    }

    public static Bitmap loadBitmapFromView(View view) {
        /*Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;*/
//Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private Bitmap getBitmapFromAsset(String strName) {
        AssetManager assetManager = mContext.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

    public void setPicture (PictureText pt)
    {
        Log.i("TAG", "setPic");
        Bitmap bm = getBitmapFromAsset(pt.getPicture());

        Drawable d = new BitmapDrawable(mContext.getResources(), bm);

        drawingView.setBackground(d);
        Log.i("TAG", "setPicDONE");

    }
    public interface FragmentEditInterface {
        void setFragmentEditActive(PictureText pt);
        /*Bitmap readInternalImageFile(String nextPic);
        void writeInternalFile(String s);
        String writeInternalImageFile(Bitmap b);
        String readInternalFile();*/
        void switchFragmentRecycle();

    }
}
