package at.pinzker.taskloop;
import org.json.*;
public class Task {
 public int id,kind=0,step=1,mask=31,ignore=0,pause=15,doneMode=0,alarmMode=0,category=0; public String title="",note="";public long anchor,next,last,completedAt;public boolean active=true,reminder=true;
 public Task(){}
 public Task(JSONObject j){id=j.optInt("id");title=j.optString("title");note=j.optString("note");kind=j.optInt("kind");step=j.optInt("step",1);mask=j.optInt("mask",31);ignore=j.optInt("ignore");pause=j.optInt("pause",15);doneMode=j.optInt("doneMode");alarmMode=j.optInt("alarmMode",0);category=j.optInt("category",0);anchor=j.optLong("anchor");next=j.optLong("next");last=j.optLong("last");completedAt=j.optLong("completedAt");active=j.optBoolean("active",true);reminder=j.optBoolean("reminder",true);}
 public JSONObject json(){JSONObject j=new JSONObject();try{j.put("id",id).put("title",title).put("note",note).put("kind",kind).put("step",step).put("mask",mask).put("ignore",ignore).put("pause",pause).put("doneMode",doneMode).put("alarmMode",alarmMode).put("category",category).put("anchor",anchor).put("next",next).put("last",last).put("completedAt",completedAt).put("active",active).put("reminder",reminder);}catch(JSONException e){throw new IllegalStateException(e);}return j;}
 public long regular(long now){return Recurrence.next(anchor,kind,step,mask,now);}
 public String rhythm(android.content.Context c){if(!L.english(c))return rhythm();String[] n={"Once","Every "+step+" days","Every "+step+" weeks","Every "+step+" months","Selected weekdays","Every "+step+" minutes"};return n[Math.max(0,Math.min(5,kind))];}
 public String rhythm(){String[] names={"Einmalig","Alle "+step+" Tage","Alle "+step+" Wochen","Alle "+step+" Monate","Ausgewählte Wochentage","Alle "+step+" Minuten"};return names[Math.max(0,Math.min(5,kind))];}
}
