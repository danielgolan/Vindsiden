package com.vindsiden.windwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Daniel on 15.02.14.
 */
public class WindRose extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));


    }

    public class MyView extends View {

        public MyView(Context context) {
            super(context);

        }

        @Override

        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            float width = (float) getWidth();
            float height = (float) getHeight();

            float radius;

            if (width > height) {
                radius = height / 4;

            } else {
                radius = width / 4;
            }

            Path path = new Path();
            path.addCircle(width, height, radius, Path.Direction.CCW);

            // / 2

            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(5);

            paint.setStyle(Paint.Style.FILL);
            float center_x, center_y;
            center_x = width / 2;
            center_y = height / 4;

            final RectF oval = new RectF();

            //Formulas :
            //SD = Start Degree
            //ED = End Degree

            //If cakepiece passes 0 (East)
            //SD, 360-(SD+ED)

            //Else :
            //SD, (ED-SD)

            oval.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);
            canvas.drawArc(oval, 315, 360 - 315 + 45, true, paint);


        }
    }

}
