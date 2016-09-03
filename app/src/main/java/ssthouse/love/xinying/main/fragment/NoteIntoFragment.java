package ssthouse.love.xinying.main.fragment;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseFragment;

/**
 * TODO:    第一次进来加载引导页面     第二次直接显示文字编辑界面
 * Created by ssthouse on 16/9/3.
 */
public class NoteIntoFragment extends BaseFragment {

    @Bind(R.id.id_iv_1)
    ImageView ivStep1;

    @Bind(R.id.id_iv_2)
    ImageView ivStep2;

    @Bind(R.id.id_iv_3)
    ImageView ivStep3;


    @Override
    public int getContentView() {
        return R.layout.fragment_into_note;
    }

    @Override
    public void init() {
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
    }
}
