package ssthouse.love.xinying.main.fragment;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseFragment;
import ssthouse.love.xinying.main.bean.FastNoteBean;
import ssthouse.love.xinying.note.FastNoteConfigUtil;
import ssthouse.love.xinying.utils.PreferUtil;

/**
 * Created by ssthouse on 16/9/3.
 */
public class ShareNoteFragment extends BaseFragment {

    @Nullable
    @Bind(R.id.id_iv_1)
    ImageView ivStep1;

    @Nullable
    @Bind(R.id.id_iv_2)
    ImageView ivStep2;

    @Nullable
    @Bind(R.id.id_iv_3)
    ImageView ivStep3;

    @Nullable
    @Bind(R.id.id_tv_your_note)
    TextView tvYourNote;

    @Nullable
    @Bind(R.id.id_tv_my_note)
    TextView tvMyNote;

    @Override
    public int getContentView() {
        if (PreferUtil.getInstance(getActivity()).isFistIn()) {
            return R.layout.fragment_into_note;
        } else {
            return R.layout.fragment_share_fast_note;
        }
    }

    @Override
    public void init() {
        if (PreferUtil.getInstance(getActivity()).isFistIn()) {
            Picasso.with(getContext())
                    .load(R.drawable.step_1)
                    .resize(480, 854)
                    .into(ivStep1);
            Picasso.with(getContext())
                    .load(R.drawable.step_2)
                    .resize(480, 854)
                    .into(ivStep2);
            Picasso.with(getContext())
                    .load(R.drawable.step_3)
                    .resize(480, 854)
                    .into(ivStep3);
        } else {
            loadSharedFastNote();
            loadLocalNote();
        }
    }

    private void loadLocalNote() {
        tvMyNote.setText(FastNoteConfigUtil.getInstance(getContext()).getNote());
    }

    private void loadSharedFastNote() {
        Observable.just(PreferUtil.getInstance(getActivity()).isCony())
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
                            fastNoteStr = "获取" + (PreferUtil.getInstance(getActivity()).isCony() ? "大宝宝" : "小宝宝")
                                    + "的fastnote失败";
                        } else {
                            fastNoteStr = fastNoteObj.getString(FastNoteBean.KEY_CONTENT);
                        }
                        tvYourNote.setText(fastNoteStr);
                    }
                });
    }
}
