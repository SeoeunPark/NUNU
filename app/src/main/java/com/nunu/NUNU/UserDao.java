package com.nunu.NUNU;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface UserDao {

    // UserInfo 테이블에 들어 있는 값을 모두 출력
    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getAll();

    // UserInfo 테이블에 존재하는 가장 최근 좌안 데이터 1개 가져오기
    @Query("SELECT leftSight FROM UserInfo ORDER BY id DESC limit 1")
    String getLeft();

    // UserInfo 테이블에 존재하는 가장 최근 우안 데이터 1개 가져오기
    @Query("SELECT rightSight FROM UserInfo ORDER BY id DESC limit 1")
    String getRight();

    // UserInfo 테이블에 마지막으로 업데이트 된 유저명 가져오기
    @Query("SELECT username FROM UserInfo ORDER BY id DESC limit 1")
    String getName();

    // UserInfo 테이블에 마지막으로 업데이트 된 날짜 가져오기
    @Query("SELECT date FROM UserInfo ORDER BY id DESC limit 1")
    String getDate();

    // 데이터 입력
    @Insert
    void insert(UserInfo userInfo);

    // 데이터 업데이트
    @Update
    void update(UserInfo userInfo);

    // 데이터 삭제
    @Delete
    void delete(UserInfo userInfo);

    // where절이 존재하지 않으므로 UserInfo에 존재하는 데이터 전체가 사라지게 됨
    @Query("DELETE FROM UserInfo")
    void deleteAll();

}
