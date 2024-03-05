package com.bigname.core.logging;


import org.apache.ofbiz.base.util.UtilMisc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Manu on 6/3/2018.
 */
public class BlackBox {
    private List<Entry> entries = new ArrayList<>();
    public enum Level {DEBUG, INFO, WARNING, ERROR, SUMMARY }
    private long idx = 0;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss,SSS a");

    public BlackBox() {

    }

    public BlackBox info(String message) {
        entries.add(new Entry(idx++, message, Level.INFO));
        return this;
    }

    public BlackBox debug(String message) {
        entries.add(new Entry(idx++, message, Level.DEBUG));
        return this;
    }

    public BlackBox warning(String message) {
        entries.add(new Entry(idx++, message, Level.WARNING));
        return this;
    }

    public BlackBox error(String message) {
        entries.add(new Entry(idx++, message, Level.ERROR));
        return this;
    }

    public BlackBox summary(String message) {
        entries.add(new Entry(idx++, message, Level.SUMMARY));
        return this;
    }

    public BlackBox merge(BlackBox blackBox) {
        blackBox.entries().forEach(this::add);
        return this;
    }

    public BlackBox extract(Level... level) {
        if(level == null || level.length == 0) {
            return this;
        } else {
            return clone(level[0]);
        }
    }

    private void add(Entry entry) {
        entries().add(entry.clone(idx++));
    }

    private BlackBox clone(Level level) {
        BlackBox clone = new BlackBox();
        clone.entries.addAll(this.entries.stream().filter(e -> e.getLevel().ordinal() >= level.ordinal()).collect(Collectors.toList()));
        clone.idx = clone.entries.size();
        return clone;
    }

    public List<Entry> entries(Level... level) {
        Level _level = level != null && level.length == 1 ? level[0] : Level.INFO;
        return entries.stream().filter(e -> e.getLevel().ordinal() >= _level.ordinal()).collect(Collectors.toList());
    }

    public List<Map<String, Object>> toList(Level... level) {
        List<Map<String, Object>> entryList = new ArrayList<>();
        Level _level = level != null && level.length == 1 ? level[0] : Level.DEBUG;
        entries.stream().filter(e -> e.getLevel().ordinal() >= _level.ordinal()).forEach(e -> entryList.add(e.toMap()));
        return entryList;
    }

    public BlackBox print(Level... level) {
        Level _level = level != null && level.length == 1 ? level[0] : Level.DEBUG;
        entries.stream().filter(e -> e.getLevel().ordinal() >= _level.ordinal() && e.getLevel() != Level.SUMMARY).forEach(System.out::println);
        if(_level == Level.SUMMARY || _level == Level.DEBUG || _level == Level.INFO) {
            entries.stream().filter(e -> e.getLevel() == Level.SUMMARY).forEach(e -> System.out.println(e.toString(true)));
        }

        return this;
    }

    public String toString(Level... level) {
        StringBuilder sb = new StringBuilder();
        Level _level = level != null && level.length == 1 ? level[0] : Level.DEBUG;
        entries.stream().filter(e -> e.getLevel().ordinal() >= _level.ordinal() && e.getLevel() != Level.SUMMARY).forEach(e -> sb.append(e).append("\n"));
        if(_level == Level.SUMMARY || _level == Level.DEBUG || _level == Level.INFO) {
            entries.stream().filter(e -> e.getLevel() == Level.SUMMARY).forEach(e -> sb.append(e.toString(true)).append("\n"));
        }

        return sb.toString();
    }

    class Entry {
        long idx = 0;
        Date time = new Date();
        String message = "";
        Level level = Level.INFO;

        Entry(long idx, String message, Level level) {
            this.idx = idx;
            this.message = message;
            this.level = level;
        }

        long getIdx() {
            return idx;
        }

        public Date getTime() {
            return time;
        }

        public String getMessage() {
            return message;
        }

        public Level getLevel() {
            return level;
        }

        Entry clone(long idx) {
            this.idx = idx;
            return this;
        }

        public String toString() {
            return String.format("%s - %s", sdf.format(time), message);
        }

        public String toString(boolean summaryFlag) {
            return summaryFlag ? message : String.format("%s - %s", sdf.format(time), message);
        }

        Map<String, Object> toMap() {
            return UtilMisc.toMap("idx", idx, "message", message, "level", level, "timestamp", time);
        }

    }
}
