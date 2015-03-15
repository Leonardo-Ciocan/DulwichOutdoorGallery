package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
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
    public LocationCardView(Context context , Art a){
        super(context);
        this.a = a;
        init();
    }

    public LocationCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        final View card = inflate(getContext(), R.layout.route_card, this);
        ImageView img = (ImageView) card.findViewById(R.id.img);
        TextView tv = (TextView) card.findViewById(R.id.title);
        TextView overlay = (TextView) card.findViewById(R.id.overlay);
        if (a != null) {
            if (!a.getPhoto().equals("")) {
                img.setImageDrawable(
                        getResources().getDrawable(
                                getResources().getIdentifier(a.getPhoto() + "_thumb", "drawable", getContext().getApplicationContext().getPackageName())
                        ));

                Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                        getResources().getIdentifier(a.getPhoto() + "_thumb", "drawable", getContext().getApplicationContext().getPackageName())
                );
                Palette palette = Palette.generate(icon);
                tv.setTextColor(palette.getDarkVibrantColor(Color.BLACK));
                overlay.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));
            }
            tv.setText(a.getName());

            Animation entry = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    super.applyTransformation(interpolatedTime, t);
                    card.setRotationY(interpolatedTime * 405);
                    card.setAlpha(interpolatedTime);
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            entry.setInterpolator(new OvershootInterpolator());
            entry.setDuration(1500);

            if (Core.getGallery().indexOf(a) > 0) overlay.setVisibility(VISIBLE);
            if (Core.getGallery().indexOf(a) == 1) {
                overlay.setTextSize(15);
                overlay.setText("Navigate to the next location to see this.");
            }

            //card.startAnimation(entry);
        }
    }


}
