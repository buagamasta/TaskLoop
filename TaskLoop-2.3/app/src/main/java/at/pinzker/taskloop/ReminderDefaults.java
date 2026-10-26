package at.pinzker.taskloop;
import java.util.*;
public final class ReminderDefaults {
 public static int freeMinute(int[] occupied){Set<Integer> used=new HashSet<>();for(int x:occupied)used.add(x);for(int m=601;m<1440;m++)if(!used.contains(m))return m;for(int m=0;m<601;m++)if(!used.contains(m))return m;return 601;}
 public static long nextWeekday(long now,int[] occupied){Calendar c=Calendar.getInstance();c.setTimeInMillis(now);int m=freeMinute(occupied);c.set(Calendar.HOUR_OF_DAY,m/60);c.set(Calendar.MINUTE,m%60);c.set(Calendar.SECOND,0);c.set(Calendar.MILLISECOND,0);if(c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY||c.getTimeInMillis()<=now){do{c.add(Calendar.DAY_OF_MONTH,1);}while(c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY);}return c.getTimeInMillis();}
 public static long applyParsedTime(long current,long parsed,boolean time,boolean date,boolean rhythm){if(time)return parsed;if(!date)return current;Calendar a=Calendar.getInstance(),b=Calendar.getInstance();a.setTimeInMillis(current);b.setTimeInMillis(parsed);b.set(Calendar.HOUR_OF_DAY,a.get(Calendar.HOUR_OF_DAY));b.set(Calendar.MINUTE,a.get(Calendar.MINUTE));b.set(Calendar.SECOND,0);b.set(Calendar.MILLISECOND,0);return b.getTimeInMillis();}
}
