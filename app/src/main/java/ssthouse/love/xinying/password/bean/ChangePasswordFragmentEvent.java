package ssthouse.love.xinying.password.bean;

/**
 * Created by ssthouse on 26/03/2017.
 */

public class ChangePasswordFragmentEvent {

    private boolean isToPassword;

    public ChangePasswordFragmentEvent(boolean isToPassword) {
        this.isToPassword = isToPassword;
    }

    public boolean isToPassword() {
        return isToPassword;
    }

    public void setToPassword(boolean toPassword) {
        isToPassword = toPassword;
    }
}
