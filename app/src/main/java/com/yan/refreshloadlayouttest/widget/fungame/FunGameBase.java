package com.yan.refreshloadlayouttest.widget.fungame;

import android.content.Context;

import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yan.pullrefreshlayout.ShowGravity;
import com.yan.refreshloadlayouttest.widget.NestedFrameLayout;


/**
 * 游戏 header
 * Created by SCWANG on 2017/6/17.
 * from https://github.com/scwang90/SmartRefreshLayout
 */

public class FunGameBase extends NestedFrameLayout implements PullRefreshLayout.OnPullListener {

    //<editor-fold desc="Field">
    protected int mHeaderHeight;
    protected int mScreenHeightPixels;
    protected boolean mManualOperation;
    protected PullRefreshLayout refreshLayout;
    //</editor-fold>

    //<editor-fold desc="View">
    public FunGameBase(Context context, PullRefreshLayout refreshLayout) {
        super(context);
        this.refreshLayout = refreshLayout;
        initView(context);
    }

    private void initView(Context context) {
        mScreenHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
        mHeaderHeight = (int) (mScreenHeightPixels * 0.16f);
        refreshLayout.setRefreshTriggerDistance(mHeaderHeight);
        refreshLayout.setHeaderShowGravity(ShowGravity.FOLLOW);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    protected void onManualOperationMove(float percent) {

    }

    private boolean isGameViewReady;

    @Override
    public void onPullChange(float percent) {
        if (percent <= 1 && mManualOperation && refreshLayout.isHoldingFinishTrigger() && !refreshLayout.isDragUp() && !refreshLayout.isDragDown()) {
            onPullFinish();
        }
        if (mManualOperation) {
            if (percent == 1) {
                refreshLayout.setDispatchPullTouchAble(true);
                refreshLayout.setMoveWithHeader(false);
                isGameViewReady = true;
            }
            if (isGameViewReady && percent < 1) {
                refreshLayout.moveChildren(refreshLayout.getRefreshTriggerDistance());
            }
            onManualOperationMove(1 + (percent - 1) * 0.6F);
        }
    }

    @Override
    public void onPullHoldTrigger() {

    }

    @Override
    public void onPullHoldUnTrigger() {

    }

    @Override
    public void onPullHolding() {
        mManualOperation = true;
        refreshLayout.setDispatchPullTouchAble(false);
        refreshLayout.setDragDampingRatio(1);
    }

    @Override
    public void onPullFinish() {
        if (refreshLayout.isDragDown() || refreshLayout.isDragUp()) {
            refreshLayout.cancelAllAnimation();
        } else {
            mManualOperation = false;
            isGameViewReady = false;
            refreshLayout.setDragDampingRatio(0.6F);
            refreshLayout.setMoveWithHeader(true);
            refreshLayout.setDispatchPullTouchAble(true);
        }
    }

    @Override
    public void onPullReset() {
    }
}
