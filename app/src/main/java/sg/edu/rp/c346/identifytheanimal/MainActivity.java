package sg.edu.rp.c346.identifytheanimal;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import sg.edu.rp.c346.identifytheanimal.Adapter.GridViewAnswerAdapter;
import sg.edu.rp.c346.identifytheanimal.Adapter.GridViewQuestionAdapter;
import sg.edu.rp.c346.identifytheanimal.Common.Common;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //for sound
    public List<String> questionCharacter = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewQuestionAdapter questionAdapter;

    public Button btnSubmit;

    public GridView gridViewQuestion, gridViewAnswer;

    public ImageButton imgbtnQuestion;

    int[] image_list = {
            R.drawable.bear,
            R.drawable.bird,
            R.drawable.cat,
            R.drawable.cow,
            R.drawable.dog,
            R.drawable.duck,
            R.drawable.elephant,
            R.drawable.giraffe,
            R.drawable.kangaroo,
            R.drawable.lion,
            R.drawable.monkey,
            R.drawable.pig,
            R.drawable.sheep,
            R.drawable.snake,
            R.drawable.tiger

    };
    int[] songs = {

            R.raw.bear,
            R.raw.bird,
            R.raw.cat,
            R.raw.cow,
            R.raw.dog,
            R.raw.duck,
            R.raw.elephant,
            R.raw.giraffe,
            R.raw.kangaroo,
            R.raw.lion,
            R.raw.monkey,
            R.raw.pig,
            R.raw.sheep,
            R.raw.snake,
            R.raw.tiger
    };


    public char[] answer;

    String correct_answer;

    private void setupList() {
        //random logo
        Random random = new Random();
        int imgSelected = image_list[random.nextInt(image_list.length)];
        imgbtnQuestion.setImageResource(imgSelected);

        correct_answer = getResources().getResourceName(imgSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);

        answer = correct_answer.toCharArray();

        Common.user_submit_answer = new char[answer.length];


        //add answer character to list
        questionCharacter.clear();

        for (char item : answer) {

            //add animal name to list
            questionCharacter.add(String.valueOf(item));

        }

            //random add some character to list

        for (int i = answer.length; i < answer.length + (10-answer.length); i++) {

            questionCharacter.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

            //sort random
            Collections.shuffle(questionCharacter);

            //set for GridView
            answerAdapter = new GridViewAnswerAdapter(setupNullList(), this);
            questionAdapter = new GridViewQuestionAdapter(questionCharacter, this, this);

            answerAdapter.notifyDataSetChanged();
            questionAdapter.notifyDataSetChanged();

            gridViewQuestion.setAdapter(questionAdapter);
            gridViewAnswer.setAdapter(answerAdapter);

        }
    }
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton imgAnimal;
        imgAnimal = (ImageButton) findViewById(R.id.imgAnimal);
        imgAnimal.setOnClickListener(this);

        gridViewAnswer = (GridView) findViewById(R.id.gridViewAnswer);
        gridViewQuestion = (GridView) findViewById(R.id.gridViewQuestion);

        imgbtnQuestion = (ImageButton) findViewById(R.id.imgAnimal);
        //add setupList method here
        setupList();

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //no space should be on the empty string as it can cause the answer to be completely incorrect
                String result = "";
                for (int i = 0; i < Common.user_submit_answer.length; i++)
                    result += String.valueOf(Common.user_submit_answer[i]);
                //if user submit correct answer go to next question
                if (result.equals(correct_answer)) {
                    Toast.makeText(MainActivity.this, "Correct! Good Job!", Toast.LENGTH_SHORT).show();
                    //reset
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    //setAdapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(), getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewQuestionAdapter questionAdapter = new GridViewQuestionAdapter(questionCharacter, getApplicationContext(), MainActivity.this);
                    gridViewQuestion.setAdapter(questionAdapter);
                    questionAdapter.notifyDataSetChanged();


                    setupList();

                } else {
                    Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

        private char[] setupNullList () {
            char result[] = new char[answer.length];
            for (int i = 0; i < answer.length; i++)
                result[i] = ' ';
            return result;
        }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.imgAnimal:
            {
                int animalSoundsId = this.getResources().getIdentifier(correct_answer.toString(),"raw",this.getPackageName());
                player = MediaPlayer.create(this, animalSoundsId);
                player.setLooping(false);
                player.setVolume(1.0f,1.0f);
                player.start();
            }
        }
    }
}
