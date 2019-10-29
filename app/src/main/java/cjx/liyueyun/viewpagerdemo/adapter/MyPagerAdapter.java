package cjx.liyueyun.viewpagerdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cjx.liyueyun.baselib.base.mvp.adapter.BasePagerAdapter;
import cjx.liyueyun.viewpagerdemo.R;

public class MyPagerAdapter extends BasePagerAdapter<Integer, MyPagerAdapter.MyHolder> {


    public MyPagerAdapter(List<Integer> dataList, Context mContext) {
        super(dataList, mContext);
    }

    @NonNull
    @Override
    protected MyHolder createHolder() {
        return new MyHolder(View.inflate(mContext, R.layout.item_vp, null));
    }

    @Override
    protected void onBindView(MyHolder holder, Integer data, int position) {
        holder.tvLeft.setText(String.valueOf(position));
        holder.tvRight.setText(String.valueOf(position));
        holder.itemView.setBackgroundResource(data);
    }

    static class MyHolder extends BasePagerAdapter.BaseHolder {

        private final TextView tvLeft;
        private final TextView tvRight;

        public MyHolder(View itemView) {
            super(itemView);
            tvLeft = (TextView) itemView.findViewById(R.id.tv_left);
            tvRight = (TextView) itemView.findViewById(R.id.tv_right);
        }
    }
}
