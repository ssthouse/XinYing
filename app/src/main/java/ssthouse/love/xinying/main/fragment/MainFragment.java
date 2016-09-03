package ssthouse.love.xinying.main.fragment;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseFragment;
import ssthouse.love.xinying.utils.StringUtils;

/**
 * Created by ssthouse on 16/5/11.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.id_tv_time)
    TextView tvTime;

    @Override
    public int getContentView() {
        return R.layout.fragment_main;
    }

    @Override
    public void init() {
        tvTime.setText(StringUtils.getLoveTimeStr());
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(StringUtils.getLoveTimeStr());
            }
        });
    }
}
