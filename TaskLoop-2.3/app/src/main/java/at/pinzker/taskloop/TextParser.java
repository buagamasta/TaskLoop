package at.pinzker.taskloop;
import java.util.*;import java.util.regex.*;
public final class TextParser {
 public static class Result {public String title,note;public int kind=0,step=1,mask=31,ignore=0,pause=15,doneMode=0;public long when;public boolean hasTime,hasRhythm,hasDate,hasPause,hasIgnore;}
 public static Result parse(String input,long now,boolean english){return parse(english?EnglishInput.normalise(input):input,now);}
 public static Result parse(String input,long now){Result r=new Result();r.title=input.trim();r.note="";String s=input.toLowerCase(Locale.GERMAN);Calendar c=Calendar.getInstance();c.setTimeInMillis(now);c.add(Calendar.HOUR_OF_DAY,1);c.set(Calendar.SECOND,0);c.set(Calendar.MILLISECOND,0);
  Matcher date=Pattern.compile("(?<!\\d)(\\d{1,2})\\.(\\d{1,2})\\.(?:(\\d{4}|\\d{2})(?!\\d))?").matcher(s);boolean explicit=false;if(date.find()){Calendar d=Calendar.getInstance();d.setTimeInMillis(now);int y=date.group(3)==null?d.get(Calendar.YEAR):Integer.parseInt(date.group(3));if(y<100)y+=2000;d.setLenient(false);d.set(y,Integer.parseInt(date.group(2))-1,Integer.parseInt(date.group(1)),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),0);d.set(Calendar.MILLISECOND,0);try{d.getTimeInMillis();c=d;explicit=true;}catch(IllegalArgumentException e){}}
  if(!explicit&&(s.contains("morgen")||s.contains("heute"))){c.setTimeInMillis(now);c.set(Calendar.SECOND,0);c.set(Calendar.MILLISECOND,0);if(s.contains("übermorgen"))c.add(Calendar.DAY_OF_MONTH,2);else if(s.contains("morgen"))c.add(Calendar.DAY_OF_MONTH,1);explicit=true;}
  Matcher time=Pattern.compile("(?:um\\s+)(\\d{1,2})(?:[:.](\\d{2}))?(?:\\s*uhr)?|(?<!\\d)(\\d{1,2}):(\\d{2})(?!\\d)").matcher(s);if(time.find()){int h=Integer.parseInt(time.group(1)!=null?time.group(1):time.group(3));String mins=time.group(1)!=null?time.group(2):time.group(4);int m=mins==null?0:Integer.parseInt(mins);if(h<24&&m<60){c.set(Calendar.HOUR_OF_DAY,h);c.set(Calendar.MINUTE,m);r.hasTime=true;if(!explicit&&c.getTimeInMillis()<=now)c.add(Calendar.DAY_OF_MONTH,1);}}
  if(s.contains("täglich")||s.contains("jeden tag")||s.contains("jeden morgen")){r.kind=Recurrence.DAILY;r.hasRhythm=true;}if(s.contains("wöchentlich")||s.contains("jede woche")){r.kind=Recurrence.WEEKLY;r.hasRhythm=true;}if(s.contains("monatlich")||s.contains("jeden monat")){r.kind=Recurrence.MONTHLY;r.hasRhythm=true;}if(s.contains("werktags")||s.contains("montag bis freitag")){r.kind=Recurrence.WEEKDAYS;r.mask=31;r.hasRhythm=true;}
  Matcher every=Pattern.compile("(?:alle|jeden)\\s+(\\d+)\\s*(minuten|minute|stunden|stunde|tage|tag|wochen|woche|monate|monat)").matcher(s);if(every.find()&&!(r.hasRhythm&&(s.contains("erneut")||s.contains("wieder erinnern")||s.contains("pause"))&&(every.group(2).startsWith("min")||every.group(2).startsWith("stund")))){int n=Integer.parseInt(every.group(1));String unit=every.group(2);r.step=Math.max(1,n);r.kind=unit.startsWith("min")||unit.startsWith("stund")?Recurrence.INTERVAL:unit.startsWith("woc")?Recurrence.WEEKLY:unit.startsWith("mon")?Recurrence.MONTHLY:Recurrence.DAILY;if(unit.startsWith("stund"))r.step*=60;r.hasRhythm=true;}
  String[] days={"montag","dienstag","mittwoch","donnerstag","freitag","samstag","sonntag"};int mask=0;for(int i=0;i<7;i++)if(s.contains(days[i]))mask|=1<<i;if(mask!=0&&!s.contains("bis")){r.kind=Recurrence.WEEKDAYS;r.mask=mask;r.hasRhythm=true;}
  Matcher pause=Pattern.compile("(?:nach|pause(?:\\s+von)?|alle)\\s+(\\d+)\\s*minuten(?:\\s+(?:erneut|wieder|erinnern))?").matcher(s);if(pause.find()&&(s.contains("wieder erinnern")||s.contains("erneut")||s.contains("pause")||s.contains("nach"))){r.pause=Math.max(1,Integer.parseInt(pause.group(1)));r.hasPause=true;}
  if(s.contains("nächsten tag")||s.contains("nächsten termin")||s.contains("nächsten wiederholung")){r.ignore=1;r.hasIgnore=true;}
  Matcher relative=Pattern.compile("(?:in)\\s+(\\d+)\\s*(minuten|minute|stunden|stunde|tagen|tag)").matcher(s);if(relative.find()){long n=Long.parseLong(relative.group(1));String u=relative.group(2);long factor=u.startsWith("stund")?3600000L:u.startsWith("tag")?86400000L:60000L;c.setTimeInMillis(now+n*factor);r.hasTime=true;}
  if(r.kind==Recurrence.WEEKDAYS){while((r.mask&(1<<((c.get(Calendar.DAY_OF_WEEK)+5)%7)))==0)c.add(Calendar.DAY_OF_MONTH,1);}
  r.hasDate=explicit;r.when=c.getTimeInMillis();
  String clean=input.replaceAll("(?iu)(?:;|,)?\\s*(?:nach|pause(?:\\s+von)?|alle)\\s+\\d+\\s*minuten\\s*(?:erneut|wieder)(?:\\s+erinnern)?", " ")
   .replaceAll("(?iu)(?:;|,)?\\s*(?:erst\\s+)?(?:bei(?:m)?|zur?)?\\s*(?:der\\s+)?nächsten\\s+(?:tag|termin|wiederholung)(?:\\s+erinnern)?", " ")
   .replaceAll("(?iu)\\b(?:ab\\s+)?(?:übermorgen|morgen|heute)\\b", " ")
   .replaceAll("(?iu)\\b(?:am\\s+)?\\d{1,2}\\.\\d{1,2}\\.(?:\\d{2,4})?", " ")
   .replaceAll("(?iu)\\b(?:um\\s+\\d{1,2}(?:[:.]\\d{2})?(?:\\s*uhr)?|\\d{1,2}:\\d{2})\\b", " ")
   .replaceAll("(?iu)\\b(?:in|alle|jeden)\\s+\\d+\\s*(?:minuten|minute|stunden|stunde|tagen|tage|tag|wochen|woche|monate|monat)\\b", " ")
   .replaceAll("(?iu)\\b(?:täglich|wöchentlich|monatlich|werktags|jeden tag|jeden morgen|jede woche|jeden monat|montag bis freitag)\\b", " ")
   .replaceAll("(?iu)\\b(?:jeden?\\s+)?(?:montag|dienstag|mittwoch|donnerstag|freitag|samstag|sonntag)(?:s)?(?:\\s+und\\s+)?\\b", " ")
   .replaceAll("\\s+", " ").replaceAll("^[\\s,;:.]+|[\\s,;:.]+$", "").trim();
  if(clean.length()>2){r.title=clean.substring(0,1).toUpperCase(Locale.GERMAN)+clean.substring(1);}return r;
 }
}
