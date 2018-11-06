package cc.coopersoft.framework.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubscribeValidResult {

    private List<ValidMessage> messages;

    private ValidLevel validLevel;

    SubscribeValidResult(ValidLevel validLevel) {
        this.validLevel = validLevel;
        messages = new ArrayList<>();
    }

    SubscribeValidResult(ValidMessage.Level level, String summary, String detail, Object... params) {
        messages = new ArrayList<>(1);
        messages.add(new ValidMessage(level,summary,detail,params));
    }

    SubscribeValidResult(ValidMessage.Level level, String summary, String detail) {
        messages = new ArrayList<>(1);
        messages.add(new ValidMessage(level,summary,detail));
    }

    SubscribeValidResult(ValidMessage message) {
        messages = new ArrayList<>(1);
        messages.add(message);
    }

    void putMessages(List<ValidMessage> messages){
        if (messages != null && validLevel != null){
            for (ValidMessage message: messages) {
                if (ValidMessage.Level.SERVER.equals(message.getLevel())) {
                    if (ValidLevel.STRICT.equals(validLevel)) {
                        message.setLevel(ValidMessage.Level.OFF);
                    } else {
                        message.setLevel(ValidMessage.Level.WARN);
                    }
                }
                this.messages.add(message);
            }
        }

    }

    ValidMessage.Level maxLevel(){
        ValidMessage.Level result = null;
        for(ValidMessage msg: messages){
            if ((result == null) || (result.pri < msg.getLevel().pri)){
                result = msg.getLevel();
            }
        }
        return result;
    }

    boolean canContinue(){
        ValidMessage.Level level = maxLevel();
        return (level == null) || !ValidMessage.Level.FAIL.equals(level);
    }

    //private boolean pass;

    public Iterator<ValidMessage> getMessages(){
        return messages.iterator();
    }

    public boolean isEmpty(){
        return messages.isEmpty();
    }

    public boolean isPass() {
        ValidMessage.Level level = maxLevel();
        return (level == null) || level.pri <= ValidMessage.Level.WARN.pri;
    }


    public enum ValidLevel{
        STRICT,
        EASY
    }
}
