package com.example.vishnumurthy.vishnufinalproject2;

import java.util.ArrayList;

/**
 * Created by 2019vmurthy on 1/11/2018.
 */

public class PicturesContainer
{
    public ArrayList<PictureText> list;
    public PicturesContainer(ArrayList<PictureText> list)
    {
        this.list = list;
    }
    public ArrayList<PictureText> getList()
    {
        return list;
    }
    public String toString()
    {
        String s = "";
        for (int i = 0; i < list.size(); i++)
            s=s + list.get(i)+" ";
        return s;
    }
    public void add(PictureText p)
    {
        list.add(p);
    }
}
