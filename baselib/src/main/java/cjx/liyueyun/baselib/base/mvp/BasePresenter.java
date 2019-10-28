package cjx.liyueyun.baselib.base.mvp;


/**
 * @author caicai
 * @create 2019/9/26
 * @Describe 
 */
public class BasePresenter<V extends BaseView> {
    protected V mView;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    public void detachView() {
        this.mView = null;
    }

    /**
     * View是否绑定
     * @return
     */
    public boolean isAttachedView() {
        return mView != null;
    }

    public V getmView() {
        if (!isAttachedView()) throw new RuntimeException("presenter has not attach to view");
        return mView;
    }
}
