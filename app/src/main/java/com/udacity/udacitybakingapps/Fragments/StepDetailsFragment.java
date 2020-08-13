package com.udacity.udacitybakingapps.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.PassWidgetInformation;
import com.udacity.udacitybakingapps.R;
import com.udacity.udacitybakingapps.Utils.FetchData;

import org.parceler.Parcels;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StepDetailsFragment extends Fragment {
    private ArrayList<String> videoURLList;
    private ArrayList<String> desc;
    private ArrayList<String> step_name;
    private ArrayList<String> imageURL;
    private TextView tv_desc,tv_name;
    private Button prev,next;
    private String ingredients_text;
    private int current_step=-1;
    PlayerView mPlayerView;
    private PassWidgetInformation widget_activity;
    private static String TAG=StepDetailsFragment.class.getSimpleName();
    private static String WHEN_READY_KEY="whenready";
    private static String CURRENT_WINDOW="currentwindow";
    private static String PLAY_BACK_POSITION="playbackposition";
    private static String STEP_POSITION="stepposition";
    private SimpleExoPlayer mPlayer;
    private URL mVideoURL;
    private String mUserAgent;
    private Boolean playWhenReady=true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private Boolean videoPresent=false;
    private Boolean postionSaved=false;
    private Recipe recipe;
    private Context context;
    private TextView no_internet_warning;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.d(TAG,"oncreateView called");
        View view = inflater.inflate(R.layout.step_details_fragment,container,false);
        Bundle arg=getArguments();
        tv_desc=view.findViewById(R.id.step_description);
        tv_name=view.findViewById(R.id.step_name);
        prev=view.findViewById(R.id.prev_button);
        next=view.findViewById(R.id.next_button);
        mPlayerView=view.findViewById(R.id.step_video);
        no_internet_warning=view.findViewById(R.id.no_internet_warning_text);
        if(context.getResources().getBoolean(R.bool.isTablet)){
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        }
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(STEP_POSITION)){
                current_step=savedInstanceState.getInt(STEP_POSITION);
                postionSaved=true;
            }
            if(savedInstanceState.containsKey(PLAY_BACK_POSITION)&&savedInstanceState.containsKey(CURRENT_WINDOW)&&savedInstanceState.containsKey(PLAY_BACK_POSITION)){
                playWhenReady=savedInstanceState.getBoolean(WHEN_READY_KEY);
                currentWindow=savedInstanceState.getInt(CURRENT_WINDOW);
                playbackPosition=savedInstanceState.getLong(PLAY_BACK_POSITION);
            }
        }
        if(arg!=null){
            setData(arg);
        }

        if(context !=null && context.getResources()!=null && !context.getResources().getBoolean(R.bool.isTablet) && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Log.d(TAG,"landscape");
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width=LinearLayout.LayoutParams.MATCH_PARENT;
            params.height=LinearLayout.LayoutParams.MATCH_PARENT;
            mPlayerView.setLayoutParams(params);
        }else {
            //Log.d(TAG,"Vertical");
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width=LinearLayout.LayoutParams.MATCH_PARENT;
            params.height=LinearLayout.LayoutParams.WRAP_CONTENT;
            mPlayerView.setLayoutParams(params);
        }
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrevStep();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextStep();
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        widget_activity=(PassWidgetInformation) getActivity();
        this.context=context;
    }

    public void setData(Bundle arg){
        videoURLList=arg.getStringArrayList(context.getResources().getString(R.string.Steps_Video_URL_key));
        desc=arg.getStringArrayList(context.getResources().getString(R.string.Steps_Desc_key));
        step_name=arg.getStringArrayList(context.getResources().getString(R.string.Steps_Name_key));
        recipe= Parcels.unwrap(arg.getParcelable(context.getResources().getString(R.string.Steps_Recipe_key)));
        imageURL=arg.getStringArrayList(context.getResources().getString(R.string.Steps_Image_URL_key));
        if(!postionSaved) {
            current_step = arg.getInt(context.getResources().getString(R.string.Steps_Positiion_key));
        }else
            postionSaved=false;
        ingredients_text=arg.getString(context.getResources().getString(R.string.Steps_Ingredients_key));

        if(current_step==0){
            setIngredients_text();
        }else {
            tv_desc.setText(desc.get(current_step-1));
            tv_name.setText(step_name.get(current_step-1));
            if(current_step==desc.size())
                next.setVisibility(View.INVISIBLE);

            playVideo();
        }
        //Log.d(TAG,"oncreateView called inside args"+current_step);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION,current_step);
        if(videoPresent){
            outState.putBoolean(WHEN_READY_KEY,playWhenReady);
            outState.putInt(CURRENT_WINDOW,currentWindow);
            outState.putLong(PLAY_BACK_POSITION,playbackPosition);
        }

    }


    void showNextStep(){
        current_step+=1;
        tv_desc.setText(desc.get(current_step-1));
        tv_name.setText(step_name.get(current_step-1));
        playWhenReady=true;
        currentWindow=0;
        playbackPosition=0;
        playVideo();
        if(current_step==desc.size()){
            next.setVisibility(View.INVISIBLE);
        }
        if(next.getText().equals(context.getResources().getString(R.string.Steps_Details_Show_Next_Button_Text))){
            next.setText(context.getResources().getString(R.string.Steps_Details_Next_Button_Text));
            prev.setVisibility(View.VISIBLE);
        }
    }

    void showPrevStep(){
        if(current_step!=1){
            current_step-=1;
            tv_desc.setText(desc.get(current_step-1));
            tv_name.setText(step_name.get(current_step-1));
            playWhenReady=true;
            currentWindow=0;
            playbackPosition=0;
            playVideo();
        }else {
            setIngredients_text();
            current_step-=1;
        }
        if(next.getVisibility()==View.INVISIBLE)
            next.setVisibility(View.VISIBLE);
    }
    private void setIngredients_text(){
        tv_desc.setText(ingredients_text);
        tv_name.setText("");
        prev.setVisibility(View.INVISIBLE);
        next.setText(context.getResources().getString(R.string.Steps_Details_Show_Next_Button_Text));
        mPlayerView.setVisibility(View.GONE);
        no_internet_warning.setVisibility(View.GONE);
        if(mPlayer!=null && mPlayer.isPlaying())
            releasePlayer();

        videoPresent=false;
    }

    private void playVideo(){
        try {
            if(videoURLList.get(current_step-1).equals("N/A") || !FetchData.internet_connection(context)){
                mPlayerView.setVisibility(View.GONE);
                if(!videoURLList.get(current_step-1).equals("N/A")&&!FetchData.internet_connection(context))
                    no_internet_warning.setVisibility(View.VISIBLE);
                else
                    no_internet_warning.setVisibility(View.GONE);
                videoPresent=false;
                if(mPlayer!=null && mPlayer.isPlaying())
                    releasePlayer();
                return;
            }
            if(mPlayerView.getVisibility()==View.GONE){
                mPlayerView.setVisibility(View.VISIBLE);
                no_internet_warning.setVisibility(View.GONE);
            }
            videoPresent=true;
            mVideoURL = new URL(videoURLList.get(current_step-1));

            DefaultTrackSelector trackSelector = new DefaultTrackSelector(context);
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
            mPlayer = new SimpleExoPlayer.Builder(context)
                    .setTrackSelector(trackSelector)
                    .build();
            mPlayerView.setPlayer(mPlayer);
            mUserAgent= Util.getUserAgent(context,"Baking App");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,mUserAgent);
            MediaSource mediaSource= new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideoURL.toURI().toString()));
            mPlayer.setPlayWhenReady(true);
            mPlayer.seekTo(currentWindow, playbackPosition);
            mPlayer.prepare(mediaSource, false, false);
        }catch(MalformedURLException | URISyntaxException ex){
            ex.printStackTrace();
        }
    }

    private void releasePlayer(){
        if (mPlayer != null) {
            playWhenReady = mPlayer.getPlayWhenReady();
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
        //Log.d("widgetupdate","StepDetailsFragmentList "+recipe.getName()+" "+current_step);
        widget_activity.passDataForWidget(recipe,current_step);

    }


}
