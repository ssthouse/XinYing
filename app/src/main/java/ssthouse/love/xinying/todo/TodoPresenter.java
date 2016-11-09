package ssthouse.love.xinying.todo;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/9/3.
 */
public class TodoPresenter {


    private IView iView;

    private Context context;

    public TodoPresenter(IView iView, Context context) {
        this.iView = iView;
        this.context = context;
    }

    public void refreshTodoBeanList() {
        Observable.just("")
                .map(new Func1<String, AVObject>() {
                    @Override
                    public AVObject call(String s) {
                        AVObject avObject = null;
                        AVQuery avQuery = new AVQuery(TodoBean.CLASS_NAME);
                        try {
                            avObject = avQuery.getFirst();
                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                        if (avObject == null) {
                            Timber.e("获取 todoList avObject W为空");
                        }
                        return avObject;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AVObject>() {
                    @Override
                    public void call(AVObject obj) {
                        //如果服务器没有List
                        if (obj == null) {
                            AVObject avObject = new AVObject(TodoBean.CLASS_NAME);
                            ArrayList<TodoBean> initList = new ArrayList<TodoBean>();
                            initList.add(new TodoBean("在一起, 一辈子", System.currentTimeMillis() + ""));
                            avObject.put(TodoBean.KEY_TODO_LIST, initList);
                            avObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    Timber.e(e == null ? "todo list saved" : "todo list saved fail");
                                }
                            });
                            iView.stopPullRefresh();
                            return;
                        }
                        //如果服务器数据为空
                        if (obj.getList(TodoBean.KEY_TODO_LIST) == null) {
                            iView.stopPullRefresh();
                            return;
                        }
                        //填充数据
                        List<TodoBean> beanList = new ArrayList<>();
                        for (Object object : obj.getList(TodoBean.KEY_TODO_LIST)) {
                            HashMap<String,String> mashMap = (HashMap<String, String>) object;
                            beanList.add(new TodoBean(mashMap.get(TodoBean.KEY_TODO_STR),
                                    mashMap.get(TodoBean.KEY_TIME_STR)));
                        }
                        //停止刷新
                        iView.stopPullRefresh();
                        iView.refreshTodoList(beanList);
                    }
                });
    }
}
