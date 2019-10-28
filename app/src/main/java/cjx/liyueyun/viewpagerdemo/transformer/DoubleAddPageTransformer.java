package cjx.liyueyun.viewpagerdemo.transformer;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author caicai
 * @create 2019/10/12
 * @Describe
 */
public class DoubleAddPageTransformer implements ViewPager.PageTransformer {
    private float mOffset = 0;
    private String TAG = this.getClass().getSimpleName();
    private float SCALE = 0.8f;
    private int offscreenPageLimit;//vp 缓存个数

    public DoubleAddPageTransformer(int offscreenPageLimit, float mOffset) {
        this.mOffset = mOffset;
        this.offscreenPageLimit = offscreenPageLimit;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        animWay1(page, position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            page.setTranslationZ(-Math.abs(position));
        }
    }

    private int pagerWidth;
    private float horizontalOffsetBase;

    private void animWay1(View page, float position) {
        if (pagerWidth == 0)
            pagerWidth = page.getWidth();//vp width
        if (horizontalOffsetBase == 0)
            horizontalOffsetBase = (pagerWidth - pagerWidth * SCALE) / 2 / offscreenPageLimit + mOffset;
        //setTranslation
//        page.setTranslationX((-page.getWidth() * position) + horizontalOffsetBase * position);
        page.setTranslationX((horizontalOffsetBase - pagerWidth) * position);
    }
}
