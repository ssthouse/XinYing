package ssthouse.love.xinying;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.vdurmont.emoji.EmojiParser;

import butterknife.Bind;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.main.MainActivity;
import ssthouse.love.xinying.utils.ActivityUtil;
import ssthouse.love.xinying.utils.PreferUtil;
import ssthouse.love.xinying.note.FastNoteConfigUtil;

/**
 * Created by ssthouse on 16/9/2.
 */
public class SplashActivity extends BaseActivity {

    private static final int ANIMATION_TIME = 800;

    @Bind(R.id.id_tv_hello)
    public TextView tvHello;

    @Bind(R.id.id_iv_bg)
    public ImageView ivBg;

    @Bind(R.id.id_iv_logo)
    public ImageView ivLogo;

    @Override
    public void init() {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        //开始动画
        Animator animatorX = ObjectAnimator.ofFloat(ivBg, "scaleX", 1.0f, 1.1f)
                .setDuration(ANIMATION_TIME);
        Animator animatorY = ObjectAnimator.ofFloat(ivBg, "scaleY", 1.0f, 1.1f)
                .setDuration(ANIMATION_TIME);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (PreferUtil.getInstance(SplashActivity.this).isFistIn()) {
                    //初始话note
                    String initialStr = ":kissing_heart::kissing_heart::kissing_heart:";
                    initialStr = EmojiParser.parseToUnicode(initialStr);
                    FastNoteConfigUtil.getInstance(SplashActivity.this).saveNote(initialStr);
                    //启动MainAty
                    ActivityUtil.startAty(SplashActivity.this, ChooseGenderAty.class);
                    PreferUtil.getInstance(SplashActivity.this).setIsFistIn(false);
                } else {
                    ActivityUtil.startAty(SplashActivity.this, MainActivity.class);
                }
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }
}
