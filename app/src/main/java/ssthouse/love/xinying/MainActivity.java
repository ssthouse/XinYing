package ssthouse.love.xinying;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ssthouse.love.xinying.utils.StringUtils;

public class MainActivity extends AppCompatActivity {


    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvTime = (TextView) findViewById(R.id.id_tv_time);
        tvTime.setText(StringUtils.getLoveTimeStr());
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(StringUtils.getLoveTimeStr());
            }
        });
    }
}
