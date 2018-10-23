package me.rosuh.likedouyinswitcher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author rosu
 * @date 2018/10/17
 */
public class PagerLayoutManager extends LinearLayoutManager {

    private PagerSnapHelper mPagerSnapHelper;
    private OnViewPagerListener mOnViewPagerListener;
    private RecyclerView mRecyclerView;
    private int mDrift;

    public PagerLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        init();
    }

    public PagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init();
    }

    private void init(){
        mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mPagerSnapHelper.attachToRecyclerView(view);
        mRecyclerView = view;
        mRecyclerView.addOnChildAttachStateChangeListener(mOnChildAttachStateChangeListener);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state){
            case RecyclerView.SCROLL_STATE_IDLE:
                View viewIdle = mPagerSnapHelper.findSnapView(this);
                if (viewIdle == null){
                    break;
                }
                int posIdle = getPosition(viewIdle);
                if (mOnViewPagerListener != null && getChildCount() == 1){
                    mOnViewPagerListener.onPageSelected(posIdle, posIdle == getItemCount() - 1, viewIdle);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                View viewDrag = mPagerSnapHelper.findSnapView(this);
                if (viewDrag == null){
                    break;
                }
                int posDrag = getPosition(viewDrag);
                break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    View viewSettling = mPagerSnapHelper.findSnapView(this);
                    if (viewSettling == null){
                        break;
                    }
                    int posSettling = getPosition(viewSettling);
                    break;
                default:

        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    public void setOnViewPagerListener(OnViewPagerListener onViewPagerListener) {
        mOnViewPagerListener = onViewPagerListener;
    }

    private RecyclerView.OnChildAttachStateChangeListener mOnChildAttachStateChangeListener =
            new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                    if (mOnViewPagerListener != null && getChildCount() == 1){
                        mOnViewPagerListener.onInitComplete(view);
                    }
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (mOnViewPagerListener == null){
                        return;
                    }
                    if (mDrift >= 0){
                        mOnViewPagerListener.onPageRelease(true, getPosition(view), view);
                    }else {
                        mOnViewPagerListener.onPageRelease(false, getPosition(view), view);
                    }
                }
            };
}
