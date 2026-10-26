import at.pinzker.taskloop.*;import java.util.*;
public class LogicTests {
 static int count; static long ts(String s){java.time.LocalDateTime d=java.time.LocalDateTime.parse(s);return d.atZone(java.time.ZoneId.of("Europe/Vienna")).toInstant().toEpochMilli();}
 static void eq(long actual,long expected,String message){count++;if(actual!=expected)throw new AssertionError(message+" expected="+expected+" actual="+actual);}
 static void yes(boolean condition,String message){count++;if(!condition)throw new AssertionError(message);}
 public static void main(String[] args){TimeZone.setDefault(TimeZone.getTimeZone("Europe/Vienna"));long a=ts("2026-10-06T09:00:00");
 eq(Recurrence.next(a,1,1,31,ts("2026-10-06T08:00:00")),a,"first upcoming");
 eq(Recurrence.next(a,1,1,31,a),ts("2026-10-07T09:00:00"),"daily next on completion");
 eq(Recurrence.next(a,1,2,31,ts("2026-10-09T12:00:00")),ts("2026-10-10T09:00:00"),"two days anchored");
 eq(Recurrence.next(a,2,2,31,ts("2026-10-21T09:00:00")),ts("2026-11-03T09:00:00"),"two weeks");
 eq(Recurrence.next(ts("2026-01-31T09:00:00"),3,1,31,ts("2026-01-31T10:00:00")),ts("2026-02-28T09:00:00"),"month clamp");
 eq(Recurrence.next(ts("2026-01-31T09:00:00"),3,1,31,ts("2026-02-28T10:00:00")),ts("2026-03-31T09:00:00"),"month restores 31");
 eq(Recurrence.next(ts("2026-10-23T09:00:00"),4,1,31,ts("2026-10-23T10:00:00")),ts("2026-10-26T09:00:00"),"weekdays skip weekend and DST");
 eq(Recurrence.next(ts("2026-10-24T09:00:00"),1,1,31,ts("2026-10-24T09:00:00")),ts("2026-10-25T09:00:00"),"daily autumn DST");
 eq(Recurrence.next(ts("2026-03-28T09:00:00"),1,1,31,ts("2026-03-28T09:00:00")),ts("2026-03-29T09:00:00"),"daily spring DST");
 eq(Recurrence.next(a,0,1,31,a),-1,"once has no recurrence");
 eq(Recurrence.next(a,5,90,31,a+50000),a+90*60000,"minute interval");
 eq(Recurrence.unattended(a,1,1,31,0,15,a),a+15*60000,"automatic retry");
 eq(Recurrence.unattended(a,1,1,31,1,15,a),ts("2026-10-07T09:00:00"),"wait until regular");
 eq(Recurrence.unattended(a,1,1,31,0,15,ts("2026-10-07T08:55:00")),ts("2026-10-07T09:00:00"),"regular before retry");
 eq(Recurrence.unattended(a,0,1,31,0,15,a),a+900000,"once retry till done");
 long now=ts("2026-10-06T01:40:00");TextParser.Result r=TextParser.parse("Morgen um 9 Uhr täglich Rechnung prüfen; nach 15 Minuten erneut erinnern",now);
 eq(r.when,ts("2026-10-07T09:00:00"),"tomorrow time");eq(r.kind,1,"parse daily");yes(r.title.equals("Rechnung prüfen"),"task title separated from schedule");eq(r.pause,15,"parse retry pause");
 r=TextParser.parse("Jeden Montag und Donnerstag um 18:30 Pflanzen prüfen",now);eq(r.mask,9,"weekday mask");eq(r.kind,4,"weekday kind");eq(r.when,ts("2026-10-08T18:30:00"),"next chosen weekday");
 r=TextParser.parse("In 30 Minuten anrufen",now);eq(r.when,now+1800000,"relative time");
 r=TextParser.parse("Alle 2 Wochen um 9 Uhr anrufen",now);eq(r.kind,2,"two weekly parse");eq(r.step,2,"two weekly step");
 r=TextParser.parse("Täglich um 9 Uhr, alle 20 Minuten erneut erinnern",now);eq(r.kind,1,"retry must not replace base recurrence");eq(r.pause,20,"retry extracted");
 r=TextParser.parse("Am 24.12.2026 um 10:30 Geschenke bringen",now);eq(r.when,ts("2026-12-24T10:30:00"),"explicit date");
 r=TextParser.parse("werktags um 8 Uhr erst beim nächsten Termin erinnern",now);eq(r.ignore,1,"regular only parse");eq(r.mask,31,"business day mask");yes(r.hasTime,"time found");
 System.out.println("PASS: "+count+" Zeit- und Freitextprüfungen");
 }
}
