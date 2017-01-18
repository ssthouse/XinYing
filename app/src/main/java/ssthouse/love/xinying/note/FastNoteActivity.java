package ssthouse.love.xinying.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.vdurmont.emoji.EmojiParser;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.mthli.knife.KnifeText;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseActivity;
import ssthouse.love.xinying.utils.Constant;
import ssthouse.love.xinying.utils.PreferUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/9/3.
 */
public class FastNoteActivity extends BaseActivity implements IFastNoteView {

    @Bind(R.id.id_et_fast_note)
    KnifeText mEtFastNote;

    @Bind(R.id.id_tb)
    Toolbar mToolbar;

    private boolean isNoteChanged = false;

    private MenuItem mShareFastnoteMenuItem;

    private FastNotePresenter mPresenter = new FastNotePresenter(this, this);

    @Override
    public void init() {
        if (PreferUtil.getInstance(this).isFistIn()) {
            PreferUtil.getInstance(this).setIsFistIn(false);
            String initialStr = ":kissing_heart::kissing_heart::kissing_heart:";
            initialStr = EmojiParser.parseToUnicode(initialStr);
            FastNoteConfigUtil.getInstance(this).saveNote(initialStr);
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
        initToolbar();
        initFastNoteEditText();
    }

    private void initFastNoteEditText() {
        mEtFastNote.fromHtml(FastNoteConfigUtil.getInstance(this).getNote());
        mEtFastNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isNoteChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            getSupportActionBar().setTitle("Cheer up!");
    }

    @OnClick(R.id.id_btn_strikethrough)
    public void onSthrikeThrough() {
        mEtFastNote.strikethrough(!mEtFastNote.contains(KnifeText.FORMAT_STRIKETHROUGH));
    }

    @OnClick(R.id.id_btn_bold)
    public void onBoldClick() {
        mEtFastNote.bold(!mEtFastNote.contains(KnifeText.FORMAT_BOLD));
    }

    @OnClick(R.id.id_btn_italic)
    public void onItalicClick() {
        mEtFastNote.italic(!mEtFastNote.contains(KnifeText.FORMAT_ITALIC));
    }

    @OnClick(R.id.id_btn_clear)
    public void onClearClick() {
        mEtFastNote.clearFormats();
    }

    @OnClick(R.id.id_btn_bullet)
    public void onBulletClick() {
        mEtFastNote.bullet(!mEtFastNote.contains(KnifeText.FORMAT_BULLET));
    }

    @OnClick(R.id.id_btn_underline)
    public void onUnserlineClick() {
        mEtFastNote.underline(!mEtFastNote.contains(KnifeText.FORMAT_UNDERLINED));
    }

    @OnClick(R.id.id_btn_undo)
    public void onUndoClick() {
        mEtFastNote.undo();
    }

    @OnClick(R.id.id_btn_redo)
    public void onRedoClick() {
        mEtFastNote.redo();
    }

    private void saveCurNote() {
        String str = mEtFastNote.toHtml();
        str = str.replace("<del>", "<strike>");
        str = str.replace("</del>", "</strike>");
        Timber.e(str);
        FastNoteConfigUtil.getInstance(this).saveNote(str);
        //通知控件更新数据
        Intent intent = new Intent(this, FastNoteProvider.class);
        intent.setAction(Constant.ACTION_NOTE_UPDATE);
        sendBroadcast(intent);
        if (isNoteChanged && PreferUtil.getInstance(this).isShareFastNote())
            mPresenter.uploadFastNote();
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
            case R.id.id_action_text_color:
                showColorPickerDialog();
                break;
            case R.id.id_action_background_color:
                showBgColorPicker();
                break;
            case R.id.id_action_share_fast_note:
                item.setChecked(!item.isChecked());
                PreferUtil.getInstance(this).setShareFastnNote(item.isChecked());
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
                .setTitle("Choose text color")
                .initialColor(FastNoteConfigUtil.getInstance(this).getColor())
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
                        FastNoteConfigUtil.getInstance(FastNoteActivity.this).setColor(selectedColor);
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

    private void showBgColorPicker(){
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose background color")
                .initialColor(FastNoteConfigUtil.getInstance(this).getColor())
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
                        FastNoteConfigUtil.getInstance(FastNoteActivity.this).setBgColor(selectedColor);
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

    @Override
    public String getFastNoteStr() {
        return mEtFastNote.toHtml();
    }
}
