package com.texttospeechdemo;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    //region BIND VIEWS
    @BindView(R.id.linear_layout_apple)
    LinearLayout mLinearLayoutApple;

    @BindView(R.id.linear_layout_ball)
    LinearLayout mLinearLayoutBall;

    @BindView(R.id.linear_layout_cat)
    LinearLayout mLinearLayoutCat;

    @BindView(R.id.linear_layout_dog)
    LinearLayout mLinearLayoutDog;

    @BindView(R.id.text_view_apple)
    TextView mTextViewApple;

    @BindView(R.id.text_view_ball)
    TextView mTextViewBall;

    @BindView(R.id.text_view_cat)
    TextView mTextViewCat;

    @BindView(R.id.text_view_dog)
    TextView mTextViewDog;
    //endregion

    //region NEEDED VARIABLE
    TextToSpeech mTextToSpeech;

    Animation animBounce, animRotate,animBlink;
    //endregion

    //region ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTextToSpeech = new TextToSpeech(this, this);

        animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);

        animBlink =  AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        RxView.clicks(mLinearLayoutApple)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        mLinearLayoutApple.startAnimation(animBounce);
                        speakOutApple();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        RxView.clicks(mLinearLayoutBall)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        mLinearLayoutBall.startAnimation(animRotate);
                        speakOutBall();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        RxView.clicks(mLinearLayoutCat)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        mLinearLayoutCat.startAnimation(animBlink);
                        speakOutCat();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        RxView.clicks(mLinearLayoutDog)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        speakOutDog();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    //endregion

    //region TEXT TO SPEECH ON-INIT
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = mTextToSpeech.setLanguage(Locale.US);

            mTextToSpeech.setPitch((float) 0.7);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
    //endregion

    //region SOUND FOR APPLE
    public void speakOutApple() {
        String strApple = mTextViewApple.getText().toString();
        mTextToSpeech.speak(strApple, TextToSpeech.QUEUE_FLUSH, null);
    }
    //endregion

    //region SOUND FOR BALL
    public void speakOutBall() {
        String strBall = mTextViewBall.getText().toString();
        mTextToSpeech.speak(strBall, TextToSpeech.QUEUE_FLUSH, null);
    }
    //endregion

    //region SOUND FOR CAT
    public void speakOutCat() {
        String strCat = mTextViewCat.getText().toString();
        mTextToSpeech.speak(strCat, TextToSpeech.QUEUE_FLUSH, null);
    }
    //endregion

    //region SOUND FOR DOG
    public void speakOutDog() {
        String strDog = mTextViewDog.getText().toString();
        mTextToSpeech.speak(strDog, TextToSpeech.QUEUE_FLUSH, null);
    }
    //endregion

    //region ON DESTROY STOP SOUND
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
    }
    //endregion

}
