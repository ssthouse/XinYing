package ssthouse.love.xinying.main.todo;

import java.util.List;

/**
 * Created by ssthouse on 16/9/4.
 */
public interface IView {

    void refreshTodoList(List<TodoBean> beanList);

    void startPullRefresh();

    void stopPullRefresh();
}
