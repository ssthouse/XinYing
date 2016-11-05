package ssthouse.love.xinying.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.vdurmont.emoji.EmojiParser;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseActivity;
import ssthouse.love.xinying.utils.Constant;
import ssthouse.love.xinying.utils.PreferUtil;

/**
 * Created by ssthouse on 16/9/3.
 */
public class NoteActivity extends BaseActivity {

    @Bind(R.id.id_et_fast_note)
    EditText mEtFastNote;

    @Bind(R.id.id_tb)
    Toolbar mToolbar;

    private MenuItem mShareFastnoteMenuItem;

    @Override
    public void init() {
        if (PreferenceHelper.getInstance(this).isFistIn()) {
            PreferenceHelper.getInstance(this).setIsFistIn(false);
            String initialStr = ":kissing_heart::kissing_heart::kissing_heart:";
            initialStr = EmojiParser.parseToUnicode(initialStr);
            PreferenceHelper.getInstance(this).saveNote(initialStr);
        }
        initView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_fast_note;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        saveCurNote();
        super.onWindowFocusChanged(hasFocus);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            getSupportActionBar().setTitle("Cheer up!");

        mEtFastNote.setText(PreferenceHelper.getInstance(this).getNote());
    }

    private void saveCurNote() {
        String str = mEtFastNote.getText() + "";
        PreferenceHelper.getInstance(this).saveNote(str);
        //通知控件更新数据
        Intent intent = new Intent(this, MyWidgetProvider.class);
        intent.setAction(Constant.ACTION_NOTE_UPDATE);
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fast_note, menu);
        mShareFastnoteMenuItem = menu.getItem(1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.id_action_color_pick:
                showColorPickerDialog();
                break;
            case R.id.id_action_share_fast_note:
                item.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        mShareFastnoteMenuItem.setChecked(PreferUtil.getInstance(this).isShareFastNote());
        return super.onMenuOpened(featureId, menu);
    }

    private void showColorPickerDialog() {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(PreferenceHelper.getInstance(this).getColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("确定", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //保存选择的颜色
                        PreferenceHelper.getInstance(NoteActivity.this).setColor(selectedColor);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveCurNote();
    }
}
