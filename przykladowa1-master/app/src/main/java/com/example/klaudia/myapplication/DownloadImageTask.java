package com.example.klaudia.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Klaudia on 2017-05-11.
 */

//klasa odpowiedzialna za pobieranie obrazka dania
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
{
    ImageView bitmapImage;
    Context context;

    public DownloadImageTask(Context context)
    {
        this.context = context;
        bitmapImage = new ImageView(context);
    }


    protected Bitmap doInBackground(String... urls)
    {
        String imageUrl = urls[0];
        Bitmap image = null;
        try
        {

            InputStream in = (InputStream) new URL(imageUrl).getContent();
            image = BitmapFactory.decodeStream(in);
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return image;
    }

    protected void onPostExecute(Bitmap result)
    {
        bitmapImage.setImageBitmap(result);
    }

}
