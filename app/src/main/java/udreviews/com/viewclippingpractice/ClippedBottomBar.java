package udreviews.com.viewclippingpractice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ankit on 5/16/2018.
 */

public class ClippedBottomBar extends View {
    private int width;
    private int height;
    private Paint mPaint;
    private RectF mRectF;
    private Path mPath;
    private Matrix mMatrix;
    private RectF bounds;

    public ClippedBottomBar(Context context) {
        super(context);
        init(context);
    }

    public ClippedBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClippedBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPath = new Path();
        mMatrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //mPath.addCircle(width/2,0,height/2, Path.Direction.CCW);
        Log.d("CView", "height:" + height + " width:" + width);
        mPath.setLastPoint((width / 2) - 118, 0);
        mPath.lineTo(width / 2, height);
        mPath.lineTo((width / 2) + 118, 0);
        //mPath.lineTo((width/2) - 80,0);

        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        canvas.drawColor(Color.parseColor("#ff7043"));//full page background
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }
}
