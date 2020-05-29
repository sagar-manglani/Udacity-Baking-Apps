package com.udacity.udacitybakingapps.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.udacitybakingapps.R;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StepDetailsFragment extends Fragment {
    ArrayList<String> videoURLList;
    ArrayList<String> desc;
    ArrayList<String> step_name;
    TextView tv_desc,tv_name;
    Button prev,next;
    String ingredients_text;
    int current_step=0;
    PlayerView mPlayerView;
    private static String TAG=StepDetailsFragment.class.getSimpleName();
    SimpleExoPlayer mPlayer;
    URL mVideoURL;
    String mUserAgent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment,container,false);
        Bundle arg=getArguments();
        tv_desc=view.findViewById(R.id.step_description);
        tv_name=view.findViewById(R.id.step_name);
        prev=view.findViewById(R.id.prev_button);
        next=view.findViewById(R.id.next_button);
        mPlayerView=view.findViewById(R.id.step_video);
        if(arg!=null){
            videoURLList=arg.getStringArrayList("videoURL");
            desc=arg.getStringArrayList("desc");
            step_name=arg.getStringArrayList("name");
            current_step=arg.getInt("position");
            ingredients_text=arg.getString("ingredients");

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
        if(current_step==0){
            setIngredients_text();
        }else {
            tv_desc.setText(desc.get(current_step-1));
            tv_name.setText(step_name.get(current_step-1));
            playVideo();
        }
        return view;
    }
    void showNextStep(){
        current_step+=1;
        tv_desc.setText(desc.get(current_step-1));
        tv_name.setText(step_name.get(current_step-1));
        playVideo();
        if(current_step==desc.size()){
            next.setVisibility(View.INVISIBLE);
        }
        if(next.getText().equals("Show Steps")){
            next.setText("Next");
            prev.setVisibility(View.VISIBLE);
        }
    }

    void showPrevStep(){
        if(current_step!=1){
            current_step-=1;
            tv_desc.setText(desc.get(current_step-1));
            tv_name.setText(step_name.get(current_step-1));
            playVideo();
        }else {
            setIngredients_text();
            current_step-=1;
        }
        if(next.getVisibility()==View.INVISIBLE)
            next.setVisibility(View.VISIBLE);
    }
    public void setIngredients_text(){
        tv_desc.setText(ingredients_text);
        tv_name.setText("");
        prev.setVisibility(View.INVISIBLE);
        next.setText("Show Steps");
    }

    public void playVideo(){
        try {

            if(videoURLList.get(current_step-1).equals("N/A")){
                mPlayerView.setVisibility(View.GONE);
                if(mPlayer!=null && mPlayer.isPlaying())
                    mPlayer.stop();
                return;
            }
            if(mPlayerView.getVisibility()==View.GONE)
                mPlayerView.setVisibility(View.VISIBLE);

            mVideoURL = new URL(videoURLList.get(current_step-1));
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(getActivity());
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
            mPlayer = new SimpleExoPlayer.Builder(getActivity())
                    .setTrackSelector(trackSelector)
                    .build();
            mPlayerView.setPlayer(mPlayer);
            mUserAgent= Util.getUserAgent(getActivity(),"Baking App");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),mUserAgent);
            MediaSource mediaSource= new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideoURL.toURI().toString()));
            mPlayer.setPlayWhenReady(true);
            mPlayer.seekTo(0, 0);
            mPlayer.prepare(mediaSource, false, false);
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(URISyntaxException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mPlayer!=null && mPlayer.isPlaying())
            mPlayer.stop();
    }
}
