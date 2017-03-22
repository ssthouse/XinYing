package ssthouse.love.xinying.jokebackup;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.utils.ToastUtil;

/**
 * Created by ssthouse on 22/03/2017.
 */

public class JokeBackupFragment extends BaseFragment {

    @Bind(R.id.id_lv_joke_list)
    ListView mLvJokeList;

    @Bind(R.id.id_et_joke_input)
    EditText mEtJokeInput;

    private List<String> mJokeList = new ArrayList<>();

    private JokeBackupManager mJokeManager;

    @OnClick(R.id.id_btn_ensure)
    public void onEnsureClick() {
        String curJoke = mEtJokeInput.getText().toString();
        if (TextUtils.isEmpty(curJoke)) {
            ToastUtil.show(getContext(), "严肃点,大宝!");
            return;
        }
        mJokeList.add(curJoke);
        mJokeListAdapter.notifyDataSetChanged();
        mJokeManager.appendJoke(curJoke);
        mEtJokeInput.setText("");
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_joke_backup;
    }

    @Override
    public void init() {
        mJokeManager = new JokeBackupManager(getContext());
        mJokeList = mJokeManager.getJokeList();
        mLvJokeList.setAdapter(mJokeListAdapter);
    }

    private BaseAdapter mJokeListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mJokeList.size();
        }

        @Override
        public Object getItem(int position) {
            return mJokeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_joke_backup, parent, false);
                viewHolder.tvJokeContent = (TextView) convertView.findViewById(R.id.id_tv_joke_content);
                viewHolder.btnRemove = (Button) convertView.findViewById(R.id.id_btn_remove);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvJokeContent.setText(mJokeList.get(position));
            viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mJokeList.remove(position);
                    mJokeManager.replaceAllJokes(mJokeList);
                    mJokeListAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    };

    class ViewHolder {
        TextView tvJokeContent;
        Button btnRemove;
    }
}
