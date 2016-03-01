package events;

/*
 * The event handler interface
 *
 * @author mfaried
 *
 * @param <T>
 *            The type that is used to hold the event information.
 *
 *
 * Created by Gabriel on 28.02.2016.
 */
//@FunctionalInterface
public interface IEventHandler<T extends EventArgs> {
    void handle(Object sender, T args);
}
