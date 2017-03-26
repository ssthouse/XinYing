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

import com.activeandroid.query.Select;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.time.bean.TodoBean;
import ssthouse.love.xinying.utils.TimeUtil;
import ssthouse.love.xinying.utils.ToastUtil;
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

    @Bind(R.id.id_tv_delete)
    TextView tvDelete;

    private TodoBean mTodoBean;

    private boolean isNewTodoItem = false;

    private static final String KEY_TODO_BEAN_ID = "todoBeanId";
    public static final long NEW_TODO_ITEM_ID = -1;

    public static final String SUFFIX = "天";

    public static void start(Context context, Long todoBeanId) {
        Intent intent = new Intent(context, TodoDetailAty.class);
        intent.putExtra(KEY_TODO_BEAN_ID, todoBeanId);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        initToolbar();
        // 取得 TodoItem 实例
        long todoBeanId = getIntent().getLongExtra(KEY_TODO_BEAN_ID, -1);
        if (todoBeanId == -1) {
            isNewTodoItem = true;
            mTodoBean = new TodoBean("say something", new Date());
        } else {
            mTodoBean = new Select()
                    .from(TodoBean.class)
                    .where("id = ?", todoBeanId)
                    .executeSingle();
            if (mTodoBean == null) {
                ToastUtil.show(this, "Something is wrong, better call your giant baby :)");
                finish();
            }
        }
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewTodoItem) {
                    return;
                }
                mTodoBean.delete();
                ToastUtil.show(TodoDetailAty.this, "deleted");
                EventBus.getDefault().post(new TodoFragment.TodoItemChangEvent());
                finish();
            }
        });

        initTimeChooser();
        refreshUI();
    }

    private void initTimeChooser() {
        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date curDate = mTodoBean.getDate();
                new DatePickerDialog(TodoDetailAty.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date newDate = new Date();
                        newDate.setYear(year - 1900);
                        newDate.setMonth(monthOfYear);
                        newDate.setDate(dayOfMonth);
                        mTodoBean.setDate(newDate);
                         Timber.e("set date: " + mTodoBean.getDate().toString());
                        refreshUI();
                    }
                }, curDate.getYear() + 1900, curDate.getMonth(), curDate.getDate()).show();
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
        getSupportActionBar().setTitle("Time flies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void refreshUI() {
        //update ui according to the new date
        mTvTime.setText(DateFormat.format("yyyy-MM-dd", mTodoBean.getDate()));
        mTvDays.setText(TimeUtil.dayInterval(mTodoBean.getDate(), new Date()) + SUFFIX);
        mEtContent.setText(mTodoBean.getContent());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_todo_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
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
                EventBus.getDefault().post(new TodoFragment.TodoItemChangEvent());
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
