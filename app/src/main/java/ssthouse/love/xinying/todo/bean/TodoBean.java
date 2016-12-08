package ssthouse.love.xinying.todo.bean;

/**
 * Created by ssthouse on 16/9/3.
 */
public class TodoBean {


    public static final String CLASS_NAME = "TodoBeanList";
    public static final String KEY_TODO_LIST = "TodoList";

    public static final String KEY_TODO_STR = "todoStr";
    public static final String KEY_TIME_STR = "timeStr";

    private String todoStr;
    private String timeStr;


    public TodoBean(String todoStr, String timeStr) {
        this.todoStr = todoStr;
        this.timeStr = timeStr;
    }


    public String getTodoStr() {
        return todoStr;
    }

    public void setTodoStr(String todoStr) {
        this.todoStr = todoStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
