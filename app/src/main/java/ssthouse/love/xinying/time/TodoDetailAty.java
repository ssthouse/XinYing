package ssthouse.love.xinying.time;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.time.bean.TodoBean;
import ssthouse.love.xinying.utils.TimeUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 17/12/2016.
 */

public class TodoDetailAty extends BaseActivity {

    @Bind(R.id.id_tb)
    Toolbar mToolbar;

    @Bind(R.id.id_et_content)
    EditText mEtContent;

    @Bind(R.id.id_tv_time)
    TextView mTvTime;

    @Bind(R.id.id_tv_days)
    TextView mTvDays;

    private TodoBean mTodoBean;

    private static final String KEY_TODO_BEAN = "todoBean";

    private static final String DAY_PREFIX = "天";

    public static void start(Context context, TodoBean todoBean) {
        Intent intent = new Intent(context, TodoDetailAty.class);
        intent.putExtra(KEY_TODO_BEAN, todoBean);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        initToolbar();

        mTodoBean = (TodoBean) getIntent().getSerializableExtra(KEY_TODO_BEAN);
        if (mTodoBean == null) {
            return;
        }

        refreshUI();
        mEtContent.setText(mTodoBean.getContent());

        initTimeChooser();
    }

    private void initTimeChooser() {
        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date curDate = mTodoBean.getDate();
                new DatePickerDialog(TodoDetailAty.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTodoBean.getDate().setYear(year - 1900);
                        mTodoBean.getDate().setMonth(monthOfYear);
                        mTodoBean.getDate().setDate(dayOfMonth );
                        Timber.e("set date");
                        refreshUI();
                    }
                }, curDate.getYear() + 1900, curDate.getMonth(), curDate.getDate() - 1).show();
            }
        });

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTodoBean.setContent(mEtContent.getText().toString());
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Title");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void refreshUI() {
        //update ui according to the new date
        Date curDate = new Date();
        mTvTime.setText(DateFormat.format("yyyy-MM-dd", mTodoBean.getDate()));
        mTvDays.setText(TimeUtil.dayInterval(mTodoBean.getDate(), curDate) + DAY_PREFIX);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_todo_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.id_action_save:
                mTodoBean.save();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
