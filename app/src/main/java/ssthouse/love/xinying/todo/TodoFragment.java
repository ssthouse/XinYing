package ssthouse.love.xinying.todo;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import lumenghz.com.pullrefresh.PullToRefreshView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.todo.bean.TodoBean;
import ssthouse.love.xinying.utils.StringUtils;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/5/11.
 */
public class TodoFragment extends BaseFragment {

    private static final int MSG_UPDATE_TIME = 1000;

    @Bind(R.id.id_tv_time)
    TextView tvTime;

    @Bind(R.id.id_pull_to_refresh)
    PullToRefreshView pullToRefreshView;

    @Bind(R.id.id_lv)
    ListView listView;

    @Bind(R.id.id_fab_add)
    FloatingActionButton fabAddTodo;

    private List<TodoBean> curTodoList = new ArrayList<>();

    private CustomHandler handler = new CustomHandler(this);

    private TodoModel mTodoModel = new TodoModel(this.getContext());

    @Override
    public int getContentView() {
        return R.layout.fragment_todo;
    }

    @Override
    public void init() {
        initLoveTime();
        startUpdateTimeStr();

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTodoModel.getTodoList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TodoBean>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(List<TodoBean> todoBeanList) {
                                Timber.e("what is wrong");
                                for (TodoBean todoBean : todoBeanList) {
                                    Timber.e(todoBean.content + todoBean.date.toString());
                                }
                            }
                        });
            }
        });

        listView.setAdapter(mAdapter);

        fabAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodoModel.saveSomeTestTodoBean();
            }
        });
    }

    private void initLoveTime() {
        tvTime.setText(StringUtils.getLoveTimeStr());
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(StringUtils.getLoveTimeStr());
            }
        });
    }

    private void startUpdateTimeStr() {
        handler.sendEmptyMessage(MSG_UPDATE_TIME);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return curTodoList.size();
        }

        @Override
        public Object getItem(int position) {
            return curTodoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
                viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.id_iv);
                viewHolder.tvTodo = (TextView) convertView.findViewById(R.id.id_tv_todo);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.ivAvatar.setImageResource(R.drawable.avatar);
            viewHolder.tvTodo.setText(curTodoList.get(position).getContent());
            return convertView;
        }
    };

    class ViewHolder {
        ImageView ivAvatar;
        TextView tvTodo;
    }

    private static class CustomHandler extends Handler {

        WeakReference<TodoFragment> mFragmentReference;

        CustomHandler(TodoFragment mainFragment) {
            mFragmentReference = new WeakReference<TodoFragment>(mainFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            final TodoFragment mainFragment = mFragmentReference.get();
            if (mainFragment == null) {
                return;
            }
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    mainFragment.tvTime.setText(StringUtils.getLoveTimeStr());
                    mainFragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 1000);
                    break;
            }
        }
    }

}
