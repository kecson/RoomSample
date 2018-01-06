package com.example.roomsample;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.roomsample.room.bean.User;
import com.example.roomsample.room.database.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppDatabase mAppDatabase;
    private TextView mTv;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);


        mAppDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name.db")
                .allowMainThreadQueries()//允许在主线程访问数据库，默认禁止
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insertUser();
                break;
            case R.id.btn_query:
                queryUsers();
                break;
            default:
                break;
        }
    }

    private void queryUsers() {
        List<User> all = mAppDatabase.userDao().getAll();
        StringBuilder str = new StringBuilder();
        for (User user : all) {
            str.append(user.toString()).append("\n");
        }
        mTv.setText("");
        mTv.setText(str.toString());
    }

    private void insertUser() {
        User user = new User();
        user.firstName = "first " + System.currentTimeMillis() % 10;
        user.lastName = "last " + System.currentTimeMillis() % 10;
        mAppDatabase.userDao().insertAll(user);
    }
}
