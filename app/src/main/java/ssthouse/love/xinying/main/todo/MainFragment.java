package ssthouse.love.xinying.main.todo;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import lumenghz.com.pullrefresh.PullToRefreshView;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseFragment;
import ssthouse.love.xinying.utils.StringUtils;
import ssthouse.love.xinying.utils.ViewUtil;

/**
 * Created by ssthouse on 16/5/11.
 */
public class MainFragment extends BaseFragment implements IView{

    private static final int MSG_UPDATE_TIME = 1000;

    @Bind(R.id.id_tv_time)
    TextView tvTime;

    @Bind(R.id.id_pull_to_refresh)
    PullToRefreshView pullToRefreshView;

    @Bind(R.id.id_lv)
    ListView listView;

    @Bind(R.id.id_fab_add)
    FloatingActionButton fabAddTodo;

    private DialogPlus addTodoDialog;
    private EditText etTodo;
    private Button btnCalcel, btnEnsure;

    private TodoPresenter todoPresenter;

    private List<TodoBean> curTodoList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_main;
    }

    @Override
    public void init() {
        todoPresenter = new TodoPresenter(this, getContext());

        //初始话todo dialog
        initAddTodoDialog();

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
                todoPresenter.refreshTodoBeanList();
            }
        });

        listView.setAdapter(mAdapter);

        fabAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoDialog.show();
            }
        });
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
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.ivAvatar.setImageResource(R.drawable.avatar);
            viewHolder.tvTodo.setText(curTodoList.get(position).getTodoStr());
            return convertView;
        }
    };

    @Override
    public void refreshTodoList(List<TodoBean> beanList) {
        curTodoList = beanList;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startPullRefresh() {
        pullToRefreshView.setRefreshing(true);
    }

    @Override
    public void stopPullRefresh() {
        pullToRefreshView.setRefreshing(false);
    }

    private void initAddTodoDialog(){
        addTodoDialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_add_todo))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        View view = addTodoDialog.getHolderView();
        etTodo = (EditText) view.findViewById(R.id.id_et_todo);
        btnCalcel = (Button) view.findViewById(R.id.id_btn_cancel);
        btnEnsure = (Button) view.findViewById(R.id.id_btn_ensure);

        btnCalcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoDialog.dismiss();
            }
        });

        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.toast(getContext(), "等大宝宝的下一个版本_(:з」∠)_");
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
