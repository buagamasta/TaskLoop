package at.pinzker.taskloop;
import java.util.*;
public final class Recurrence {
 public static final int ONCE=0,DAILY=1,WEEKLY=2,MONTHLY=3,WEEKDAYS=4,INTERVAL=5;
 public static long next(long anchor,int kind,int step,int mask,long now){
  if(anchor>now)return anchor;
  if(kind==ONCE)return -1;
  if(kind==INTERVAL){long span=Math.max(1,step)*60000L;return anchor+((now-anchor)/span+1)*span;}
  Calendar base=Calendar.getInstance();base.setTimeInMillis(anchor);
  Calendar c=(Calendar)base.clone();
  if(kind==MONTHLY){int day=base.get(Calendar.DAY_OF_MONTH);Calendar current=Calendar.getInstance();current.setTimeInMillis(now);int months=(current.get(Calendar.YEAR)-base.get(Calendar.YEAR))*12+current.get(Calendar.MONTH)-base.get(Calendar.MONTH);int n=Math.max(0,months/Math.max(1,step));c.set(Calendar.DAY_OF_MONTH,1);c.add(Calendar.MONTH,n*Math.max(1,step));c.set(Calendar.DAY_OF_MONTH,Math.min(day,c.getActualMaximum(Calendar.DAY_OF_MONTH)));while(c.getTimeInMillis()<=now){c.set(Calendar.DAY_OF_MONTH,1);c.add(Calendar.MONTH,Math.max(1,step));c.set(Calendar.DAY_OF_MONTH,Math.min(day,c.getActualMaximum(Calendar.DAY_OF_MONTH)));}return c.getTimeInMillis();}
  if(kind==WEEKDAYS){Calendar current=Calendar.getInstance();current.setTimeInMillis(now);c.set(current.get(Calendar.YEAR),current.get(Calendar.MONTH),current.get(Calendar.DAY_OF_MONTH),base.get(Calendar.HOUR_OF_DAY),base.get(Calendar.MINUTE),0);c.set(Calendar.MILLISECOND,0);for(int i=0;i<8;i++){int bit=(c.get(Calendar.DAY_OF_WEEK)+5)%7;if(c.getTimeInMillis()>now&&c.getTimeInMillis()>=anchor&&(mask&(1<<bit))!=0)return c.getTimeInMillis();c.add(Calendar.DAY_OF_MONTH,1);}return -1;}
  int days=Math.max(1,step)*(kind==WEEKLY?7:1);long elapsed=Math.max(0,(now-anchor)/86400000L);int cycles=(int)Math.max(0,elapsed/days-1);c.add(Calendar.DAY_OF_MONTH,cycles*days);while(c.getTimeInMillis()<=now)c.add(Calendar.DAY_OF_MONTH,days);return c.getTimeInMillis();
 }
 public static long unattended(long anchor,int kind,int step,int mask,int mode,int minutes,long now){long regular=next(anchor,kind,step,mask,now);if(mode==1)return regular;long retry=now+Math.max(1,minutes)*60000L;return regular>0?Math.min(retry,regular):retry;}
}
