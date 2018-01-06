package com.example.roomsample.room.bean;

import android.arch.persistence.room.ColumnInfo;

/**
 * 获取user的first name 和 last name:
 *
 * @author chenKeSheng
 * @version V1.0
 * @date 2018-01-07 00:30
 */
public class NameTuple {
    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;


}