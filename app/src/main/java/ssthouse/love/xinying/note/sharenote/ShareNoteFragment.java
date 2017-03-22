package ssthouse.love.xinying.note.sharenote;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.note.FastNoteConfigUtil;
import ssthouse.love.xinying.note.FastNoteProvider;
import ssthouse.love.xinying.utils.Constant;
import ssthouse.love.xinying.utils.PreferUtil;

/**
 * Created by ssthouse on 16/9/3.
 */
public class ShareNoteFragment extends BaseFragment implements IShareNoteView {

    @Nullable
    @Bind(R.id.id_iv_1)
    ImageView ivStep1;

    @Nullable
    @Bind(R.id.id_iv_2)
    ImageView ivStep2;

    @Nullable
    @Bind(R.id.id_iv_3)
    ImageView ivStep3;

    @Nullable
    @Bind(R.id.id_tv_your_note)
    WebView wbYourNote;

    @Nullable
    @Bind(R.id.id_tv_my_note)
    WebView wvMyNote;

    private ShareNotePresenter mPresenter;

    @Override
    public int getContentView() {
        if (PreferUtil.getInstance(getActivity()).isFistIn()) {
            return R.layout.fragment_into_note;
        } else {
            return R.layout.fragment_share_fast_note;
        }
    }

    @Override
    public void init() {
//        mPresenter = new ShareNotePresenter(getContext(), this);
//        initFragmentMenu();
//        if (PreferUtil.getInstance(getActivity()).isFistIn()) {
//            Picasso.with(getContext())
//                    .load(R.drawable.step_1)
//                    .resize(480, 854)
//                    .into(ivStep1);
//            Picasso.with(getContext())
//                    .load(R.drawable.step_2)
//                    .resize(480, 854)
//                    .into(ivStep2);
//            Picasso.with(getContext())
//                    .load(R.drawable.step_3)
//                    .resize(480, 854)
//                    .into(ivStep3);
//        } else {
//            //init the webview setting
//            wbYourNote.getSettings().setDefaultFontSize(18);
//            wvMyNote.getSettings().setDefaultFontSize(18);
//            mPresenter.loadSharedFastNote();
//            loadLocalNote();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter = new ShareNotePresenter(getContext(), this);
        initFragmentMenu();
        if (PreferUtil.getInstance(getActivity()).isFistIn()) {
            Picasso.with(getContext())
                    .load(R.drawable.step_1)
                    .resize(480, 854)
                    .into(ivStep1);
            Picasso.with(getContext())
                    .load(R.drawable.step_2)
                    .resize(480, 854)
                    .into(ivStep2);
            Picasso.with(getContext())
                    .load(R.drawable.step_3)
                    .resize(480, 854)
                    .into(ivStep3);
        } else {
            //init the webview setting
            wbYourNote.getSettings().setDefaultFontSize(18);
            wvMyNote.getSettings().setDefaultFontSize(18);
            mPresenter.loadSharedFastNote();
            loadLocalNote();
        }
    }

    private void initFragmentMenu() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share_fast_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_action_refresh) {
            mPresenter.loadSharedFastNote();
            loadLocalNote();
            //通知控件更新数据
            Intent intent = new Intent(getContext(), FastNoteProvider.class);
            intent.setAction(Constant.ACTION_NOTE_UPDATE);
            getContext().sendBroadcast(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadLocalNote() {
        if (wvMyNote != null)
            wvMyNote.loadData(FastNoteConfigUtil.getInstance(getContext()).getNote(), "text/html", "utf-8");
    }

    @Override
    public void setYourNoteText(String noteStr) {
        if (noteStr == null)
            return;
        if (wbYourNote != null)
            wbYourNote.loadData(noteStr, "text/html", "utf-8");
    }
}
