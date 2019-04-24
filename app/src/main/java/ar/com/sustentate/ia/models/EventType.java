package ar.com.sustentate.ia.models;

/**
 * Created by emzas on 15/01/2019.
 */

public class EventType{

    public enum EventTypes {
        speach,
        course,
        fair,
        workingDay,
        workshop,
        festival,
        volunteering;
    }

    EventTypes eventT;

    public EventType(EventTypes eventT) {
        this.eventT = eventT;
    }
}

