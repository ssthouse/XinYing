package ssthouse.love.xinying.time;

import android.content.Context;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ssthouse.love.xinying.time.bean.TodoBean;

/**
 * 数据操作Model:
 * 使用 sqlite 存储
 * Created by ssthouse on 30/10/2016.
 */

public class TodoModel {

    private Context mContext;

    private List<TodoBean> mTodoBeanList;

    public TodoModel(Context mContext) {
        this.mContext = mContext;
    }

    public List<TodoBean> getTodoBeanList() {
        if (mTodoBeanList == null) {
            mTodoBeanList = new Select()
                    .from(TodoBean.class)
                    .orderBy("time DESC")
                    .execute();

        }
        return mTodoBeanList;
    }

    public void reloadTodoBeanList() {
        mTodoBeanList = new Select()
                .from(TodoBean.class)
                .orderBy("time DESC")
                .execute();
    }

    public void saveSomeTestTodoBean() {
        List<TodoBean> todoBeanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            todoBeanList.add(new TodoBean("content " + i, new Date()));
        }
        for (TodoBean todoBean : todoBeanList) {
            todoBean.save();
        }
    }
}
