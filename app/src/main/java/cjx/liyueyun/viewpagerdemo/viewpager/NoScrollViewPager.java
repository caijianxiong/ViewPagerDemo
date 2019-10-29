package cjx.liyueyun.viewpagerdemo.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;

/**
 * Created by SongJie on 10/23 0023.
 * 设置禁止左右键滑动
 */

public class NoScrollViewPager extends ViewPager {
    private final String TAG = this.getClass().getSimpleName();

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) currentFocused = null;

        boolean handled = false;
        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
        if (nextFocused != null && nextFocused != currentFocused) {
            if (direction == View.FOCUS_LEFT) {
                    handled = false;
            } else if (direction == View.FOCUS_RIGHT) {
                    handled = false;
            }
        } else if (direction == FOCUS_LEFT || direction == FOCUS_BACKWARD) {//17 or 1
            handled = true;
        } else if (direction == FOCUS_RIGHT || direction == FOCUS_FORWARD) {//66 or 2
            handled = true;
        }
        return handled;
//        return super.arrowScroll(direction);
    }
}