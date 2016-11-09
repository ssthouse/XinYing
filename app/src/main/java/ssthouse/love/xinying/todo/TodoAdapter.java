package ssthouse.love.xinying.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ssthouse.love.xinying.R;

/**
 * Created by ssthouse on 30/10/2016.
 */

public class TodoAdapter extends BaseAdapter {

    private Context mContext;
    private TodoModel mTodoModel;

    public TodoAdapter(Context context, TodoModel todoModel) {
        mContext = context;
        mTodoModel = todoModel;
    }

    @Override
    public int getCount() {
        return mTodoModel.getTodoList().size();
    }

    @Override
    public Object getItem(int position) {
        return mTodoModel.getTodoList().get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_todo, parent, false);
            viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.id_iv);
            viewHolder.tvTodo = (TextView) convertView.findViewById(R.id.id_tv_todo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivAvatar.setImageResource(R.drawable.avatar);
        viewHolder.tvTodo.setText(mTodoModel.getTodoList().get(position).getTodoStr());
        return convertView;
    }


    private class ViewHolder {
        ImageView ivAvatar;
        TextView tvTodo;
    }
}
