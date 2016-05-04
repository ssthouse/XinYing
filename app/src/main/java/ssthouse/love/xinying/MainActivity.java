package ssthouse.love.xinying;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private static Calendar loveBeginCalendar = new GregorianCalendar(2016, 2, 13);

    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvTime = (TextView) findViewById(R.id.id_tv_time);
        tvTime.setText(getLoveTimeStr());
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(getLoveTimeStr());
            }
        });
    }

    //init love time
    private String getLoveTimeStr() {
        //Date currentDate =
        Calendar currentCalendar = Calendar.getInstance();
        return "已相爱: " + (currentCalendar.getTimeInMillis() - loveBeginCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24) + "天";
    }
}
