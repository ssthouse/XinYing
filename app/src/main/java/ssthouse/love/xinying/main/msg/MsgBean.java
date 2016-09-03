package ssthouse.love.xinying.main.msg;

/**
 * Created by ssthouse on 16/9/3.
 */
public class MsgBean {

    private String msg;
    private boolean isCony;
    private String timeStr;

    public MsgBean(String msg, boolean isCony, String timeStr) {
        this.msg = msg;
        this.isCony = isCony;
        this.timeStr = timeStr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isCony() {
        return isCony;
    }

    public void setCony(boolean cony) {
        isCony = cony;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}