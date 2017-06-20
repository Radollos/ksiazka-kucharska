package com.example.klaudia.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Slawcio on 2017-05-08.
 */

public class ImageAdapter extends BaseAdapter{
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public static int getReferences(int position) {    return mThumbIds[position]; }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = size.x/2;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

        //    imageView.setLayoutParams(new GridView.LayoutParams(x, x));
            // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       //     imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext)
                .load(mThumbIds[position])
                .resize(x, x)

                .centerCrop()
                .into(imageView);

    //    imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private static Integer[] mThumbIds = {
            R.drawable.favorites,
            R.drawable.breakfast,
            R.drawable.soups,
            R.drawable.meat,
            R.drawable.fish,
            R.drawable.appetizer,
            R.drawable.salads,
            R.drawable.desserts,
            R.drawable.drinks,
            R.drawable.appetizer,
            R.drawable.flour_dishes,
            R.drawable.glutenfree,
            R.drawable.cakes,
            R.drawable.mexican,
            R.drawable.thai,
            R.drawable.japanese,
            R.drawable.italian,
            R.drawable.vegan,
            R.drawable.vege
    };

}
