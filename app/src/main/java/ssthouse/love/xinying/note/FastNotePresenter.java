package ssthouse.love.xinying.note;

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
import ssthouse.love.xinying.bean.FastNoteBean;
import ssthouse.love.xinying.utils.PreferUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 05/11/2016.
 */

public class FastNotePresenter {

    private Context mContext;

    private IFastNoteView mFastNoteView;

    public FastNotePresenter(Context mContext, IFastNoteView mFastNoteView) {
        this.mContext = mContext;
        this.mFastNoteView = mFastNoteView;
    }

    public void uploadFastNote() {
        final boolean isCony = PreferUtil.getInstance((Activity) mContext).isCony();
        final String fastNoteContent = mFastNoteView.getFastNoteStr();
        Observable.just(isCony)
                .observeOn(Schedulers.newThread())
                .map(new Func1<Boolean, AVObject>() {
                    @Override
                    public AVObject call(Boolean isCony) {
                        AVObject fastNoteObj;
                        AVQuery<AVObject> fastNoteQuery = new AVQuery<>(FastNoteBean.CLASS_NAME);
                        fastNoteQuery.whereEqualTo(FastNoteBean.KEY_IS_CONY, isCony);
                        try {
                            fastNoteObj = fastNoteQuery.getFirst();
                        } catch (AVException e) {
                            e.printStackTrace();
                            fastNoteObj = null;
                        }
                        return fastNoteObj;
                    }
                })
                .map(new Func1<AVObject, Exception>() {
                    @Override
                    public Exception call(AVObject fastNoteObj) {
                        Exception exception = null;
                        if (fastNoteObj == null) {
                            fastNoteObj = new AVObject(FastNoteBean.CLASS_NAME);
                            fastNoteObj.put(FastNoteBean.KEY_IS_CONY, isCony);
                            fastNoteObj.put(FastNoteBean.KEY_CONTENT, fastNoteContent);
                        } else {
                            fastNoteObj.put(FastNoteBean.KEY_CONTENT, fastNoteContent);
                        }
                        try {
                            fastNoteObj.save();
                        } catch (AVException e) {
                            exception = e;
                            e.printStackTrace();
                        }
                        return exception;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Exception>() {
                    @Override
                    public void call(Exception e) {
                        if (e == null) {
                            Timber.e("fastnote 上传成功");
                        } else {
                            Timber.e("fastnote 上传失败");
                        }
                    }
                });
    }
}
