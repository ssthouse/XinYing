package ssthouse.love.xinying.todo;

import java.util.List;

import ssthouse.love.xinying.todo.bean.TodoBean;

/**
 * Created by ssthouse on 16/9/4.
 */
public interface IView {

    void refreshTodoList(List<TodoBean> beanList);

    void startPullRefresh();

    void stopPullRefresh();
}
