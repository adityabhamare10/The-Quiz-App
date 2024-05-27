package com.aditya.thequizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aditya.thequizapp.databinding.ActivityMainBinding;
import com.aditya.thequizapp.model.Question;
import com.aditya.thequizapp.model.QuestionList;
import com.aditya.thequizapp.viewModel.QuizViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    QuizViewModel quizViewModel;

    List<Question> questionList;

    static int result = 0;
    static int totalQuestion = 0;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Data Binding
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );

        // Resetting the Scores
        result = 0;
        totalQuestion = 0;

        // instance of QuizViewModel
        quizViewModel = new ViewModelProvider(this)
                .get(QuizViewModel.class);

        //Displaying the first question
        DisplayFirstQuestion();

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayNextQuestion();
            }
        });

    }

    public void DisplayFirstQuestion(){
        quizViewModel.getQuestionListLiveData().observe(
                this,
                new Observer<QuestionList>() {
                    @Override
                    public void onChanged(QuestionList questions) {
                        // Called when the data changes inside LiveData
                        questionList = questions;

                        binding.textQuestion.setText("Question 1: " + questions.get(0).getQuestion());
                        binding.radio1.setText(questions.get(0).getOption1());
                        binding.radio2.setText(questions.get(0).getOption2());
                        binding.radio3.setText(questions.get(0).getOption3());
                        binding.radio4.setText(questions.get(0).getOption4());
                    }
                }
        );
    }

    public void DisplayNextQuestion(){

        //Direct the user to the result Activity

        if(binding.nextBtn.getText().toString().equals("Finish")){
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(intent);
//            finish();
        }


        //Displaying the Question
        int selectedOption = binding.radioGroup.getCheckedRadioButtonId();
        if (selectedOption != -1) {
            RadioButton radioButton = findViewById(selectedOption);

            //More Question to Display???
            if((questionList.size() - i)>0){
                //Getting the number of Questions
                totalQuestion = questionList.size();

                //Checking the answer
                if(radioButton.getText().toString().equals(questionList.get(i).getCorrectOption()
                )){
                    result++;
                    binding.textResult.setText(
                            "Correct Answer : " + result
                    );
                }
                if(i==0){
                    i++;
                }

                //Displaying the next Questions
                binding.textQuestion.setText("Question "+(i+1)+": "+questionList.get(i).getQuestion());
                binding.radio1.setText(questionList.get(i).getOption1());
                binding.radio2.setText(questionList.get(i).getOption2());
                binding.radio3.setText(questionList.get(i).getOption3());
                binding.radio4.setText(questionList.get(i).getOption4());

                //check if it is the last question
                if(i==(questionList.size()-1)){
                    binding.nextBtn.setText("Finish");
                }
                binding.radioGroup.clearCheck();
                i++;



            }
            else{
                if(radioButton.getText().toString().equals(
                        questionList.get(i-1).getCorrectOption()
                )){
                    result++;
                    binding.textResult.setText("Correct Answer: " + result);
                }
            }
        }else{
            Toast.makeText(
                    this,
                    "Please make an Selection",
                    Toast.LENGTH_SHORT).show();
        }


    }

}