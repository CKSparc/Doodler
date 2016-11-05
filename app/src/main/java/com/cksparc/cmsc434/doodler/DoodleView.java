package com.cksparc.cmsc434.doodler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johndoe on 11/3/16.
 */

public class DoodleView extends View {

    public static MyPaint _paintDoodle;
    public static Path _path;

    public DoodleView(Context context) {
        super(context);
        init(null,0);
    }

    public DoodleView(Context context, AttributeSet attrset) {
        super(context,attrset);
        init(attrset,0);
    }

    public DoodleView(Context context, AttributeSet attrset, int defStyle) {
        super(context,attrset, defStyle);
        init(attrset,defStyle);

    }

    private void init(AttributeSet attrset, int defStyle){

        _paintDoodle = new MyPaint(0,0,0);
        _path = new Path();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawLine(0,0,getWidth(),getHeight(),_paintDoodle);

          for(Stroke stroke : MainActivity.strokes) {
              canvas.drawPath(stroke.path, stroke.paint);
           }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                _path.moveTo(touchX,touchY);
                MainActivity.strokes.add(new Stroke(_paintDoodle,_path));
                MainActivity.undoedStrokes.clear();
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.strokes.get(MainActivity.strokes.size() - 1).path.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                _path = new Path();
                break;
        }

        invalidate();
        return true;
    }



}

class Stroke {

    MyPaint paint;
    Path path;

    public Stroke(MyPaint paint, Path path) {
        this.paint = paint;
        this.path = path;
    }
}

class MyPaint extends Paint {

    public MyPaint(int r, int g, int b) {
        this.setColor(Color.rgb(r,g,b));
        this.setAntiAlias(true);
        this.setStyle(Style.STROKE);
        this.setStrokeWidth(10);
    }

    public MyPaint(int a, int r, int g, int b) {
        this.setColor(Color.argb(a,r,g,b));
        this.setAntiAlias(true);
        this.setStyle(Style.STROKE);
        this.setStrokeWidth(10);
    }

    public void setBrushSize(int size) {
        this.setStrokeWidth(size);
    }

}
