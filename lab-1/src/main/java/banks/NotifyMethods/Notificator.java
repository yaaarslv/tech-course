package banks.NotifyMethods;

/**
 * The Notificator interface provides a way to send notifications to subscribers.
 */
public interface Notificator {
    /**
     * Returns whether the subscriber has been notified.
     * @return true if the subscriber has been notified, false otherwise
     */
    Boolean getIsNotified();

    /**
     * Sends a notification message to the subscriber.
     * @param message the notification message to send
     */
    void notifySubscriber(String message);
}
