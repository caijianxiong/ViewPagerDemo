package cjx.liyueyun.viewpagerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cjx.liyueyun.baselib.base.mvp.BaseActivity;

public class MainActivity extends BaseActivity {

    private Integer[] colors = new Integer[]{
            R.color.tomato,
            R.color.aliceblue,
            R.color.cadetblue,
            R.color.gold,
            R.color.pink,
            R.color.lightsalmon,
            R.color.hotpink,
            R.color.deeppink,
            R.color.magenta,
            R.color.salmon,
            R.color.sandybrown,
            R.color.khaki,
            R.color.crimson,
            R.color.goldenrod,
            R.color.greenyellow,
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
