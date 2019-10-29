package cjx.liyueyun.viewpagerdemo.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * @author caicai
 * @create 2019/7/2
 * @Describe 移动到第一个，或最后一个时，，拦截左键和右键
 */
public class ViewPagerBorder extends ViewPager {
    public ViewPagerBorder(Context context) {
        super(context);
    }

    public ViewPagerBorder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (getCurrentItem() == 0) {
//                    logUtil.d_2("454545", "左边界");
                    return true;
                }
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (getAdapter()!=null&&getCurrentItem() == getAdapter().getCount() - 1) {
//                    logUtil.d_2("454545", "右边界");
                    return true;
                }
                break;

        }
        return super.dispatchKeyEvent(event);
    }

}
