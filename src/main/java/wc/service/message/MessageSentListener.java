package wc.service.message;

import wc.model.Message;

public interface MessageSentListener {

    void onMessageSent(Message message);
}
