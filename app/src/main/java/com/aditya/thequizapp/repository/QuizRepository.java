package com.aditya.thequizapp.repository;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aditya.thequizapp.model.Question;
import com.aditya.thequizapp.model.QuestionList;
import com.aditya.thequizapp.retrofit.QuestionsAPI;
import com.aditya.thequizapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRepository {

//    Interacting with the API service interfaces
//            Handling data retrieval and operations

    private QuestionsAPI questionsAPI;

    public QuizRepository() {
        this.questionsAPI = new RetrofitInstance()
                .getRetrofitInstance()
                .create(QuestionsAPI.class);
    }

    public LiveData<QuestionList> getQuestionsFromAPI() {
        MutableLiveData<QuestionList> data = new MutableLiveData<>();

        Call<QuestionList> response = questionsAPI.getQuestions();
        response.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                // saving the data to the list
                QuestionList list = response.body();
                data.setValue(list);
            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable throwable) {

            }
        });

        return data;

    }

}
