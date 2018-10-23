package me.rosuh.likedouyinswitcher;

import android.view.View;

/**
 * @author rosu
 * @date 2018/10/17
 */
public interface OnViewPagerListener {
    /**
     * 初始化
     */ void onInitComplete(View view); /**
     * 释放
     */ void onPageRelease(boolean isNext, int position, View view); /**
     * 选中
     */ void onPageSelected(int position, boolean isBottom, View view);
}
