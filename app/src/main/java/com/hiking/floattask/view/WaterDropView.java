package com.hiking.floattask.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hiking.floattask.R;
import com.hiking.floattask.manager.GestureManager;
import com.hiking.floattask.utils.DensityUtil;
import com.hiking.floattask.utils.PointCalUtil;
import com.hiking.floattask.utils.SPUtil;
import com.hiking.floattask.utils.ScreenUtils;
import com.hiking.floattask.utils.SystemUtil;

public class WaterDropView extends View {
    private static final long DELAY_MSG_LONG_PRESS = 1500L;
    private static final long DOUBLE_CLICK_DURATION = 300L;

    private static final long DL = 2L;
    private static int INNER_CIRCLE_R;
    public static int OUTER_CIRCLE_R;
    private static final float INNER_CIRCLE_R_ZOONIN = 1.15F;

    private static int OUT_STROKE_WIDTH = 3;
    public static int START_POSITION ;
    private static final String TAG = WaterDropView.class.getSimpleName();
    private static long mLastClickDownTime = 0L;
    private static long mCurrentClickDownTime = 0L;
    private int ANGLE_135 = 135;
    private int ANGLE_45 = 45;
    private int OFFSET_CENTER_Y = 3;
    private int OFFSET_OVAL_CENTER_X = 2;
    float absPostionX;
    float absPostionY;
    float absStartX;
    float absStartY;
    private boolean isFling;
    private int mAngle;
    private int mCenterCircleX;
    private int mCenterCircleY;
    private boolean mDoubleClickFlag;
    private Runnable mDoubleClickRunnable;
    private final Paint mFillInPaint = new Paint();
    private Path mFillInnerPath = new Path();
    private final Paint mFillOutPaint = new Paint();
    private Path mFillOuterPath = new Path();



    public GestureOperationListener mGestureOperationListener;
    private int mInnerCircleR;
    private boolean mIsDoubleClick;
    private boolean mIsLongPress;
    private boolean mIsSingleClick;
    private boolean mIsMove;
    private Runnable mLongPressRunnable;
    private float mMoveX;
    private float mMoveY;

    private Runnable mSingleClickRunnable;

    private ViewGroup.LayoutParams params;
    private boolean pointInOuterCircle = true;
    private int mAMoveX;
    private int mAMoveY;

    public WaterDropView(Context context) {
        this(context,null);
    }

    public WaterDropView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterDropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //setBackgroundColor(getResources().getColor(R.color.peachpuff));
        initResource();
        initPaint();
        initPath();
    }

    private void initPath() {
        mFillOuterPath.addCircle(mCenterCircleX, mCenterCircleY, OUTER_CIRCLE_R, Path.Direction.CCW);
        mFillInnerPath.addCircle(mCenterCircleX, mCenterCircleY, INNER_CIRCLE_R, Path.Direction.CCW);

    }

    private void initPaint() {
        mFillOutPaint.setAntiAlias(true);
        mFillOutPaint.setStyle(Paint.Style.FILL);
        mFillOutPaint.setColor(getResources().getColor(R.color.beige));
        mFillInPaint.setAntiAlias(true);
        mFillInPaint.setStyle(Paint.Style.FILL);
        mFillInPaint.setColor(getResources().getColor(R.color.lime_alpha));

    }

    private void initResource() {
        INNER_CIRCLE_R = DensityUtil.dip2px(getContext(),12);
        OUTER_CIRCLE_R = (int) (1.4*INNER_CIRCLE_R);
        START_POSITION = (int) (INNER_CIRCLE_R_ZOONIN*INNER_CIRCLE_R*2);
        mInnerCircleR = INNER_CIRCLE_R;
        mCenterCircleX = START_POSITION ;
        mCenterCircleY = mCenterCircleX;
        mMoveX = mCenterCircleX;
        mMoveY = mCenterCircleY;
    }

    public int getViewWidth(){
        return 2*START_POSITION;
    }

    private void resetView(){
        mInnerCircleR = INNER_CIRCLE_R;
        mCenterCircleX = START_POSITION ;
        mCenterCircleY = mCenterCircleX;
        mFillOutPaint.setColor(getResources().getColor(R.color.beige));
        mFillInnerPath.reset();
        mFillInnerPath.addCircle(mCenterCircleX, mCenterCircleY, mInnerCircleR, Path.Direction.CCW);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        params = getLayoutParams();
        params.width = (2 * START_POSITION);
        Log.d(TAG,"START_POSITION = "+START_POSITION );
        params.height = params.width;
        setLayoutParams(params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mFillOuterPath,mFillOutPaint);
        canvas.drawPath(mFillInnerPath,mFillInPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                mAMoveX = (int) event.getRawX()-START_POSITION;
                mAMoveY = (int) event.getRawY()-START_POSITION;

                calTouchPosition(event);


                mFillOutPaint.setColor(getResources().getColor(R.color.gainsboro));
                mInnerCircleR = ((int)(INNER_CIRCLE_R_ZOONIN * INNER_CIRCLE_R));
                mFillInnerPath.reset();
                mFillInnerPath.addCircle(START_POSITION, START_POSITION, mInnerCircleR, Path.Direction.CCW);

                //============================================================================================
                removeAllRunnable();
                postLongPressDelayed();
                mCurrentClickDownTime = System.currentTimeMillis();


                break;

            case MotionEvent.ACTION_MOVE:

                if (mIsLongPress){
                    int l=PointCalUtil.getInstance(new Point((int)event.getRawX()-START_POSITION,(int)event.getRawY()-START_POSITION- ScreenUtils.getStatusHeight(getContext())),new Point(mAMoveX,mAMoveY));
                    mIsMove = (l>DL);
                }
                mAMoveX = (int) event.getRawX()-START_POSITION;
                mAMoveY = (int) event.getRawY()- START_POSITION-ScreenUtils.getStatusHeight(getContext());



                if (!mIsLongPress){
                    calTouchPosition(event);
                    mFillInnerPath.reset();
                    mFillInnerPath.addCircle(mCenterCircleX, mCenterCircleY, mInnerCircleR, Path.Direction.CCW);
                }else{

                    if (mIsMove){

                        mGestureOperationListener.onGestureOperate(Gesture.MOVE,new Point(mAMoveX,mAMoveY));
                    }else{
                        resetView();

                    }
                }



                break;

            case MotionEvent.ACTION_UP:

                if (System.currentTimeMillis() - mCurrentClickDownTime < DELAY_MSG_LONG_PRESS){
                    mIsLongPress = false;
                    removeLongPressRunnable();
                }else{
                    mIsLongPress = true;
                }

                if (!mIsLongPress){
                    if (isFling){
                        postFling();
                    }else{
                        if (System.currentTimeMillis() - mLastClickDownTime < DOUBLE_CLICK_DURATION){
                            postDoubleClickDelayed();
                        }else{
                            postSingleClickDelayed();
                        }
                    }
                }

                mLastClickDownTime = mCurrentClickDownTime;


                resetView();

                if(mIsLongPress){
                    SPUtil.put(getContext(), GestureManager.MOVE_X,mAMoveX);
                    SPUtil.put(getContext(), GestureManager.MOVE_Y,mAMoveY);
                }
                mIsLongPress = false;
                mIsMove = false;

                break;

        }

        invalidate();
        return true;
        //return super.onTouchEvent(event);
    }

    private void postFling(){
        if (!(mGestureOperationListener == null)){
            Gesture gesture = getFlingDirection(PointCalUtil.getAngle(new Point(mCenterCircleX,mCenterCircleY),new Point(START_POSITION,START_POSITION)));
            mGestureOperationListener.onGestureOperate(gesture);
        }
    }

    private void postLongPressDelayed() {
        Log.i(TAG, "postLongPressDelayed");
        if (mLongPressRunnable == null) {
            mLongPressRunnable = new Runnable()
            {
                public void run()
                {
                    mIsLongPress = true;
                    Log.d(TAG, "onLongPress");
                    if (mGestureOperationListener == null) {
                        return;
                    }


                    mGestureOperationListener.onGestureOperate(Gesture.LONG_PRESS);
                }
            };
        }
        mIsLongPress = false;
        postDelayed(mLongPressRunnable, DELAY_MSG_LONG_PRESS);
    }

    private void postDoubleClickDelayed() {
        Log.i(TAG, "postDoubleClickDelayed");
        if (mDoubleClickRunnable == null) {
            mDoubleClickRunnable = new Runnable()
            {
                public void run()
                {
                    if (mGestureOperationListener == null) {
                        return;
                    }
                    Log.d(TAG, "DoubleClick");

                    mGestureOperationListener.onGestureOperate(Gesture.DOUBLE_CLICK);
                }
            };
        }
        mDoubleClickFlag = false;
        postDelayed(mDoubleClickRunnable, DOUBLE_CLICK_DURATION);
    }


    private void postSingleClickDelayed() {
        Log.i(TAG, "postSinglePressDelayed");
        if (mSingleClickRunnable == null) {
            mSingleClickRunnable = new Runnable()
            {
                public void run()
                {
                    Log.d(TAG, "SingleClick");
                    if (mGestureOperationListener == null) {
                        return;
                    }


                    mGestureOperationListener.onGestureOperate(Gesture.SINGLE_CLICK);
                }
            };
        }
        mIsSingleClick = false;
        postDelayed(mSingleClickRunnable, DOUBLE_CLICK_DURATION);
    }

    private void removeAllRunnable()
    {
        removeLongPressRunnable();
        removeSingleClickRunnable();
        removeDoubleClickRunnable();
    }

    private void removeLongPressRunnable()
    {
        Log.d(TAG,"removeLongPressRunnable");
        mIsLongPress = false;
        removeCallbacks(mLongPressRunnable);
    }

    private void removeDoubleClickRunnable()
    {
        Log.d(TAG,"removeDoubleClickRunnable");
        mIsDoubleClick = false;
        removeCallbacks(mDoubleClickRunnable);
    }



    private void removeSingleClickRunnable()
    {
        Log.d(TAG,"removeSingleClickRunnable");
        mIsSingleClick = false;
        removeCallbacks(mSingleClickRunnable);
    }

    private Gesture getFlingDirection(int angle)
    {
        Gesture gesture = Gesture.FLING_UP;
        if ((angle < -this.ANGLE_45) && (angle > -this.ANGLE_135)) {
            gesture = Gesture.FLING_UP;
        }
        if ((angle < this.ANGLE_135) && (angle > this.ANGLE_45)) {
            gesture = Gesture.FLING_DOWN;
        }
        if ((angle >= -this.ANGLE_45) && (angle <= this.ANGLE_45)) {
            gesture = Gesture.FLING_RIGHT;
        }
        if ((angle >= this.ANGLE_135) || (angle <= -this.ANGLE_135)) {
            gesture = Gesture.FLING_LEFT;
        }
        return gesture;
    }


    private void calTouchPosition(MotionEvent event) {
        mMoveX = event.getX();
        mMoveY = event.getY();
        if (isPointOuterCircle(mMoveX,mMoveY)){
            Point realP = getRealPoint(mMoveX,mMoveY);
            mMoveX =realP.x;
            mMoveY = realP.y;
            isFling = true;
        }else{
            isFling = false;
        }
        mCenterCircleX = (int) mMoveX;
        mCenterCircleY = (int) mMoveY;
    }

    private boolean isPointOuterCircle(float mMoveX, float mMoveY) {
        return PointCalUtil.getInstance(new Point((int)mMoveX, (int)mMoveY),new Point(START_POSITION,START_POSITION)) > OUTER_CIRCLE_R;
    }

    private Point getRealPoint(float mMoveX, float mMoveY){
        Point realP = new Point();
        float s = PointCalUtil.getInstance(new Point((int)mMoveX, (int)mMoveY),new Point(START_POSITION,START_POSITION));
        float scale = INNER_CIRCLE_R/s;
        realP.x = (int) (START_POSITION + (mMoveX -START_POSITION)*scale);
        realP.y = (int) (START_POSITION + (mMoveY -START_POSITION)*scale);
        return realP;
    }



    public void setGestureOperationListener(GestureOperationListener gestureOperationListener) {
        mGestureOperationListener = gestureOperationListener;
    }

    public static enum Gesture{
        SINGLE_CLICK,
        DOUBLE_CLICK,
        LONG_PRESS,
        FLING_UP,
        FLING_DOWN,
        FLING_LEFT,
        FLING_RIGHT,
        MOVE;



    }

    public static abstract interface GestureOperationListener {
        public abstract void onGestureOperate(Gesture gesture);
        public abstract void onGestureOperate(Gesture gesture,Point point);
    }


}