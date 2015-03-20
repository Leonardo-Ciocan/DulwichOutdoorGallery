package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Shows a number of dots with all the art , highlights the visited arts
 */
public class RouteProgressIndicator extends View{
    public RouteProgressIndicator(Context context){
        super(context);
    }

    public RouteProgressIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * The paint used to draw
     */
    Paint paint = new Paint();
    /**
     * We store how many we visited
     */
    int visitedCount = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //We set some parameters
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3.5f);

        //The radius is dynamically calculated
        float radius = getWidth() / Core.getGallery().size();
        //We draw each dot
        for(int x = 0; x < Core.getGallery().size();x++){
            //We draw the inner dot
            paint.setStrokeWidth(3.5f);
            paint.setColor(x <= visitedCount ? getResources().getColor(R.color.brand) : Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x * radius + radius / 2f, radius, radius / 2f, paint);

            //And the border to separate the dots
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(getResources().getColor(R.color.brandDark));
            paint.setStrokeWidth(5);
            canvas.drawCircle(x*radius+radius/2f , radius ,radius/2f,paint);
        }
    }

    /**
     * Set how many are selected
     * @param visited The progress
     */
    public void setSelected(int visited){
        visitedCount = visited;
        invalidate();
    }
}
