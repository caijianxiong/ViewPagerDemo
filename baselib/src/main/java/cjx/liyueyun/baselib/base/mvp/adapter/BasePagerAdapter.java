package cjx.liyueyun.baselib.base.mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BasePagerAdapter<T, H extends BasePagerAdapter.BaseHolder> extends PagerAdapter {


    protected List<T> dataList;
    protected Context mContext;

    public BasePagerAdapter(List<T> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        H holder;
        T data;
        holder = createHolder();
        data = dataList.get(position);
        container.addView(holder.itemView);
        onBindView(holder, data, position);
        return holder.itemView;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View removeView = (View) object;
        container.removeView(removeView);
        onReleasePagerHolder((H) removeView.getTag(),position);
    }


    @NonNull
    protected abstract H createHolder();
    protected abstract void onBindView(H holder, T data, int position);
    /**
     * 用于释放页面destroyItem调用且移除时调用
     */
    protected void onReleasePagerHolder(H holder, int position) {
    }

    public static class BaseHolder {
        public final View itemView;
        public BaseHolder(View itemView) {
            itemView.setTag(this);
            this.itemView = itemView;
        }
    }

}
