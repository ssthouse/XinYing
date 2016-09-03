package ssthouse.love.xinying.main.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssthouse on 16/9/3.
 */
public class MsgModel {


    public List<MsgBean> loadMore() {
        ArrayList<MsgBean> msgList = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            MsgBean msgBean = new MsgBean("Im am a test msg", true, "2016.09.05");
            msgList.add(msgBean);
        }
        return msgList;
    }

}
