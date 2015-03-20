package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Shown on top of all fragments when the user earns a badge
 */
public class BadgeNotification extends RelativeLayout {

    /**
     * The title of the badge
     */
    private TextView title;

    /**
     * The description of the badge
     */
    private TextView description;

    public BadgeNotification(Context context) {
        super(context);
        init();
    }

    public BadgeNotification(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BadgeNotification(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * The view where the image is shown
     */
    ImageView imageView;

    /**
     * The animation that shows the card
     */
    Animation enterAnim;

    /**
     * The animation that hides it
     */
    Animation hideAnim;


    /**
     * The badge this represents
     */
    Badge badge;

    /**
     * Initializes animations and UI
     */
    private void init() {
        enterAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                //We reset some of the properties that might've been changed when hidden
                BadgeNotification.this.setAlpha(1);
                BadgeNotification.this.setScaleX(1);
                BadgeNotification.this.setScaleY(1);
                BadgeNotification.this.setPivotX(0);
                BadgeNotification.this.setPivotY(0);
                //We modify the appropiate properties multiplied by the interpolation float
                FrameLayout.LayoutParams navBtnLayout = (FrameLayout.LayoutParams) BadgeNotification.this.getLayoutParams();
                navBtnLayout.leftMargin = (int)(interpolatedTime*10* getContext().getResources().getDisplayMetrics().density);
                navBtnLayout.rightMargin =(int)( interpolatedTime*10* getContext().getResources().getDisplayMetrics().density);
                navBtnLayout.topMargin = (int) ((-180 + (int) (185 * interpolatedTime)) * getContext().getResources().getDisplayMetrics().density);
                BadgeNotification.this.setLayoutParams(navBtnLayout);
                BadgeNotification.this.setRotation(interpolatedTime < 0.5f ? interpolatedTime * 30 : (1-interpolatedTime) * 30 );
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        enterAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        enterAnim.setDuration(1100);

        //This animation hides the card
        hideAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                BadgeNotification.this.setScaleX(1-interpolatedTime);
                BadgeNotification.this.setScaleY(1 - interpolatedTime);
                BadgeNotification.this.setRotation(interpolatedTime*80);
                BadgeNotification.this.setAlpha(1-interpolatedTime);

                if(interpolatedTime>=1f){
                    this.cancel();
                    FrameLayout.LayoutParams navBtnLayout = (FrameLayout.LayoutParams) BadgeNotification.this.getLayoutParams();
                    navBtnLayout.leftMargin = 0;
                    navBtnLayout.rightMargin = 0;
                    navBtnLayout.topMargin = (int) ((-180) * getContext().getResources().getDisplayMetrics().density);
                    BadgeNotification.this.setLayoutParams(navBtnLayout);
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        hideAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        hideAnim.setDuration(565);

        //We inflate the card into this layout
        View inner = inflate(getContext(), R.layout.badge_notification, this);
        //The button that closes the card
        Button closeBtn = (Button)inner.findViewById(R.id.closeNotification);

        imageView = (ImageView)inner.findViewById(R.id.image);
        title = (TextView) inner.findViewById(R.id.title);
        description = (TextView) inner.findViewById(R.id.description);


        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //We reset the pivot on close
                BadgeNotification.this.setPivotX(0.9f * BadgeNotification.this.getWidth());
                BadgeNotification.this.setPivotY(0.9f * BadgeNotification.this.getWidth());
                BadgeNotification.this.startAnimation(hideAnim);
            }
        });


        Button shareBtn = (Button) inner.findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    //We share which badge the user got
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "I got the <" + badge.getTitle() + "> badge in the Dulwich Outdoor Gallery ");
                    getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }


    /**
     * Shows the card
     * @param b The badge earned
     */
    public void show(Badge b){
        this.badge = b;
        imageView.setImageResource(b.getBadgeID());
        title.setText(b.getTitle());
        description.setText(b.getDescription());
        this.startAnimation(enterAnim);
    }
}