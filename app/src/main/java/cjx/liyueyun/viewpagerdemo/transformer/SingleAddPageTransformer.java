package cjx.liyueyun.viewpagerdemo.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author caicai
 * @create 2019/10/12
 * @Describe
 */
public class SingleAddPageTransformer implements ViewPager.PageTransformer {
    private float mOffset = 0;
    private String TAG = this.getClass().getSimpleName();
    private float scall = 1;
    private static final float CENTER_PAGE_SCALE = 0.8f;
    private int offscreenPageLimit;//vp 缓存个数
    private boolean addToRight = true;

    public SingleAddPageTransformer(int offscreenPageLimit, float mOffset, boolean addToRight) {
        this.mOffset = mOffset;
        this.offscreenPageLimit = offscreenPageLimit;
        this.addToRight = addToRight;
    }

    private int pagerWidth;
    private float horizontalOffsetBase;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (pagerWidth == 0)
            pagerWidth = page.getWidth();//vp width
        if (horizontalOffsetBase == 0)
            horizontalOffsetBase = (pagerWidth - pagerWidth * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + mOffset;

        if (addToRight) {
            if (position >= offscreenPageLimit || position <= -1) {//向左边滑去的
//            logUtil.d_2(TAG, "向左边滑去了");
                page.setVisibility(View.GONE);
            } else {
                page.setVisibility(View.VISIBLE);
            }

            //setTranslation
            if (position >= 0) {
//            page.setTranslationX((-pagerWidth * position) + horizontalOffsetBase * position);
                page.setTranslationX((horizontalOffsetBase - pagerWidth) * position);
            } else {
                page.setTranslationX(0);
            }

            //setScale
            if (position == 0) {
                page.setScaleX(CENTER_PAGE_SCALE);
                page.setScaleY(CENTER_PAGE_SCALE);
            } else {
                float scaleFactor = Math.min(CENTER_PAGE_SCALE - position * 0.1f, CENTER_PAGE_SCALE);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }

        } else {
            if ( position >= 1) {
                page.setVisibility(View.GONE);
            } else {
                page.setVisibility(View.VISIBLE);
            }

            //setTranslation
            if (position < 0) {
//            page.setTranslationX((-pagerWidth * position) + horizontalOffsetBase * position);
                page.setTranslationX((horizontalOffsetBase - pagerWidth) * position);
            } else {
                page.setTranslationX(0);
            }

            //setScale
            if (position == 0) {
                page.setScaleX(CENTER_PAGE_SCALE);
                page.setScaleY(CENTER_PAGE_SCALE);
            } else {
                float scaleFactor = Math.min(CENTER_PAGE_SCALE - Math.abs(position) * 0.1f, CENTER_PAGE_SCALE);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }

        }


    }


}
