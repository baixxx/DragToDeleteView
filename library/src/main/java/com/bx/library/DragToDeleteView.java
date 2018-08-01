package com.bx.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
*@author small white
*@date 2018/7/30
*@fuction 自定义滑动删除控件
*/
public class DragToDeleteView extends FrameLayout {

    private ViewDragHelper helper;
    /**
     * 上层view，被滑动的view
     */
    private View dragView;
    /**
     *下层view
     */
    private View deleteView;
    /**
     *是否是滑开状态
     */
    private boolean isOpen;


    public DragToDeleteView(@NonNull Context context) {
        this(context,null,0);
    }

    public DragToDeleteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DragToDeleteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = ViewDragHelper.create(this,callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child==dragView;
        }

        /**
         *
         * @param child
         * @param left 即将移动到的位置
         * @param dx
         * @return 该方法中对child移动的边界进行控制
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left > 0 ? 0 : left < -deleteView.getWidth() ? -deleteView.getWidth() : left;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (isOpen) {
                if (xvel > deleteView.getWidth() || -dragView.getLeft() < deleteView.getWidth() / 2) {
                    close();
                } else {
                    open();
                }
            } else {
                if (-xvel > deleteView.getWidth() || -dragView.getLeft() > deleteView.getWidth() / 2) {
                    open();
                } else {
                    close();
                }
            }
        }

    };

    /**
     *
     * @param event 事件
     * @return 事件处理交给ViewDragHelper
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    /**
     *
     * @param event 事件
     * @return 事件拦截交给ViewDragHelper
     */
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return helper.shouldInterceptTouchEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        deleteView = getChildAt(0);
        dragView = getChildAt(1);
    }


    /**
     * 滑开
     */
    private void open(){
        helper.smoothSlideViewTo(dragView,-deleteView.getWidth(),0);
        isOpen= true;
        invalidate();
    }
    /**
     * 关闭
     */
    private void close(){
        helper.smoothSlideViewTo(dragView,0,0);
        isOpen= false;
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (helper.continueSettling(true)){
            invalidate();
        }
    }

    public boolean getIsOpen(){
        return isOpen;
    }
}
