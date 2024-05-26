package com.aditya.thequizapp.retrofit;

import com.aditya.thequizapp.model.QuestionList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionsAPI {

    @GET("quizapi.php") //end point
    Call<QuestionList> getQuestions();

}
