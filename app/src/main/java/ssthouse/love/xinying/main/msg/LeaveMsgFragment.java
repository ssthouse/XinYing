package ssthouse.love.xinying.main.msg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import lumenghz.com.pullrefresh.PullToRefreshView;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.base.BaseFragment;

/**
 * Created by ssthouse on 16/9/3.
 */
public class LeaveMsgFragment extends BaseFragment {

    private MsgModel msgModel;

    private List<MsgBean> curMsgList = new ArrayList<>();

    @Bind(R.id.id_pull_to_refresh)
    PullToRefreshView pullToRefreshView;

    @Bind(R.id.id_lv)
    ListView listView;

    @Bind(R.id.id_fab_add_msg)
    FloatingActionButton fabAdd;

    @Override
    public int getContentView() {
        return R.layout.fragment_leave_msg;
    }

    @Override
    public void init() {
        msgModel = new MsgModel();

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                    curMsgList.addAll(msgModel.loadMore());
                    pullToRefreshView.setRefreshing(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return curMsgList.size();
            }

            @Override
            public Object getItem(int position) {
                return curMsgList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView != null) {
                    viewHolder = (ViewHolder) convertView.getTag();
                }else{
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_leave_msg, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.id_iv_avatar);
                    viewHolder.tvMsg = (TextView) convertView.findViewById(R.id.id_tv_msg);
                    viewHolder.tvTime = (TextView) convertView.findViewById(R.id.id_tv_time);
                    convertView.setTag(viewHolder);
                }
                viewHolder.ivAvatar.setImageResource(R.drawable.logo);
                viewHolder.tvMsg.setText(curMsgList.get(position).getMsg());
                viewHolder.tvTime.setText(curMsgList.get(position).getTimeStr());
                return convertView;
            }
        });

    }

    static class ViewHolder{
        ImageView ivAvatar;
        TextView tvMsg;
        TextView tvTime;
    }
}
