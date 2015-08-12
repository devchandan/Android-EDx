package hk.ust.cse.comp107x.basicgraphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by muppala on 23/5/15.
 */
public class DrawView extends View {

    private int width, height;
    Paint redpaint, bluepaint;
    Bitmap android_guy;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);


        redpaint = new Paint();
        redpaint.setColor(Color.RED);
        bluepaint = new Paint();
        bluepaint.setColor(Color.BLUE);


        android_guy = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher),50,50, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawLine(width / 2, height-100, width / 2, height, bluepaint);
        canvas.drawRect(width / 2 - 30, height - 10, width / 2 + 30, height, bluepaint);
        canvas.drawCircle(width / 2, height - 40, 10, redpaint);
        canvas.drawRect(width / 2 - 10, height - 40, width / 2 + 10, height, bluepaint);
        canvas.drawBitmap(android_guy, width/2, 0, null);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
    }
}
