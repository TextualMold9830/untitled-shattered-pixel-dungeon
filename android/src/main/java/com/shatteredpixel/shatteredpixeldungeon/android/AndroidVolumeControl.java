package com.shatteredpixel.shatteredpixeldungeon.android;

import android.content.Context;
import android.media.AudioManager;

import com.watabou.utils.VolumeController;

public class AndroidVolumeControl implements VolumeController {
    private Context context;

    public AndroidVolumeControl(Context context) {
        this.context = context;
    }

    @Override
    public void setVolume(float volume) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = volume;
        int Volume = (int) (maxVolume*percent);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, Volume, 0);
    }
}

