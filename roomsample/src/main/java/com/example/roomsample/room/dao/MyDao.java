package com.example.roomsample.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.roomsample.room.bean.Book;
import com.example.roomsample.room.bean.NameTuple;
import com.example.roomsample.room.bean.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MyDao {
    //可以让这个方法返回一个int类型的值，表示从数据库中插入的行数
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(User... users);

    @Insert
    public void insertBothUsers(User user1, User user2);

    @Insert
    public void insertUsersAndFriends(User user, List<User> friends);

    //根据user的主键删除user
    //可以让这个方法返回一个int类型的值，表示从数据库中被删除的行数
    @Delete
    public void deleteUsers(User... users);

    //根据user的主键更新user
    //可以让这个方法返回一个int类型的值，表示从数据库中更新的行数
    @Update
    public void updateUsers(User... users);

    //简单的查询
    //这是一个非常简单的查询，加载所有的user。
    // 在编译时，Room知道它是查询user表中的所有字段。
    //  如果query有语法错误，或者user表不存在，Room将在app编译时显示恰当的错误信息。
    @Query("SELECT * FROM user")
    public User[] loadAllUsers();

    @Query("SELECT * FROM user WHERE age > :minAge")
    public User[] loadAllUsersOlderThan(int minAge);

    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM user WHERE first_name LIKE :search "
            + "OR last_name LIKE :search")
    public List<User> findUserWithName(String search);

    @Query("SELECT first_name, last_name FROM user")
    public List<NameTuple> loadFullName();

//    @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
//    public List<NameTuple> loadUsersFromRegions(List<String> regions);


    /**
     * 可观察的查询
     * 当执行查询的时候，你通常希望app的UI能自动在数据更新的时候更新。
     * 为此，在query方法中使用LiveData类型的返回值。
     * 当数据库变化的时候，Room会生成所有的必要代码来更新LiveData。
     *
     * @param regions
     * @return
     */
    @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    public LiveData<List<User>> loadUsersFromRegionsSync(List<String> regions);


    /**
     * Room还可以让你定义的查询返回RxJava2的Publisher和Flowable对象
     *
     * @param id
     * @return
     */
    @Query("SELECT * from user where id = :id LIMIT 1")
    public Flowable<User> loadUserById(int id);


    /**
     * 如果应用程序的逻辑要求直接访问返回行, 则可以从查询中返回一个对象, 如下面的代码段所示:Cursor
     * 注：不推荐使用Cursor API，因为它无法保证行是否存在或者行中有哪些值。只有在当前的代码需要一个cursor，而且你又不好重构的时候才使用这个功能。
     *
     * @param minAge
     * @return
     */
    @Query("SELECT * FROM user WHERE age > :minAge LIMIT 5")
    public Cursor loadRawUsersOlderThan(int minAge);


    /**
     * 多表查询
     *
     * @param userName
     * @return
     */
    @Query("SELECT * FROM book "
            + "INNER JOIN loan ON loan.book_id = book.id "
            + "INNER JOIN user ON user.id = loan.user_id "
            + "WHERE user.name LIKE :userName")
    public List<Book> findBooksBorrowedByNameSync(String userName);


    /**
     * 返回POJO对象。比如你可以写一个如下的查询加载user与它们的宠物名字：
     *
     * @return
     */
    @Query("SELECT user.name AS userName, pet.name AS petName "
            + "FROM user, pet "
            + "WHERE user.id = pet.user_id")
    public LiveData<List<UserPet>> loadUserAndPetNames();

    // You can also define this class in a separate file, as long as you add the
    // "public" access modifier.
    static class UserPet {
        public String userName;
        public String petName;
    }
}