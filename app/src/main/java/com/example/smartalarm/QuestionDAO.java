package com.example.smartalarm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionDAO {

    @Query("select * from Question")
    List<Question> getQuestionList();

    @Query("select count(id) from Question")
    Integer getQuestionCount();

    @Insert
    void addQuestion(Alarm Question);

    @Update
    void updateQuestion(Alarm Question);

    @Delete
    void deleteQuestion(Alarm Question);

    @Query("delete from Question")
    Integer deleteAllQuestion();


}
