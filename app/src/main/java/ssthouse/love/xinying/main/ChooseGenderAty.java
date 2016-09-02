package ssthouse.love.xinying.main;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.utils.ActivityUtil;
import ssthouse.love.xinying.utils.BlurUtil;
import ssthouse.love.xinying.utils.PreferUtil;
import ssthouse.love.xinying.utils.ViewUtil;

/**
 * Created by ssthouse on 16/9/2.
 */
public class ChooseGenderAty extends BaseActivity{

    private boolean isCony = true;

    private static final float TRANSPARENT_ALPHA = 0.5f;

    @Bind(R.id.id_iv_bg)
    ImageView ivBg;

    @Bind(R.id.id_iv_avatar_cony)
    public ImageView ivCony;

    @Bind(R.id.id_iv_avatar_brown)
    public ImageView ivBrown;

    @Bind(R.id.id_tv_who_i_love)
    TextView tvWhoILove;

    @Bind(R.id.id_btn_sure)
    public Button btnSure;

    @Override
    public void init() {
        //模糊背景
        //        获取需要被模糊的原图bitmap
        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.mmexport1458472869885);

        //        scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
        Bitmap blurBitmap = BlurUtil.toBlur(scaledBitmap, 3);
        ivBg.setImageBitmap(blurBitmap);

        //跳动文字
        ViewUtil.loadThreeDot(tvWhoILove);

        //初始话性别
        chooseWho(PreferUtil.getInstance().isCony());

        //点击事件
        ivBrown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseWho(false);
            }
        });
        ivCony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWho(true);
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferUtil.getInstance().setIsCony(isCony);
                ActivityUtil.startAty(ChooseGenderAty.this, MainActivity.class);
                finish();
            }
        });
    }

    private void chooseWho(boolean isCony) {
        this.isCony = isCony;
        if (isCony) {
            ivCony.setAlpha(1.0f);
            ivBrown.setAlpha(TRANSPARENT_ALPHA);
            tvWhoILove.setText(R.string.str_i_love_brown);
        }else{
            ivCony.setAlpha(TRANSPARENT_ALPHA);
            ivBrown.setAlpha(1.0f);
            tvWhoILove.setText(R.string.str_i_love_cony);
        }
        ViewUtil.loadThreeDot(tvWhoILove);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_choose_gender;
    }
}
