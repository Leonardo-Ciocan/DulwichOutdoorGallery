package team3m.dulwichoutdoorgallery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Art>{
    public MyAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View itemView= convertView;

        if(itemView==null){
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            itemView = inflater.inflate(R.layout.item_view, parent, false);
        }

        Art a = getItem(position);
        TextView textName= (TextView) itemView.findViewById(R.id.textView);
        textName.setText(a.getName());

        TextView textAuthor= (TextView)itemView.findViewById(R.id.textAuthor);
        textAuthor.setText(a.getAuthor());

        ImageView photo = (ImageView)itemView.findViewById(R.id.photo);

        if(a.getIsOnline() != null) {
            File imgFile = new  File(getContext().getApplicationContext().getFilesDir()+ File.separator+ a.getIsOnline());
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                photo.setImageBitmap(myBitmap);

            }
        }
        else {
            photo.setImageDrawable(a.getDrawable(getContext()));
        }

            return itemView;


    }
}
