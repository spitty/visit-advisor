package org.ipccenter.visitadvisor.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.ipccenter.visitadvisor.model.TimeInterval;
import org.slf4j.LoggerFactory;

@ManagedBean
@ApplicationScoped
public class TimeIntervalUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TestEntityCreator.class);
    
    // remove static
    static Date getNextOccurrence(TimeInterval t) {
        long st = t.getStartTime().getTime();
        long et = t.getEndTime().getTime();
        Date d = new Date();
        long ct = d.getTime();
        if (ct >= et) return null;
        long dt = ct - st;
        LOG.debug("dt " + String.valueOf(dt));
        if (dt < 0) {
            dt %= t.getRepeatInterval();
            dt += t.getRepeatInterval();
        }
        d.setTime(ct + dt);
        return d;
    }
    
    // remove static
    static int timeComparator(TimeInterval t1, TimeInterval t2) {
        Date d1 = getNextOccurrence(t1);
        Date d2 = getNextOccurrence(t2);
        if (d1 == null) return -1;
        else if (d2 == null) return 1;
        long d = d1.getTime() - d2.getTime();
        LOG.debug(String.valueOf(d));
        if (d > 0)
            return 1;
        else if (d < 0)
            return -1;
        else
            return 0;
    }
    
    // remove static
    static List<TimeInterval> sortByNextOccurrence(Collection<TimeInterval> times) {
        return times.stream().sorted(/*this*/TimeIntervalUtils::timeComparator).collect(Collectors.toList());
    }
}
