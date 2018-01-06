package com.example.roomsample.room;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * To do
 *
 * @author chenKeSheng
 * @version V1.0
 * @date 2018-01-07 00:41
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
