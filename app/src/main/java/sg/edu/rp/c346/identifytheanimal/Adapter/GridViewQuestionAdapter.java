package sg.edu.rp.c346.identifytheanimal.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;


import java.util.List;

import sg.edu.rp.c346.identifytheanimal.Common.Common;
import sg.edu.rp.c346.identifytheanimal.MainActivity;


public class GridViewQuestionAdapter extends BaseAdapter {

    private List<String> questionCharacter;
    private Context context;
    private MainActivity mainActivity;

    public GridViewQuestionAdapter(List<String> questionCharacter, Context context, MainActivity mainActivity) {
        this.questionCharacter = questionCharacter;
        this.context = context;
        this.mainActivity = mainActivity;
    }


    @Override
    public int getCount() {
        return questionCharacter.size();
    }

    @Override
    public Object getItem(int i) {
        return questionCharacter.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Button button;
        if (view == null) {
            if (questionCharacter.get(i).equals("null")) {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(130, 130));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);

            } else {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(130, 130));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);
                button.setTextColor(Color.YELLOW);
                button.setText((questionCharacter.get(i)));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if correct answer contains letter
                        if (String.valueOf(mainActivity.answer).contains(questionCharacter.get(i))) {
                            char compare = questionCharacter.get(i).charAt(0);
                            for (int i = 0; i < mainActivity.answer.length; i++) {
                                if (compare == mainActivity.answer[i])
                                    Common.user_submit_answer[i] = compare;
                            }
                            //update UI
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
                            mainActivity.gridViewAnswer.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();


                            //remove
                            mainActivity.questionCharacter.set(i, "null");
                            mainActivity.questionAdapter = new GridViewQuestionAdapter(mainActivity.questionCharacter, context, mainActivity);
                            mainActivity.gridViewQuestion.setAdapter(mainActivity.questionAdapter);
                            mainActivity.questionAdapter.notifyDataSetChanged();


                        } else {
                            //remove
                            mainActivity.questionCharacter.set(i, "null");
                            mainActivity.questionAdapter = new GridViewQuestionAdapter(mainActivity.questionCharacter, context, mainActivity);
                            mainActivity.gridViewQuestion.setAdapter(mainActivity.questionAdapter);
                            mainActivity.questionAdapter.notifyDataSetChanged();
                        }
                    }

                });
            }
        }
        else
            button = (Button)view;
        return button;
    }
}