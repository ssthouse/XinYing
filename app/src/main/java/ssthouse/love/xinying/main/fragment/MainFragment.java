package ssthouse.love.xinying.main.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import lumenghz.com.pullrefresh.PullToRefreshView;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseFragment;
import ssthouse.love.xinying.main.todo.TodoBean;
import ssthouse.love.xinying.main.todo.TodoModel;
import ssthouse.love.xinying.utils.StringUtils;

/**
 * Created by ssthouse on 16/5/11.
 */
public class MainFragment extends BaseFragment {

    private static final int MSG_UPDATE_TIME = 1000;

    @Bind(R.id.id_tv_time)
    TextView tvTime;

    @Bind(R.id.id_pull_to_refresh)
    PullToRefreshView pullToRefreshView;

    @Bind(R.id.id_lv)
    ListView listView;

    private TodoModel todoModel;

    private List<TodoBean> curTodoList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_main;
    }

    @Override
    public void init() {
        todoModel = new TodoModel();

        //开始刷新时间
        tvTime.setText(StringUtils.getLoveTimeStr());
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(StringUtils.getLoveTimeStr());
            }
        });
        handler.sendEmptyMessage(MSG_UPDATE_TIME);

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                    curTodoList.addAll(todoModel.loadMore());
                    pullToRefreshView.setRefreshing(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return curTodoList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
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
                }else{
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.ivAvatar.setImageResource(R.drawable.avatar);
                viewHolder.tvTodo.setText(curTodoList.get(position).getTodoStr());
                return convertView;
            }
        });
    }

    class ViewHolder{
        ImageView ivAvatar;
        TextView tvTodo;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    tvTime.setText(StringUtils.getLoveTimeStr());
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, 1000);
                    break;
            }
        }
    };
}
