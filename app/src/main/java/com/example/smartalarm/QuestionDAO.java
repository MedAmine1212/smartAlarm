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
    void addQuestion(Question question);

    @Update
    void updateQuestion(Question question);

    @Delete
    void deleteQuestion(Question question);

    @Query("delete from Question")
    Integer deleteAllQuestion();

    @Query("SELECT * FROM  Question ORDER BY RANDOM() LIMIT 1")
    Question getRandomQuestion();

    @Query("SELECT * FROM  Question WHERE selected = ' false '")
    Question getNoSelectedQuestion();

    @Query("SELECT * FROM  Question WHERE selected = ' true '")
    Question getSelectedQuestion();

}
