package ssthouse.love.xinying.main.todo;

/**
 * Created by ssthouse on 16/9/3.
 */
public class TodoBean {

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
