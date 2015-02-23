package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RouteProgressIndicator extends View{
    public RouteProgressIndicator(Context context){
        super(context);
    }

    public RouteProgressIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Paint paint = new Paint();
    int selectedIndex = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(1.5f);

        //int half = Core.getGallery().size()/2;
        float radius = getWidth() / Core.getGallery().size();
        //float padding = (getWidth() - Core.getGallery().size() * radius * 2) / (Core.getGallery().size()+1);
        for(int x =0; x < Core.getGallery().size();x++){
            paint.setColor(x == selectedIndex ? getResources().getColor(R.color.brand) : Color.LTGRAY);
            //canvas.drawCircle(x * 10 + (x+1) * padding, getHeight() / 4f * (float)(Math.floor(x/half) + 1f) - radius , radius , paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x* radius , 0 , x*radius + radius , getHeight(),paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor( Color.WHITE);
            canvas.drawRect(x* radius , 0 , x*radius + radius , getHeight(),paint);
        }
    }

    public void setSelected(int i){
        selectedIndex = i;
        invalidate();
    }
}
