package com.aditya.thequizapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aditya.thequizapp.model.QuestionList;
import com.aditya.thequizapp.repository.QuizRepository;

public class QuizViewModel extends ViewModel {

    QuizRepository repository = new QuizRepository();

    LiveData<QuestionList> questionListLiveData;


    public QuizViewModel() {
        questionListLiveData = repository.getQuestionsFromAPI();
    }

    public LiveData<QuestionList> getQuestionListLiveData() {
        return questionListLiveData;
    }
}
