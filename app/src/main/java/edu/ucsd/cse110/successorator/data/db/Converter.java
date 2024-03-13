package edu.ucsd.cse110.successorator.data.db;

import androidx.room.TypeConverter;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.RecurringTypeFactory;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String recurringTypeToString(RepeatType repeatType) { return repeatType.name(); }

    @TypeConverter
    public static RepeatType stringToRecurringType(String repeatTypeString) {
        return RepeatType.valueOf(repeatTypeString);
    }
}
