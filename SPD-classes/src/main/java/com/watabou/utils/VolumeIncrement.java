package com.watabou.utils;

import java.util.Locale;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class VolumeIncrement {
    public static void incrementVolume(float volume){
        if (volume>1){
            volume /=100;
        }
        if (DeviceCompat.isDesktop()){
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase(Locale.ROOT).contains("windows")){
                try {
                    Mixer.Info[] mixers = AudioSystem.getMixerInfo();
                    for (Mixer.Info mixerInfo : mixers) {
                        Mixer mixer = AudioSystem.getMixer(mixerInfo);
                        Line.Info[] lineInfos = mixer.getTargetLineInfo();
                        for (Line.Info lineInfo : lineInfos) {
                            Line line = null;
                            boolean opened = true;
                            try {
                                line = mixer.getLine(lineInfo);
                                opened = line.isOpen() || line instanceof Clip;
                                if (!opened) {
                                    line.open();
                                }
                                FloatControl volCtrl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                                float currentVolume = volCtrl.getValue();
                                volCtrl.setValue(currentVolume + volume); //Increase volume from 1% to 100% from 0.01 and 1
                            } catch (LineUnavailableException | IllegalArgumentException | NullPointerException ignored) {
                            } finally {
                                if (line != null && !opened) {
                                    line.close();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }  else if (osName.toLowerCase(Locale.ROOT).contains("mac")) {
                try {
                    System.out.println("works");
                    String[] cmd = {"/usr/bin/osascript","-e", "set volume "+volume};
                    Runtime.getRuntime().exec(cmd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    String[] cmd = {"amixer", "-q", "sset", "Master", volume*100+"%"};
                    Runtime.getRuntime().exec(cmd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if (DeviceCompat.isAndroid()){

        } else {
            System.out.println("iOS");
        }
    }
    }

