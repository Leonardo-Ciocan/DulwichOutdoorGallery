package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BadgeNotification extends RelativeLayout {

    private TextView title;
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

    ImageView imageView;
    Animation enterAnim;
    Animation hideAnim;
    private void init() {
        enterAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                BadgeNotification.this.setAlpha(1);
                BadgeNotification.this.setScaleX(1);
                BadgeNotification.this.setScaleY(1);

                BadgeNotification.this.setPivotX(0);
                BadgeNotification.this.setPivotY(0);
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

        View inner = inflate(getContext(), R.layout.badge_notification, this);
        Button closeBtn = (Button)inner.findViewById(R.id.closeNotification);
        imageView = (ImageView)inner.findViewById(R.id.image);

        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BadgeNotification.this.setPivotX(0.9f * BadgeNotification.this.getWidth());
                BadgeNotification.this.setPivotY(0.9f * BadgeNotification.this.getWidth());
                BadgeNotification.this.startAnimation(hideAnim);
            }
        });

        title = (TextView) inner.findViewById(R.id.title);
        description = (TextView) inner.findViewById(R.id.description);

        Button shareBtn = (Button) inner.findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "I got the <" + b.getTitle() + "> badge in the Dulwich Outdoor Gallery ");
                    getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }

    Badge b;

    public void show(Badge b){
        this.b = b;
        imageView.setImageResource(b.getBadgeID());
        title.setText(b.getTitle());
        description.setText(b.getDescription());
        this.startAnimation(enterAnim);
    }
}