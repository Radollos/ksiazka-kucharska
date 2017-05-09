package com.example.klaudia.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Slawcio on 2017-05-08.
 */

public class ImageAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

  //  public int getCount() {
      //  return mThumbIds.length;
    //}

    public Object getItem(int position) {
        return null;
    }

 //   public long getItemId(int position) {
   //     return mThumbIds[position];
   // }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
      //  if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
    //        imageView.setLayoutParams(new GridView.LayoutParams(540, 500));
      //     imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       //     imageView.setPadding(0, 0, 0, 0);
   //     } else {
     //       imageView = (ImageView) convertView;
     //   }
//
     //   imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.breakfast, R.drawable.cake,
//            R.drawable.dessert, R.drawable.drinks,
//            R.drawable.fish, R.drawable.flour_dishes,
//            R.drawable.meat, R.drawable.national,
//            R.drawable.salad, R.drawable.soup,
//    };
}
