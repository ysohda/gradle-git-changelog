package com.selesse.dates;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FlexibleDateParser {
    private final List<ThreadLocal<SimpleDateFormat>> threadLocals;
    private static final List<String> formats = Lists.newArrayList(
            "yyyy-MM-dd HH:mm:ss Z",
            "EEE MMM d HH:mm:ss yyyy Z"
    );

    public FlexibleDateParser() {
        threadLocals = Lists.newArrayList();

        for (final String format : formats) {
            ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
                dateFormat.setLenient(false);
                return dateFormat;
            });
            threadLocals.add(dateFormatThreadLocal);
        }
    }

    public Date parseDate(String dateStr) {
        for (ThreadLocal<SimpleDateFormat> dateFormatThreadLocal : threadLocals) {
            SimpleDateFormat dateFormat = dateFormatThreadLocal.get();
            try {
                return dateFormat.parse(dateStr);
            } catch (ParseException ignored) {
            }
        }
        return null;
    }
}
