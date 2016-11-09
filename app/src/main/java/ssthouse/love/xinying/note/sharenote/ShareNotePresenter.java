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
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.bean.FastNoteBean;
import ssthouse.love.xinying.utils.PreferUtil;

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
        mShareNoteView.setYourNoteText(mContext.getString(R.string.str_load_your_note));
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
                        } else {
                            fastNoteStr = fastNoteObj.getString(FastNoteBean.KEY_CONTENT);
                        }
                        mShareNoteView.setYourNoteText(fastNoteStr);
                    }
                });
    }
}
