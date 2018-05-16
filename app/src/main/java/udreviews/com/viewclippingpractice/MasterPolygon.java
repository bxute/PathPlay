package udreviews.com.viewclippingpractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ankit on 5/16/2018.
 */

public class MasterPolygon extends View {
    private Paint mPaint;
    private Path mPath;
    private float centerX;
    private float centerY;
    private int width;
    private int height;
    private int radius;
    private int sides;
    private float totalLen;

    public MasterPolygon(Context context) {
        super(context);
        init();
    }

    public MasterPolygon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MasterPolygon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#2196f3"));
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setStrokeWidth(24);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        drawShape();
    }

    public void setSides(int sides) {
        this.sides = sides;
        drawShape();
    }

    public void setCornerRadius(int radius) {
        mPaint.setPathEffect(new CornerPathEffect(radius));
        drawShape();
    }

    public void setPathProgress(int progress) {
        PathMeasure pathMeasure = new PathMeasure(mPath, true);
        totalLen += pathMeasure.getLength();
        while (pathMeasure.nextContour()) {
            totalLen += pathMeasure.getLength();
        }
        float onDistance = (totalLen * progress) / 100 + 10;
        float offDistance = totalLen - onDistance;
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{onDistance, offDistance}, 0);
        mPaint.setPathEffect(dashPathEffect);
        drawShape();
    }

    private void drawShape() {
        mPath = new Path();
        double angleFactor = 2.0 * Math.PI / sides;
        centerX = width / 2;
        centerY = height / 2;
        mPath.moveTo(centerX + getXAtAngle(radius, 0), centerY);
        for (int i = 1; i <= sides; i++) {
            double angle = angleFactor * i;
            float x = centerX + getXAtAngle(radius, angle);
            float y = centerY + getYAtAngle(radius, angle);
            mPath.lineTo(x, y);
        }
        mPath.close();
        totalLen = 0;
        invalidate();
    }

    private float getXAtAngle(int radius, double angle) {
        return (float) (radius * Math.cos(angle));
    }

    private float getYAtAngle(int radius, double angle) {
        return (float) (radius * Math.sin(angle));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }
}
