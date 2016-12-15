package ssthouse.love.xinying.todo.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by ssthouse on 16/9/3.
 */
@Table(name = "Todo")
public class TodoBean extends Model {

    @Column(name = "content")
    public String content;

    @Column(name = "time")
    public Date date;

    public TodoBean() {
        super();
    }

    public TodoBean(String content, Date date) {
        super();
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
