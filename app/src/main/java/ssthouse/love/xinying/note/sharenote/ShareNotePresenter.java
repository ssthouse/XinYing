package ssthouse.love.xinying.note.sharenote;

import android.app.Activity;
import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import ssthouse.love.xinying.note.FastNoteConfigUtil;
import ssthouse.love.xinying.note.bean.FastNoteBean;
import ssthouse.love.xinying.utils.PreferUtil;
import ssthouse.love.xinying.utils.ToastUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 09/11/2016.
 */

public class ShareNotePresenter {

    private Context mContext;
    private IShareNoteView mShareNoteView;

    public ShareNotePresenter(Context context, IShareNoteView mShareNoteView) {
        this.mContext = context;
        this.mShareNoteView = mShareNoteView;
    }

    public void loadSharedFastNote() {
        //直接设置 raw string ===> 正在加载哦 :)
        mShareNoteView.setYourNoteText(" &#27491;&#22312;&#21152;&#36733;&#21734; :)");
        Observable.just(PreferUtil.getInstance((Activity) mContext).isCony())
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Boolean, AVObject>() {
                    @Override
                    public AVObject call(Boolean isCony) {
                        AVObject fastNoteObj = null;
                        AVQuery<AVObject> fastNoteQuery = new AVQuery<AVObject>(FastNoteBean.CLASS_NAME);
                        fastNoteQuery.whereEqualTo(FastNoteBean.KEY_IS_CONY, !isCony);
                        try {
                            fastNoteObj = fastNoteQuery.getFirst();
                        } catch (AVException e) {
                            Timber.e(e.getMessage());
                            e.printStackTrace();
                        }
                        return fastNoteObj;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AVObject>() {
                    @Override
                    public void call(AVObject fastNoteObj) {
                        String fastNoteStr;
                        if (fastNoteObj == null) {
                            fastNoteStr = "获取" + (PreferUtil.getInstance((Activity) mContext).isCony() ? "大宝宝" : "小宝宝")
                                    + "的fastnote失败";
                            ToastUtil.show(mContext, fastNoteStr);
                        } else {
                            fastNoteStr = fastNoteObj.getString(FastNoteBean.KEY_CONTENT);
                            mShareNoteView.setYourNoteText(fastNoteStr);

                        }
                    }
                });
    }

    public void restoreFastNoteFromCloud() {
        final boolean isCony = PreferUtil.getInstance((Activity) mContext).isCony();
        Observable.just(isCony)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Boolean, String>() {
                    @Override
                    public String call(Boolean aBoolean) {
                        AVQuery<AVObject> fastNoteQuery = new AVQuery<AVObject>(FastNoteBean.CLASS_NAME);
                        fastNoteQuery.whereEqualTo(FastNoteBean.KEY_IS_CONY, isCony);
                        AVObject fastNoteObj = null;
                        try {
                            fastNoteObj = fastNoteQuery.getFirst();
                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                        if (fastNoteObj == null) {
                            return null;
                        } else {
                            return fastNoteObj.getString(FastNoteBean.KEY_CONTENT);
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String myNote) {
                        if (myNote == null) {
                            ToastUtil.show(mContext, "上传成功");
                        } else {
                            FastNoteConfigUtil.getInstance(mContext).saveNote(myNote);
                            ToastUtil.show(mContext, "上传成功");
                        }
                    }
                });
    }
}
