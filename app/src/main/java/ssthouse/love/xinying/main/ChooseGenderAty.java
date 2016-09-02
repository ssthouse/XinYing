package ssthouse.love.xinying.main;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.frakbot.jumpingbeans.JumpingBeans;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;

/**
 * Created by ssthouse on 16/9/2.
 */
public class ChooseGenderAty extends BaseActivity{

    @Bind(R.id.id_iv_avatar_cony)
    public ImageView ivCoyny;

    @Bind(R.id.id_iv_avatar_brown)
    public ImageView ivBrown;

    @Bind(R.id.id_tv_who_i_love)
    TextView tvWhoILove;

    @Bind(R.id.id_btn_sure)
    public Button btnSure;

    @Override
    public void init() {
        JumpingBeans.with(btnSure)
                .appendJumpingDots()
                .build();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_choose_gender;
    }
}
