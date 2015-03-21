package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/**
 * Used in the routing to show the art
 */
public class LocationCardView extends RelativeLayout {
    /**
     * The art it shows
     */
    Art a;

    /**
     * The overlay shown to hide the picture
     */
    private TextView overlay;

    /**
     * The actual image
     */
    private ImageView img;
    private Animation entry;

    public LocationCardView(Context context , Art a){
        super(context);
        init();
    }

    public LocationCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes the view
     */
    void init() {
        //When clicked we go to the corresponding art in the InfoActivity
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), InfoActivity.class);
                if(a!=null){
                    //We pass the index so the activity knows which art to show
                    int index = Core.getGallery().indexOf(a);
                    i.putExtra("index", index);
                    getContext().startActivity(i);
                }
            }
        });

        //Inflating the card from the layout
        View card = inflate(getContext(), R.layout.route_card, this);
        img = (ImageView) card.findViewById(R.id.img);
        overlay = (TextView) card.findViewById(R.id.overlay);

        //The animation to hide the overlay
        entry = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                overlay.setAlpha(1 - interpolatedTime);
                overlay.setScaleX(interpolatedTime + 1);
                overlay.setScaleY(interpolatedTime + 1);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        entry.setInterpolator(new AccelerateDecelerateInterpolator());
        entry.setDuration(950);
    }


    /**
     * Sets the art and colors the overlay dynamically
     * @param a
     */
    public void setArt(Art a){
        this.a = a;
        //We check if the photo is local or not (if not then it's from Dropbox)
        if (!a.getPhoto().equals("")) {
            //We set the image view source
            img.setImageDrawable(
                    getResources().getDrawable(
                            getResources().getIdentifier(a.getPhoto() + "_thumb", "drawable",
                                    getContext().getApplicationContext().getPackageName())
                    ));

            //We get a bitmap for the color extraction
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    getResources().getIdentifier(a.getPhoto() + "_thumb", "drawable",
                            getContext().getApplicationContext().getPackageName())
            );

            //We generate the dominant color and set it as the background of the overlay
            Palette palette = Palette.generate(icon);
            overlay.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));
        }
        else{
            //Gets the file downloaded from Dropbox
            File imgFile = new  File(getContext().getApplicationContext().getFilesDir()
                                     + File.separator+ a.getOnlinePicture());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);
                Palette palette = Palette.generate(myBitmap);
                overlay.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));
            }
        }
    }

    /**
     * Hides the overlay using an animation
     */
    public void hideOverlay(){
        overlay.startAnimation(entry);
    }
}
