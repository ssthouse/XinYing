package ssthouse.love.xinying.main.todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssthouse on 16/9/3.
 */
public class TodoModel {


    public List<TodoBean> loadMore() {
        ArrayList<TodoBean> msgList = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            TodoBean todoBean = new TodoBean("Im am a test msg", "2016.09.05");
            msgList.add(todoBean);
        }
        return msgList;
    }

}
