package cc.coopersoft.framework.services;


public class SubscribeFailException extends Exception{

    private SubscribeValidResult validResult;

    public SubscribeFailException(String summary, String detail, Object... params) {
        validResult = new SubscribeValidResult(ValidMessage.Level.FAIL,summary,detail,params);
    }

    public SubscribeFailException(String summary, String detail) {
        validResult = new SubscribeValidResult(ValidMessage.Level.FAIL,summary,detail);
    }

    public SubscribeFailException(ValidMessage msg) {
        if (!ValidMessage.Level.FAIL.equals(msg.getLevel())){
            throw new IllegalArgumentException("messages level must be FAIL!");
        }
        validResult = new SubscribeValidResult(msg);
    }

    SubscribeFailException(SubscribeValidResult validResult) {
        this.validResult = validResult;
    }

    public SubscribeValidResult getValidResult() {
        return validResult;
    }
}