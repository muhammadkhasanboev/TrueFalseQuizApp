package android.bignerdranch.truefalseapppractise;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int mTextResId, boolean mAnswerTrue){
        this.mTextResId = mTextResId;
        this.mAnswerTrue = mAnswerTrue;
    }

    public int getTextResId(){
        return mTextResId;
    }
    public void setTextResId(int TextResId){
        mTextResId = TextResId;
    }
    public boolean ismAnswerTrue(){
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue){
        mAnswerTrue = answerTrue;
    }

}
// getText, setText, getAnswer, setAnswer