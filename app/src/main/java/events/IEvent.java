package events;

/**
 * The interface for the events.
 *
 * @author mfaried
 *
 * @param <T>
 *            The type that is used to hold the event information.
 */
/**
 * Created by Gabriel on 28.02.2016.
 */
public interface IEvent<T extends EventArgs> {

    /**
     * Adds and event handler to this event that is called when the event is
     * fired.
     *
     * @param handler
     *            The handler to be added.
     */
    void addHandler(IEventHandler<T> handler);

    /**
     * Removes an event handler for that event.
     *
     * @param handler
     *            The handler to be removed.
     */
    void removeHandler(IEventHandler<T> handler);

    /**
     * Fires the event and causes all the registered event handlers to be
     * called.
     *
     * @param sender
     *            The sender of the event.
     * @param args
     *            The information about the event.
     */
    void fire(Object sender, T args);

}