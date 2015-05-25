package org.ipccenter.visitadviser.bean;

import java.util.List;

import org.ipccenter.visitadvisor.bean.EventContainer;
import org.ipccenter.visitadvisor.bean.EventService;
import org.ipccenter.visitadvisor.model.Event;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author spitty
 */
@Test
public class EventServiceTest {

    static EventService service = new EventService();
    
    public static void test() {
        Event event1 = new Event();
        event1.setName("wfdsllskfjkldsfklj");

        Event event = service.add(event1);
    }
}
