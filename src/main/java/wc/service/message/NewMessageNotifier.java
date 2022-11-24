package wc.service.message;

public interface NewMessageNotifier {

    void notifyAboutNewMessage(long recipientId, MessageView message);
}
