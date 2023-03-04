package banks.NotifyMethods;

/**
 * This class implements the Notificator interface to send a notification message to a subscriber.
 */
public class SimpleNotification implements Notificator {
    private Boolean isNotified;

    /**
     * Sends a notification message to a subscriber.
     * @param message the message to be sent to the subscriber
     */
    public void notifySubscriber(String message) {
        System.out.println(message);
        isNotified = true;
    }

    /**
     * Returns whether the subscriber was notified.
     * @return true if the subscriber was notified, false otherwise
     */
    public Boolean getIsNotified() {
        return isNotified;
    }
}
