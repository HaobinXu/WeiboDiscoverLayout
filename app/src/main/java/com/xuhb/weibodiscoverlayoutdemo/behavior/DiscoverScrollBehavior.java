package com.xuhb.weibodiscoverlayoutdemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xuhb.weibodiscoverlayoutdemo.R;

import java.util.ArrayList;

/**
 * @author xuhb
 * @version 2.8.2
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * @date 16/8/29 下午8:12
 */
public class DiscoverScrollBehavior extends AppBarLayout.ScrollingViewBehavior {

    /**
     * 子控件初始的margin，-height
     */
    int mStartMargin;
    /**
     * appbar的高度
     */
    int mParentHeight;

    /**
     * 子View最后的高度
     */
    int mChildFinalHeight;

    /**
     * tablayout的高度
     */
    int mActionBarSize;
    private ArrayList<View> mChildViews = new ArrayList<>();

    /**
     * 开始变化时的百分比
     */
    private float beginChange = 0.8f;

    private ViewPager viewPager;

    /**
     * 是否从appbar展开状态开始滑
     */
    private boolean scrollFromExpend = true;

    public DiscoverScrollBehavior(Context context, AttributeSet set) {
        super(context, set);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewChanged(parent, child, dependency);
        if (dependency instanceof AppBarLayout) {
            if (child instanceof ViewPager) {
                if (mChildViews.isEmpty()) {
                    ViewPager viewPager = (ViewPager) child;

                    FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) viewPager.getAdapter();
                    for (int i = 0; i < adapter.getCount(); i++) {
                        Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, i);
                        ViewGroup viewGroup = (ViewGroup) fragment.getView();
                        if (viewGroup != null)
                            mChildViews.add(viewGroup.findViewById(R.id.layout_top));
                    }
                }
                if (!mChildViews.isEmpty()) {
                    maybeInitProperties(mChildViews.get(0), dependency);
                    // - dependency.getY()是appbar移动的距离(初始在顶部)，mParentHeight
                    // 所以expandedPercentageFactor的值就是appbar移动的距离占总距离的百分比
                    float expandedPercentageFactor = -dependency.getY() / (mParentHeight-mActionBarSize);
                    // beginChange是当expandedPercentageFactor大于这个值时，子view需要开始推出
                    if (expandedPercentageFactor >= beginChange) {
                        //因为我是在appbar移动beginChange之后才开始动，所以，totalY = (1 - beginChange) * mParentHeight。appbar移动完这个距离的时候，childView需要全部显示出来，并且有一个tabLayout固定在顶部
                        //所以他们的时间是一样的，所以childView要移动的距离是(mChildFinalHeight + mActionBarSize)
                        //得出(expandedPercentageFactor - beginChange) * mParentHeight / totalY = (mChildFinalHeight + mActionBarSize) / X
                        //即appbar之后实际移动的距离比上它启动动画之后移动的总距离，等于child的高度比上它的移动距离。即得出X = (expandedPercentageFactor - beginChange) * mChildFinalHeight / (1 - beginChange)
                        //得出实际的childMargin = mStartMargin + X
                        float childMargin = mStartMargin + (expandedPercentageFactor - beginChange) * (mChildFinalHeight) / (1 - beginChange);
                        for (View mChildView : mChildViews) {
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mChildView.getLayoutParams();
                            lp.topMargin = (int) childMargin;
                            mChildView.setLayoutParams(lp);
                        }
                    } else {
                        for (View mChildView : mChildViews) {
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mChildView.getLayoutParams();
                            lp.topMargin = mStartMargin;
                            mChildView.setLayoutParams(lp);
                        }
                    }

                    if (viewPager == null) {
                        viewPager = (ViewPager) child;
                    }
                    if (mParentHeight + dependency.getY() <= mActionBarSize) {
                        float parentMargin = mActionBarSize - dependency.getY() - mParentHeight;
                        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
                        lp.topMargin = (int) parentMargin;
                        viewPager.setLayoutParams(lp);
                    } else {
                        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
                        lp.topMargin = 0;
                        viewPager.setLayoutParams(lp);
                    }
                }
            }
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    private void maybeInitProperties(View child, View dependency) {
        if (mChildFinalHeight == 0) {
            mChildFinalHeight = dependency.getContext().getResources().getDimensionPixelSize(R.dimen.tab_height);
        }
        if (mParentHeight == 0) {
            mParentHeight = dependency.getHeight();
        }
        if (mActionBarSize == 0) {
            final TypedArray styledAttributes = dependency.getContext().getTheme().obtainStyledAttributes(
                    new int[] { R.attr.actionBarSize });
            mActionBarSize = (int) styledAttributes.getDimension(0, 0);
            styledAttributes.recycle();
        }
        if (mStartMargin == 0) {
            mStartMargin = -dependency.getContext().getResources().getDimensionPixelSize(R.dimen.tab_height);
        }

    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollFromExpend = child.getY() > mActionBarSize;
                break;
            case MotionEvent.ACTION_MOVE:
                if (scrollFromExpend && child.getY() <= mActionBarSize) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                scrollFromExpend = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                scrollFromExpend = false;
                break;
        }
        return super.onTouchEvent(parent, child, ev);
    }
}
