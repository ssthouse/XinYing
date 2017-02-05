package ssthouse.love.xinying.time;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import lumenghz.com.pullrefresh.PullToRefreshView;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.time.bean.TodoBean;
import ssthouse.love.xinying.utils.StringUtils;

/**
 * Created by ssthouse on 16/5/11.
 * TODO:
 * 点击 item --->  弹出 dialog 显示数据   可以编辑
 * 左滑显示删除按钮  ---> 点击删除 item
 */
public class TodoFragment extends BaseFragment {

    private static final int MSG_UPDATE_TIME = 1000;

    @Bind(R.id.id_tv_time)
    TextView tvTime;

    @Bind(R.id.id_pull_to_refresh)
    PullToRefreshView mPullToRefreshView;

    @Bind(R.id.id_lv)
    ListView listView;

    @Bind(R.id.id_fab_add)
    FloatingActionButton fabAddTodo;

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

        listView.setAdapter(mAdapter);

        fabAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTodoItemList();
            }
        });
    }

    private void refreshTodoItemList() {
        mTodoModel.reloadTodoBeanList();
        mAdapter.notifyDataSetChanged();
        mPullToRefreshView.setRefreshing(false);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTodoItemChangeEvent(TodoItemChangEvent event) {
        refreshTodoItemList();
    }

    private void startUpdateTimeStr() {
        handler.sendEmptyMessage(MSG_UPDATE_TIME);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mTodoModel.getTodoBeanList().size();
        }

        @Override
        public Object getItem(int position) {
            return mTodoModel.getTodoBeanList().get(position);
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
                viewHolder.tvTodo = (TextView) convertView.findViewById(R.id.id_tv_todo);
                viewHolder.tvTimeLabel = (TextView) convertView.findViewById(R.id.id_tv_time_label);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            TodoBean bean = mTodoModel.getTodoBeanList().get(position);
            viewHolder.tvTodo.setText(bean.getContent());
            viewHolder.tvTimeLabel.setText(DateFormat.format("yyyy-MM-dd", bean.getDate()));
            CardView cvItem = (CardView) convertView.findViewById(R.id.id_card_view);
            cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoDetailAty.start(getContext(), mTodoModel.getTodoBeanList().get(position).getId());
                }
            });
            return convertView;
        }
    };

    class ViewHolder {
        TextView tvTodo;
        TextView tvTimeLabel;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 表示 TodoItem 数据有变化的 event
     * Created by ssthouse on 05/02/2017.
     */
    public static class TodoItemChangEvent {
    }
}
