package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationCardView extends RelativeLayout {
    Art a;
    private TextView overlay;
    private ImageView img;
    private View card;

    public LocationCardView(Context context , Art a){
        super(context);
        this.a = a;
        init();
    }

    public LocationCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    static float x = 0;
    void init() {
        card = inflate(getContext(), R.layout.route_card, this);
        img = (ImageView) card.findViewById(R.id.img);
        overlay = (TextView) card.findViewById(R.id.overlay);
        //this.setAlpha(1/(x+1));
        x+=1;
    }

    public void setArt(Art a){
        if (!a.getPhoto().equals("")) {
            img.setImageDrawable(
                    getResources().getDrawable(
                            getResources().getIdentifier(a.getPhoto() + "_thumb", "drawable", getContext().getApplicationContext().getPackageName())
                    ));

            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    getResources().getIdentifier(a.getPhoto() + "_thumb", "drawable", getContext().getApplicationContext().getPackageName())
            );
            Palette palette = Palette.generate(icon);
//            tv.setTextColor(palette.getDarkVibrantColor(Color.BLACK));
            overlay.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));
        }
    }

    public void hideOverlay(){
        Animation entry = new Animation() {
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
        entry.setDuration(3000);

        overlay.startAnimation(entry);
    }

    public void showOverlay(){
        overlay.setAlpha(1);
        overlay.setVisibility(VISIBLE);
    }


}
