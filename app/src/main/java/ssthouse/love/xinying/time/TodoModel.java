package ssthouse.love.xinying.time;

import android.content.Context;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import ssthouse.love.xinying.time.bean.TodoBean;
import timber.log.Timber;

/**
 * 数据操作Model:
 * 使用 sqlite 存储
 * Created by ssthouse on 30/10/2016.
 */

public class TodoModel {

    private Context mContext;

    public TodoModel(Context mContext) {
        this.mContext = mContext;
    }

    // TODO: 30/10/2016
    public Observable<List<TodoBean>> getTodoList() {
        List<TodoBean> todoBeanList = new Select()
                .from(TodoBean.class)
                .execute();
        Timber.e(todoBeanList.toString());

        return Observable.just("").map(new Func1<String, List<TodoBean>>() {
            @Override
            public List<TodoBean> call(String s) {
                List<TodoBean> todoBeanList = new Select()
                        .from(TodoBean.class)
                        .execute();
                Timber.e(todoBeanList.toString());
                return todoBeanList;
            }
        });
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
