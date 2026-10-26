package at.pinzker.taskloop;
import android.content.*;
public class AlarmReceiver extends BroadcastReceiver {
 @Override public void onReceive(Context c,Intent i){String action=i.getAction();if("RESUME_REMINDERS".equals(action)){Scheduler.restore(c);return;}int id=i.getIntExtra("id",-1);Task t=Store.get(c,id);if(t==null)return;if("FINISH".equals(action)){Scheduler.finish(c,t);return;}if("DONE".equals(action)){Scheduler.done(c,t);return;}if("SNOOZE".equals(action)){Scheduler.snooze(c,t);return;}if(!"FIRE".equals(action)||!t.active||!t.reminder)return;long now=System.currentTimeMillis();if(t.next>now+1500)return; t.last=now;t.next=Recurrence.unattended(t.anchor,t.kind,t.step,t.mask,t.ignore,t.pause,now);Store.save(c,t);Scheduler.schedule(c,t);Scheduler.display(c,t);}
}
