package cjx.liyueyun.viewpagerdemo.transformer;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

import javax.xml.transform.Transformer;

/**
 * @author caicai
 * @create 2019/10/29
 * @Describe
 */
public class ScaleTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.00f;
    private static final float MIN_SCALE = 0.7f;

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;

        float slope = (MAX_SCALE - MIN_SCALE) / 1;
        float scaleValue = MIN_SCALE + tempScale * slope;
        page.setScaleX(scaleValue);
        page.setScaleY(scaleValue);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            page.getParent().requestLayout();
        }

    }
}
