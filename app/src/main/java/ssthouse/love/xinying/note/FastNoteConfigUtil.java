package ssthouse.love.xinying.note;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import ssthouse.love.xinying.note.bean.FastNoteBean;
import ssthouse.love.xinying.utils.PreferUtil;
import ssthouse.love.xinying.utils.ToastUtil;

/**
 * Created by ssthouse on 2015/12/9.
 */
public class FastNoteConfigUtil {

    private static FastNoteConfigUtil preferenceHelper;

    private SharedPreferences sharedPreferences;

    private Context mContext;

    private static final String KEY_NOTE = "note";
    private static final String KEY_COLOR = "color";
    private static final String PREFER_FILE_NAME = "preference";

    private FastNoteConfigUtil(Context context) {
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static FastNoteConfigUtil getInstance(Context context) {
        if (preferenceHelper == null) {
            preferenceHelper = new FastNoteConfigUtil(context);
        }
        return preferenceHelper;
    }

    public void saveNote(String note) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NOTE, note);
        editor.apply();
    }

    public String getNote() {
        return sharedPreferences.getString(KEY_NOTE, "");
    }

    public void setColor(int color) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_COLOR, color);
        editor.apply();
    }

    public int getColor() {
        return sharedPreferences.getInt(KEY_COLOR, 0xffffffff);
    }


    public void pullFastNoteFromCloud() {
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String myNote) {
                        if (myNote == null) {
                            ToastUtil.show(mContext, "获取失败");
                        } else {
                            FastNoteConfigUtil.getInstance(mContext).saveNote(myNote);
                            ToastUtil.show(mContext, "获取成功");
                        }
                    }
                });
    }
}
