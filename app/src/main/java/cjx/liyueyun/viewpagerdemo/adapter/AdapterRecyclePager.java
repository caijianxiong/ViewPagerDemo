package cjx.liyueyun.viewpagerdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import cjx.liyueyun.baselib.base.mvp.utils.logUtil;

/**
 * @author caicai
 * @create 2019/10/22
 * @Describe
 */
public abstract class AdapterRecyclePager<T, H extends AdapterRecyclePager.PagerHolder> extends PagerAdapter {
    private String TAG = "RecyclePagerAdapter02";
    private final WeakReference<Context> mContext;
    private List<T> dataList;

    //不删除记号，用于destroyItem时不清除view
    protected HashMap<Integer, H> allHolder;

    public AdapterRecyclePager(Context context, List<T> infos) {
        if (infos == null)
            throw new NullPointerException();
        this.mContext = new WeakReference<>(context);
        this.dataList = infos;
        allHolder = new HashMap<>();
        createAllItemView(dataList);
    }

    public void refreshOneItem(List<T> newDataList, int refreshPos) {
        this.dataList = newDataList;
        H holder = allHolder.get(refreshPos);
        T info = dataList.get(refreshPos);
        onBindPagerHolder(holder, info, refreshPos);
    }


    private void createAllItemView(List<T> dataList) {
        allHolder.clear();
        for (int i = 0; i < dataList.size(); i++) {
            H itemH = onCreatePagerHolder(mContext.get(), i);
            allHolder.put(i, itemH);
        }
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //获取缓存下标
        logUtil.d_2(TAG, "instantiateItem--" + position + "---allHolder=" + allHolder.size());
        T info = dataList.get(position);
        H holder;
        holder = allHolder.get(position);
        if (holder == null) {
            logUtil.d_2(TAG, "应该不会出现这种情况吧");
            holder = onCreatePagerHolder(mContext.get(), position);
            allHolder.put(position, holder);
        }
        if (-1 == container.indexOfChild(holder.view)) {
            container.addView(holder.view);
            holder.view.setVisibility(View.VISIBLE);
            logUtil.d_2(TAG, "没有" + position + "位置的View");
        }
        onBindPagerHolder(holder, info, position);
        return holder.view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //检查是否在不删除标记内，是则不移除view，否则移除view
        View removeView = (View) object;
        container.removeView(removeView);
    }

    /**
     * 创建一个Holder
     *
     * @param position 页面下标
     * @return holder
     */
    protected abstract H onCreatePagerHolder(Context context, int position);

    /**
     * 绑定页面数据
     *
     * @param info     页面数据
     * @param position 页面下标
     */
    protected abstract void onBindPagerHolder(H holder, T info, int position);

    /**
     * 用于释放页面destroyItem调用且移除时调用
     */
    protected void onReleasePagerHolder(H holder, int position) {
    }

    public List<T> getPagerInfos() {
        return dataList;
    }

    public Context getContext() {
        return mContext.get();
    }

    public static class PagerHolder {
        public final View view;

        public PagerHolder(View view) {
            view.setTag(this);
            this.view = view;
        }
    }

}
