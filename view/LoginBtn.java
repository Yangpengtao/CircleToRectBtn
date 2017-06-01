package cn.com.oomall.kktown.activity.seller.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.com.oomall.kktown.R;
import cn.com.oomall.kktown.Utils.LogPrinter;
import cn.com.oomall.kktown.Utils.ScreenUtils;

/**
 * 登录button
 * Yang  pengtao
 * Created by root on 16-8-15.
 */
public class LoginBtn extends RelativeLayout {
    private final String TAG = "LoginBtn--->>>>>";
    private Paint mPaint;
    private Paint textPaint;
    public float mWidth = 85;
    public float mHight = 85;
    private float mTopBottom = 85;
    private float mLeftRight = 85;
    private float maxWidth, maxHeight;
    private int i = 0;
    private Context mContext;
    private boolean isFirst = true;
    LinearLayout.LayoutParams params;
    //    private int drawId;
    private String text;
    private TypedArray types;
    private float textHeight;
    private float density = 1;
	
	private RectF rF;
	
	private  DisplayMetrics metrics;


    public LoginBtn(Context context) {
        super(context);
        mContext = context;
    }

    public LoginBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context, attrs);
    }

    public LoginBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        density = ScreenUtils.getScreen(context).density;
        mHight = mHight * density;
        mWidth = mWidth * density;
        mTopBottom = mTopBottom * density;
        mLeftRight = mLeftRight * density;
        types = context.obtainStyledAttributes(attrs, R.styleable.LoginBtn);
        mPaint = new Paint();
        textPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);// 设置画笔填充
        mPaint.setColor(Color.WHITE);// 设置画笔颜色为透明
        //绘制文字开始
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(16 * density);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //获取文字度量信息
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        textHeight = fm.descent - fm.ascent;

//        drawId = types.getResourceId(R.styleable.LoginBtn_backgroundRes, R.mipmap.login_business);
        text = types.getString(R.styleable.LoginBtn_loginText);
		
		rF = new RectF(0, 0, mWidth, mHight);// 设置个新的长方形
		metrics = ScreenUtils.getScreen(mContext);
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    public void setText(String str) {
        this.text = str;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!isFirst) {

            canvas.drawRoundRect(rF, mTopBottom, mLeftRight, mPaint);//第二个参数是x半，第三个参数是y半

            //绘制文字的矩形框范围
            canvas.drawText(text, mWidth / 2, mHight / 2 + textHeight / 2 - 3 * density, textPaint);
            canvas.restore();
            //绘制文字结束

            //该view左右margin50
            maxWidth = ScreenUtils.getScreenWidth(mContext) - ScreenUtils.convertDpToPixel(mContext, 100);
            if (maxWidth >= mWidth) {
                mWidth += 15;
            }
            maxHeight = ScreenUtils.convertDpToPixel(mContext, 50);
            if (mHight > maxHeight) {
                mHight -= 10;
            }
            if (mTopBottom >= 50)
                mTopBottom -= 5;
            if (mLeftRight >= 50)
                mLeftRight -= 5;


			//在onDraw里面尽量少出现new对象。可以通过params.width=mWidth;params.height=mHight替换
			//提高自定义view性能
            params = new LinearLayout.LayoutParams((int) mWidth, (int) mHight);
            int margin = ScreenUtils.convertDpToPixel(mContext, 50);
            params.setMargins(margin, 0, margin, 0);
            setAlpha(0.9f);
            if (maxWidth >= mWidth) {
                setLayoutParams(params);
            } else {
                mPaint.setAlpha(0);
                canvas.drawRoundRect(rF, mTopBottom, mLeftRight, mPaint);
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                setBackgroundResource(R.drawable.login_boder);
            }


        }
        LogPrinter.e(TAG, maxWidth+"----------"+maxHeight);
        /*else {
            Bitmap btm = BitmapFactory.decodeResource(getResources(), R.drawable.login_buyer);
            canvas.drawBitmap(btm, 0, 0, mPaint);
        }*/
    }

    public void setIsfir(Boolean isFir) {
        isFirst = isFir;
    }

    public boolean getIsfir() {
        return isFirst;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void startAnim() {


    }

}
