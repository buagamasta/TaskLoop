package at.pinzker.taskloop;
import java.util.regex.*;
public final class AlarmRules {
 public static final int RAMP=0,LOUD=1,NOTIFICATION=2;
 public static class Command {public String text;public int mode=-1;}
 public static Command command(String s){Command c=new Command();c.text=s;Matcher m=Pattern.compile("(?iu)(?:^|[\\s,;:.]+)(lauter alarm|loud alarm|nur benachrichtigung|notification only|alarm)[\\s.!]*$").matcher(s);if(m.find()){String x=m.group(1).toLowerCase(java.util.Locale.ROOT);c.mode=x.equals("lauter alarm")||x.equals("loud alarm")?LOUD:x.equals("nur benachrichtigung")||x.equals("notification only")?NOTIFICATION:RAMP;c.text=s.substring(0,m.start()).trim();}return c;}
 public static float volume(long elapsed,long duration,int start,int target,boolean fade){float end=Math.max(1,Math.min(100,target))/100f;float begin=Math.min(end,Math.max(1,Math.min(100,start))/100f);if(!fade||duration<=0)return end;double progress=Math.min(1,Math.max(0,elapsed/(double)duration));return (float)(begin+(end-begin)*progress);}
}
