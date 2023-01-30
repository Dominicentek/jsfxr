package com.jsfxr;

import javax.sound.sampled.*;
import java.awt.FileDialog;
import java.io.ByteArrayInputStream;
import java.util.Random;

public class Application {
    public static Category[] categories = new Category[]{
        new Category(),
        new Category(),
        new Category(),
        new Category(),
        new Category(),
        new Category(),
        new Category(),
        new Category(),
        new Category(),
        new Category()
    };
    public static int wave_type;
    public static float p_base_freq;
    public static float p_freq_limit;
    public static float p_freq_ramp;
    public static float p_freq_dramp;
    public static float p_duty;
    public static float p_duty_ramp;
    public static float p_vib_strength;
    public static float p_vib_speed;
    public static float p_vib_delay;
    public static float p_env_attack;
    public static float p_env_sustain;
    public static float p_env_decay;
    public static float p_env_punch;
    public static boolean filter_on;
    public static float p_lpf_resonance;
    public static float p_lpf_freq;
    public static float p_lpf_ramp;
    public static float p_hpf_freq;
    public static float p_hpf_ramp;
    public static float p_pha_offset;
    public static float p_pha_ramp;
    public static float p_repeat_speed;
    public static float p_arp_speed;
    public static float p_arp_mod;
    public static float master_vol = 0.05f;
    public static float sound_vol = 0.5f;
    public static boolean playing_sample = false;
    public static int phase;
    public static double fperiod;
    public static double fmaxperiod;
    public static double fslide;
    public static double fdslide;
    public static int period;
    public static float square_duty;
    public static float square_slide;
    public static int env_stage;
    public static int env_time;
    public static int[] env_length = new int[3];
    public static float env_vol;
    public static float fphase;
    public static float fdphase;
    public static int iphase;
    public static float[] phaser_buffer = new float[1024];
    public static int ipp;
    public static float[] noise_buffer = new float[32];
    public static float fltp;
    public static float fltdp;
    public static float fltw;
    public static float fltw_d;
    public static float fltdmp;
    public static float fltphp;
    public static float flthp;
    public static float flthp_d;
    public static float vib_phase;
    public static float vib_speed;
    public static float vib_amp;
    public static int rep_time;
    public static int rep_limit;
    public static int arp_time;
    public static int arp_limit;
    public static double arp_mod;
    public static FieldValue vselected = null;
    public static int vcurbutton = -1;
    public static int wav_bits = 16;
    public static int wav_freq = 44100;
    public static int file_sampleswritten;
    public static float filesample = 0.0f;
    public static int fileacc = 0;
    public static boolean mute_stream;
    public static boolean firstframe = true;
    public static int refresh_counter = 0;
    public static Tools.Spriteset font;
    public static Tools.Spriteset ld48;
    public static int drawcount = 0;
    public static int rnd(int n) {
        return new Random().nextInt(n + 1);
    }
    public static float frnd(float range) {
        return rnd(10000) / 10000f * range;
    }
    public static class Category {
        public String name;
    }
    public static void resetParams() {
        wave_type = 0;
        p_base_freq = 0.3f;
        p_freq_limit = 0.0f;
        p_freq_ramp = 0.0f;
        p_freq_dramp = 0.0f;
        p_duty = 0.0f;
        p_duty_ramp = 0.0f;
        p_vib_strength = 0.0f;
        p_vib_speed = 0.0f;
        p_vib_delay = 0.0f;
        p_env_attack = 0.0f;
        p_env_sustain = 0.3f;
        p_env_decay = 0.4f;
        p_env_punch = 0.0f;
        filter_on = false;
        p_lpf_resonance = 0.0f;
        p_lpf_freq = 1.0f;
        p_lpf_ramp = 0.0f;
        p_hpf_freq = 0.0f;
        p_hpf_ramp = 0.0f;
        p_pha_offset = 0.0f;
        p_pha_ramp = 0.0f;
        p_repeat_speed = 0.0f;
        p_arp_speed = 0.0f;
        p_arp_mod = 0.0f;
    }
    public static void loadSettings(String filename) {
        try {
            ByteArray file = new ByteArray(filename);
            int version = file.readInt();
            if (version != 100 && version != 101 && version != 102) {
                return;
            }
            wave_type = file.readInt();
            sound_vol = 0.5f;
            if (version == 102) {
                sound_vol = file.readFloat();
            }
            p_base_freq = file.readFloat();
            p_freq_limit = file.readFloat();
            p_freq_ramp = file.readFloat();
            if (version >= 101) {
                p_freq_dramp = file.readFloat();
            }
            p_duty = file.readFloat();
            p_duty_ramp = file.readFloat();
            p_vib_strength = file.readFloat();
            p_vib_speed = file.readFloat();
            p_vib_delay = file.readFloat();
            p_env_attack = file.readFloat();
            p_env_sustain = file.readFloat();
            p_env_decay = file.readFloat();
            p_env_punch = file.readFloat();
            filter_on = file.readBoolean();
            p_lpf_resonance = file.readFloat();
            p_lpf_freq = file.readFloat();
            p_lpf_ramp = file.readFloat();
            p_hpf_freq = file.readFloat();
            p_hpf_ramp = file.readFloat();
            p_pha_offset = file.readFloat();
            p_pha_ramp = file.readFloat();
            p_repeat_speed = file.readFloat();
            if (version >= 101) {
                p_arp_speed = file.readFloat();
                p_arp_mod = file.readFloat();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveSettings(String filename) {
        try {
            ByteArray file = new ByteArray(105, filename);
            file.writeInt(102);
            file.writeInt(wave_type);
            file.writeFloat(sound_vol);
            file.writeFloat(p_base_freq);
            file.writeFloat(p_freq_limit);
            file.writeFloat(p_freq_ramp);
            file.writeFloat(p_freq_dramp);
            file.writeFloat(p_duty);
            file.writeFloat(p_duty_ramp);
            file.writeFloat(p_vib_strength);
            file.writeFloat(p_vib_speed);
            file.writeFloat(p_vib_delay);
            file.writeFloat(p_env_attack);
            file.writeFloat(p_env_sustain);
            file.writeFloat(p_env_decay);
            file.writeFloat(p_env_punch);
            file.writeBoolean(filter_on);
            file.writeFloat(p_lpf_resonance);
            file.writeFloat(p_lpf_freq);
            file.writeFloat(p_lpf_ramp);
            file.writeFloat(p_hpf_freq);
            file.writeFloat(p_hpf_ramp);
            file.writeFloat(p_pha_offset);
            file.writeFloat(p_pha_ramp);
            file.writeFloat(p_repeat_speed);
            file.writeFloat(p_arp_speed);
            file.writeFloat(p_arp_mod);
            file.writeAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void resetSample(boolean restart) {
        if (!restart) phase = 0;
        fperiod = 100.0 / (p_base_freq * p_base_freq + 0.001);
        period = (int)fperiod;
        fmaxperiod = 100.0 / (p_freq_limit * p_freq_limit + 0.001);
        fslide = 1.0 - Math.pow(p_freq_ramp, 3) * 0.01;
        fdslide = -Math.pow(p_freq_dramp, 3) * 0.000001;
        square_duty = 0.5f - p_duty * 0.5f;
        square_slide = -p_duty_ramp * 0.00005f;
        if (p_arp_mod >= 0.0f) arp_mod = 1.0 - Math.pow(p_arp_mod, 2.0) * 0.9;
        else arp_mod = 1.0 + Math.pow(p_arp_mod, 2.0) * 10.0;
        arp_time = 0;
        arp_limit = (int)(Math.pow(1.0f - p_arp_speed, 2.0f) * 20000 + 32);
        if (p_arp_speed == 1.0f) arp_limit = 0;
        if (!restart) {
            fltp = 0.0f;
            fltdp = 0.0f;
            fltw = (float)Math.pow(p_lpf_freq, 3.0f) * 0.1f;
            fltw_d = 1.0f + p_lpf_ramp * 0.0001f;
            fltdmp = (float)(5.0f / (1.0f + Math.pow(p_lpf_resonance, 2.0f) * 20.0f) * (0.01f + fltw));
            if (fltdmp > 0.8f) fltdmp = 0.8f;
            fltphp = 0.0f;
            flthp = (float)Math.pow(p_hpf_freq, 2.0f) * 0.1f;
            flthp_d = (float)(1.0 + p_hpf_ramp * 0.0003f);
            vib_phase = 0.0f;
            vib_speed = (float)Math.pow(p_vib_speed, 2.0f) * 0.01f;
            vib_amp = p_vib_strength * 0.5f;
            env_vol = 0.0f;
            env_stage = 0;
            env_time = 0;
            env_length[0] = (int)(p_env_attack * p_env_attack * 100000.0f);
            env_length[1] = (int)(p_env_sustain * p_env_sustain * 100000.0f);
            env_length[2] = (int)(p_env_decay * p_env_decay * 100000.0f);
            fphase = (float)Math.pow(p_pha_offset, 2.0f) * 1020.0f;
            if (p_pha_offset < 0.0f) fphase = -fphase;
            fdphase = (float)Math.pow(p_pha_ramp, 2.0f);
            if (p_pha_ramp < 0.0f) fdphase = -fdphase;
            iphase = Math.abs((int)fphase);
            ipp = 0;
            for (int i = 0; i < 1024; i++) {
                phaser_buffer[i] = 0.0f;
            }
            for (int i = 0; i < 32; i++) {
                noise_buffer[i] = frnd(2.0f) - 1.0f;
            }
            rep_time = 0;
            rep_limit = (int)(Math.pow(1.0f - p_repeat_speed, 2.0f) * 20000 + 32);
            if (p_repeat_speed == 0.0f) rep_limit = 0;
        }
    }
    public static void playSample() {
        resetSample(false);
        playing_sample = true;
        if (!mute_stream) {
            new Thread(() -> {
                ByteArray wav = new ByteArray(1024 * 1024);
                writeWAV(wav);
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(wav.arrayBeforePointer())));
                    clip.start();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public static void synthSample(int length, ByteArray array) {
        for (int i = 0; i < length; i++) {
            if (!playing_sample) break;
            rep_time++;
            if (rep_limit != 0 && rep_time >= rep_limit) {
                rep_time = 0;
                resetSample(true);
            }
            arp_time++;
            if (arp_limit != 0 && arp_time >= arp_limit) {
                arp_limit = 0;
                fperiod *= arp_mod;
            }
            fslide += fdslide;
            fperiod *= fslide;
            if (fperiod > fmaxperiod) {
                fperiod = fmaxperiod;
                if (p_freq_limit > 0.0f) playing_sample = false;
            }
            float rfperiod = (float)fperiod;
            if (vib_amp > 0.0f) {
                vib_phase += vib_speed;
                rfperiod = (float)(fperiod * (1.0 + Math.sin(vib_phase) * vib_amp));
            }
            period = (int)rfperiod;
            if (period < 8) period = 8;
            square_duty += square_slide;
            if (square_duty < 0.0f) square_duty = 0.0f;
            if (square_duty > 0.5f) square_duty = 0.5f;
            env_time++;
            if (env_time > env_length[env_stage]) {
                env_time = 0;
                env_stage++;
                if (env_stage == 3) playing_sample = false;
            }
            if (env_stage == 0) env_vol = (float)env_time / env_length[0];
            if (env_stage == 1) env_vol = (float)(1.0f + Math.pow(1.0f - (float)env_time / env_length[1], 1.0f) * 2.0f * p_env_punch);
            if (env_stage == 2) env_vol = 1.0f - (float)env_time / env_length[2];
            fphase += fdphase;
            iphase = Math.abs((int)fphase);
            if (iphase > 1023) iphase = 1023;
            if (flthp_d != 0.0f) {
                flthp *= flthp_d;
                if (flthp < 0.00001f) flthp = 0.00001f;
                if (flthp > 0.1f) flthp = 0.1f;
            }
            float ssample = 0.0f;
            for (int si = 0; si < 8; si++) {
                float sample = 0.0f;
                phase++;
                if (phase >= period) {
                    phase %= period;
                    if (wave_type == 3) {
                        for (int j = 0; j < 32; j++) {
                            noise_buffer[j] = frnd(2.0f) - 1.0f;
                        }
                    }
                }
                float fp = (float)phase / period;
                switch (wave_type) {
                    case 0:
                        if (fp < square_duty)
                            sample = 0.5f;
                        else
                            sample = -0.5f;
                        break;
                    case 1:
                        sample = 1.0f - fp * 2;
                        break;
                    case 2:
                        sample = (float)Math.sin(fp * 2 * Math.PI);
                        break;
                    case 3:
                        sample = noise_buffer[phase * 32 / period];
                        break;
                    case 4:
                        if (fp <= 0.5) sample = -1 + fp * 4;
                        else sample = 3 - fp * 4;
                        break;
                }
                float pp = fltp;
                fltw *= fltw_d;
                if (fltw < 0.0f) fltw = 0.0f;
                if (fltw > 0.1f) fltw = 0.1f;
                if (p_lpf_freq != 1.0f) {
                    fltdp += (sample - fltp) * fltw;
                    fltdp -= fltdp * fltdmp;
                }
                else {
                    fltp = sample;
                    fltdp = 0.0f;
                }
                fltp += fltdp;
                fltphp += fltp - pp;
                fltphp -= fltphp * flthp;
                sample = fltphp;
                phaser_buffer[ipp & 1023] = sample;
                sample += phaser_buffer[(ipp - iphase + 1024) & 1023];
                ipp = (ipp + 1) & 1023;
                ssample += sample * env_vol;
            }
            ssample = ssample / 8 * master_vol;
            ssample *= 2.0f * sound_vol;
            ssample *= 4.0f;
            if (ssample > 1.0f) ssample = 1.0f;
            if (ssample < -1.0f) ssample = -1.0f;
            filesample += ssample;
            fileacc++;
            if (wav_freq == 44100 || fileacc == 2) {
                filesample /= fileacc;
                fileacc = 0;
                if (wav_bits == 16) {
                    short isample = (short)(filesample * 32000);
                    if (array != null) array.writeShort(isample);
                }
                else {
                    int isample = (int)(filesample * 127 + 128);
                    if (array != null) array.writeByte(isample);
                }
                filesample = 0.0f;
            }
            file_sampleswritten++;
        }
    }
    public static boolean exportWAV(String filename) {
        try {
            ByteArray file = new ByteArray(1024 * 1024, filename);
            writeWAV(file);
            file.writeBeforePointer();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void writeWAV(ByteArray array) {
        array.writeString("RIFF");
        array.writeInt(0);
        array.writeString("WAVE");
        array.writeString("fmt ");
        array.writeInt(16);
        array.writeShort(1);
        array.writeShort(1);
        array.writeInt(wav_freq);
        array.writeInt(wav_freq * wav_bits / 8);
        array.writeShort(wav_bits / 8);
        array.writeShort(wav_bits);
        array.writeString("data");
        int foutstream_datasize = array.pointer();
        array.writeInt(0);
        mute_stream = true;
        file_sampleswritten = 0;
        filesample = 0.0f;
        fileacc = 0;
        playSample();
        while (playing_sample) {
            synthSample(256, array);
        }
        mute_stream = false;
        int eof = array.pointer();
        array.pointer(4);
        array.writeInt(foutstream_datasize - 4 + file_sampleswritten * wav_bits / 8);
        array.pointer(foutstream_datasize);
        array.writeInt(file_sampleswritten * wav_bits / 8);
        array.pointer(eof);
    }
    public static boolean slider(int x, int y, FieldValue value, Object defaultValue, boolean bipolar, String text) {
        boolean result = false;
        if (Tools.mouseInBox(x, y, 100, 10)) {
            if (Main.mouse_rightclick) {
                value.set(defaultValue);
            }
            if (Main.mouse_leftclick) {
                vselected = value;
            }
        }
        if (vselected != null) {
            float val = bipolar ? (Main.mouse_x - x) / 50f - 1f : (Main.mouse_x - x) / 100f;
            if (!value.getFieldName().equals(vselected.getFieldName())) val = value.<Float>get();
            if (bipolar) {
                value.set(val);
                if (value.<Float>get() < -1.0f) value.set(-1.0f);
            }
            else {
                value.set(val);
                if (value.<Float>get() < 0.0f) value.set(0.0f);
            }
            if (value.<Float>get() > 1.0f) value.set(1.0f);
        }
        Tools.drawBar(x - 1, y, 102, 10, 0x000000);
        float minVal = 0;
        int ival = (int)(value.<Float>get() * 99);
        if (bipolar) {
            minVal = -1;
            ival = (int)(value.<Float>get() * 49.5f + 49.5f);
        }
        if (value.<Float>get() < minVal) value.set(minVal);
        if (value.<Float>get() > 1) value.set(1);
        Tools.drawBar(x, y + 1, ival, 8, 0xF0C090);
        Tools.drawBar(x + ival, y + 1, 100 - ival, 8, 0x807060);
        Tools.drawBar(x + ival, y + 1, 1, 8, 0xFFFFFF);
        if (bipolar) {
            Tools.drawBar(x + 50, y - 1, 1, 3, 0x000000);
            Tools.drawBar(x + 50, y + 8, 1, 3, 0x000000);
        }
        int tcol = 0x000000;
        if (wave_type != 0 && (value.getFieldName().equals("p_duty") || value.getFieldName().equals("p_duty_ramp"))) tcol = 0x808080;
        Tools.drawText(font, x - 4 - text.length() * 8, y + 1, tcol, text);
        return result;
    }
    public static boolean button(int x, int y, boolean highlight, String text, int id) {
        return buttonWH(x, y, 100, 17, highlight, text, id);
    }
    public static boolean buttonWH(int x, int y, int w, int h, boolean highlight, String text, int id) {
        int color1 = 0x000000;
        int color2 = 0xA09088;
        int color3 = 0x000000;
        boolean hover = Tools.mouseInBox(x, y, w, h);
        if (hover && Main.mouse_leftclick) {
            vcurbutton = id;
        }
        boolean current = vcurbutton == id;
        if (highlight) {
            color1 = 0x000000;
            color2 = 0x988070;
            color3 = 0xFFF0E0;
        }
        if (current && hover) {
            color1 = 0xA09088;
            color2 = 0xFFF0E0;
            color3 = 0xA09088;
        }
        Tools.drawBar(x - 1, y - 1, w + 2, h + 2, color1);
        Tools.drawBar(x, y, w, h, color2);
        Tools.drawText(font, x + 5, y + 5, color3, text);
        return current && hover && !Main.mouse_left;
    }
    public static void drawScreen() {
        boolean redraw = true;
        if (!firstframe && Main.mouse_x - Main.mouse_px == 0 && Main.mouse_y - Main.mouse_py == 0 && !Main.mouse_left && !Main.mouse_right) redraw = false;
        if (!Main.mouse_left) {
            if (vselected != null || vcurbutton > -1) {
                redraw = true;
                refresh_counter = 2;
            }
            vselected = null;
        }
        if (refresh_counter > 0) {
            refresh_counter--;
            redraw = true;
        }
        if (playing_sample) redraw = true;
        if (drawcount++ > 20) {
            redraw = true;
            drawcount = 0;
        }
        if (!redraw) return;
        firstframe = false;
        Tools.clearScreen(0xC0B090);
        Tools.drawText(font, 10, 10, 0x504030, "GENERATOR");
        for (int i = 0; i < 7; i++) {
            if (button(5, 35 + i * 30, false, categories[i].name, 300 + i)) {
                switch (i) {
                    case 0:
                        resetParams();
                        p_base_freq = 0.5f + frnd(0.5f);
                        p_env_attack = 0.0f;
                        p_env_sustain = frnd(0.1f);
                        p_env_decay = 0.1f + frnd(0.4f);
                        p_env_punch = 0.3f + frnd(0.3f);
                        if (rnd(1) == 0) {
                            p_arp_speed = 0.5f + frnd(0.2f);
                            p_arp_mod = 0.2f + frnd(0.4f);
                        }
                        break;
                    case 1:
                        resetParams();
                        wave_type = rnd(2);
                        if (wave_type == 2 && rnd(1) == 0) wave_type = rnd(1);
                        p_base_freq = 0.5f + frnd(0.5f);
                        p_freq_limit = p_base_freq - 0.2f - frnd(0.6f);
                        if (p_freq_limit < 0.2f) p_freq_limit = 0.2f;
                        p_freq_ramp = -0.15f - frnd(0.2f);
                        if (rnd(2) == 0) {
                            p_base_freq = 0.3f + frnd(0.6f);
                            p_freq_limit = frnd(0.1f);
                            p_freq_ramp = -0.35f - frnd(0.3f);
                        }
                        if (rnd(1) == 0) {
                            p_duty = frnd(0.5f);
                            p_duty_ramp = frnd(0.2f);
                        }
                        else {
                            p_duty = 0.4f + frnd(0.5f);
                            p_duty_ramp = -frnd(0.7f);
                        }
                        p_env_attack = 0.0f;
                        p_env_sustain = 0.1f + frnd(0.2f);
                        p_env_decay = frnd(0.4f);
                        if (rnd(1) == 0) {
                            p_env_punch = frnd(0.3f);
                        }
                        if (rnd(2) == 0) {
                            p_pha_offset = frnd(0.2f);
                            p_pha_ramp = -frnd(0.2f);
                        }
                        if (rnd(1) == 0) {
                            p_hpf_freq = frnd(0.3f);
                        }
                        break;
                    case 2:
                        resetParams();
                        wave_type = 3;
                        if (rnd(1) == 0) {
                            p_base_freq = 0.1f + frnd(0.4f);
                            p_freq_ramp = 0.1f + frnd(0.4f);
                        }
                        else {
                            p_base_freq = 0.2f + frnd(0.7f);
                            p_freq_ramp = 0.2f - frnd(0.2f);
                        }
                        p_base_freq *= p_base_freq;
                        if (rnd(4) == 0) {
                            p_freq_ramp = 0.0f;
                        }
                        if (rnd(2) == 0) {
                            p_repeat_speed = 0.3f + frnd(0.5f);
                        }
                        p_env_attack = 0.0f;
                        p_env_sustain = 0.1f + frnd(0.3f);
                        p_env_decay = frnd(0.5f);
                        if (rnd(1) == 0) {
                            p_pha_offset = -0.3f + frnd(0.9f);
                            p_pha_ramp = -frnd(0.3f);
                        }
                        p_env_punch = 0.2f + frnd(0.6f);
                        if (rnd(1) == 0) {
                            p_vib_strength = frnd(0.7f);
                            p_vib_speed = frnd(0.6f);
                        }
                        if (rnd(2) == 0) {
                            p_arp_speed = 0.6f + frnd(0.3f);
                            p_arp_mod = 0.8f - frnd(1.6f);
                        }
                        break;
                    case 3:
                        resetParams();
                        if (rnd(1) == 0) wave_type = 1;
                        else p_duty = frnd(0.6f);
                        if (rnd(1) == 0) {
                            p_base_freq = 0.2f + frnd(0.3f);
                            p_freq_ramp = 0.1f + frnd(0.4f);
                            p_repeat_speed = 0.4f + frnd(0.4f);
                        }
                        else {
                            p_base_freq = 0.2f + frnd(0.3f);
                            p_freq_ramp = 0.05f + frnd(0.2f);
                            if (rnd(1) == 0) {
                                p_vib_strength = frnd(0.7f);
                                p_vib_speed = frnd(0.6f);
                            }
                        }
                        p_env_attack = 0.0f;
                        p_env_sustain = frnd(0.4f);
                        p_env_decay = 0.1f + frnd(0.4f);
                        break;
                    case 4:
                        wave_type = rnd(2);
                        if (wave_type == 2) wave_type = 3;
                        if (wave_type == 0) p_duty = frnd(0.6f);
                        p_base_freq = 0.2f + frnd(0.6f);
                        p_freq_ramp = -0.3f - frnd(0.4f);
                        p_env_attack = 0.0f;
                        p_env_sustain = frnd(0.1f);
                        p_env_decay = 0.1f + frnd(0.2f);
                        if (rnd(1) == 0) p_hpf_freq = frnd(0.3f);
                    case 5:
                        resetParams();
                        wave_type = 0;
                        p_duty = frnd(0.6f);
                        p_base_freq = 0.3f + frnd(0.3f);
                        p_freq_ramp = 0.1f + frnd(0.2f);
                        p_env_attack = 0.0f;
                        p_env_sustain = 0.1f + frnd(0.3f);
                        p_env_decay = 0.1f + frnd(0.2f);
                        if (rnd(1) == 0) p_hpf_freq = frnd(0.3f);
                        if (rnd(1) == 0) p_lpf_freq = 1.0f - frnd(0.6f);
                        break;
                    case 6:
                        resetParams();
                        wave_type = rnd(1);
                        if (wave_type == 0) p_duty = frnd(0.6f);
                        p_base_freq = 0.2f + frnd(0.4f);
                        p_env_attack = 0.0f;
                        p_env_sustain = 0.1f + frnd(0.1f);
                        p_env_decay = frnd(0.2f);
                        p_hpf_freq = 0.1f;
                        break;
                    default:
                        break;
                }
                playSample();
            }
        }
        Tools.drawBar(110, 0, 2, 480, 0x000000);
        Tools.drawText(font, 120, 10, 0x504030, "MANUAL SETTINGS");
        Tools.drawSprite(ld48, 8, 440, 0, 0xB0A080);
        boolean do_play = false;
        if (buttonWH(130, 30, 78, 19, wave_type == 0, "SQUARE", 10)) {
            wave_type = 0;
            do_play = true;
        }
        if (buttonWH(226, 30, 78, 19, wave_type == 1, "SAWTOOTH", 11)) {
            wave_type = 1;
            do_play = true;
        }
        if (buttonWH(322, 30, 78, 19, wave_type == 2, "SINE", 12)) {
            wave_type = 2;
            do_play = true;
        }
        if (buttonWH(418, 30, 78, 19, wave_type == 3, "NOISE", 13)) {
            wave_type = 3;
            do_play = true;
        }
        if (buttonWH(514, 30, 78, 19, wave_type == 4, "TRIANGLE", 13)) {
            wave_type = 4;
            do_play = true;
        }
        Tools.drawBar(5 - 1 - 1, 412 - 1 - 1, 102 + 2, 19 + 2, 0x000000);
        if (button(5, 412, false, "RANDOMIZE", 40)) {
            p_base_freq = (float)Math.pow(frnd(2.0f) - 1.0f, 2f);
            if (rnd(1) == 0) p_base_freq = (float)Math.pow(frnd(2.0f) - 1.0f, 3.0f) + 0.5f;
            p_freq_limit = 0.0f;
            p_freq_ramp = (float)Math.pow(frnd(2.0f) - 1.0f, 5.0f);
            if (p_base_freq > 0.7f && p_freq_ramp > 0.2f) p_freq_ramp = -p_freq_ramp;
            if (p_base_freq < 0.2f && p_freq_ramp < -0.05f) p_freq_ramp = -p_freq_ramp;
            p_freq_dramp = (float)Math.pow(frnd(2.0f) - 1.0f, 3.0f);
            p_duty = frnd(2.0f) - 1.0f;
            p_duty_ramp = (float)Math.pow(frnd(2.0f) - 1.0f, 3.0f);
            p_vib_strength = (float)Math.pow(frnd(2.0f) - 1.0f, 3.0f);
            p_vib_speed = frnd(2.0f)-1.0f;
            p_vib_delay = frnd(2.0f) - 1.0f;
            p_env_attack = (float)Math.pow(frnd(2.0f) - 1.0f, 3.0f);
            p_env_sustain = (float)Math.pow(frnd(2.0f) - 1.0f, 2.0f);
            p_env_decay = frnd(2.0f) - 1.0f;
            p_env_punch = (float)Math.pow(frnd(0.8f), 2.0f);
            if (p_env_attack+p_env_sustain+p_env_decay < 0.2f) {
                p_env_sustain += 0.2f + frnd(0.3f);
                p_env_decay += 0.2f + frnd(0.3f);
            }
            p_lpf_resonance = frnd(2.0f) - 1.0f;
            p_lpf_freq = 1.0f -(float)Math.pow(frnd(1.0f), 3.0f);
            p_lpf_ramp = (float)Math.pow(frnd(2.0f)-1.0f, 3.0f);
            if (p_lpf_freq < 0.1f && p_lpf_ramp < -0.05f) p_lpf_ramp = -p_lpf_ramp;
            p_hpf_freq = (float)Math.pow(frnd(1.0f), 5.0f);
            p_hpf_ramp = (float)Math.pow(frnd(2.0f) - 1.0f, 5.0f);
            p_pha_offset=(float)Math.pow(frnd(2.0f) - 1.0f, 3.0f);
            p_pha_ramp = (float)Math.pow(frnd(2.0f) - 1.0f, 3.0f);
            p_repeat_speed = frnd(2.0f) - 1.0f;
            p_arp_speed = frnd(2.0f) - 1.0f;
            p_arp_mod = frnd(2.0f) - 1.0f;
            wave_type = rnd(4);
            do_play=true;
        }
        if (button(5, 382, false, "MUTATE", 30)) {
            if (rnd(1) == 0) p_base_freq += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_freq_ramp += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_freq_dramp += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_duty += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_duty_ramp += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_vib_strength += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_vib_speed += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_vib_delay += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_env_attack += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_env_sustain += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_env_decay += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_env_punch += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_lpf_resonance += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_lpf_freq += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_lpf_ramp += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_hpf_freq += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_hpf_ramp += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_pha_offset += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_pha_ramp += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_repeat_speed += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_arp_speed += frnd(0.1f) - 0.05f;
            if (rnd(1) == 0) p_arp_mod += frnd(0.1f) - 0.05f;
            do_play=true;
        }
        if (button(5, 382 - 412 + 382, false, "DEFAULT", 50)) {
            resetParams();
            do_play=true;
        }
        Tools.drawText(font, 515, 170, 0x000000, "VOLUME");
        Tools.drawBar(490 - 1 - 1 + 60, 180 - 1 + 5, 70, 2, 0x000000);
        Tools.drawBar(490 - 1 - 1 + 60 + 68, 180 - 1 + 5, 2, 205, 0x000000);
        Tools.drawBar(490 - 1 - 1 + 60, 180 - 1, 42 + 2, 10 + 2, 0xFF0000);
        if (slider(490, 180, new FieldValue(Application.class, "sound_vol"), 0.5f, false, " ")) playSample();
        if (button(490, 200, false, "PLAY SOUND", 20)) playSample();
        if (button(490, 290, false, "LOAD SOUND", 14)) {
            FileDialog dialog = new FileDialog(Main.window);
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            if (dialog.getFiles().length > 0) {
                resetParams();
                loadSettings(dialog.getFiles()[0].getAbsolutePath());
                playSample();
            }
        }
        if (button(490, 320, false, "SAVE SOUND", 14)) {
            FileDialog dialog = new FileDialog(Main.window);
            dialog.setMode(FileDialog.SAVE);
            dialog.setVisible(true);
            if (dialog.getFiles().length > 0) {
                saveSettings(dialog.getFiles()[0].getAbsolutePath());
            }
        }
        Tools.drawBar(490 - 1 - 1 + 60, 380 - 1 + 9, 70, 2, 0x000000);
        Tools.drawBar(490 - 1 - 2, 380 - 1 - 2, 102 + 4, 19 + 4, 0x000000);
        if (button(490, 380, false, "EXPORT .WAV", 16)) {
            FileDialog dialog = new FileDialog(Main.window);
            dialog.setMode(FileDialog.SAVE);
            dialog.setVisible(true);
            if (dialog.getFiles().length > 0) {
                exportWAV(dialog.getFiles()[0].getAbsolutePath());
            }
        }
        if (button(490, 410, false, wav_freq + " HZ", 18)) {
            if (wav_freq == 44100) wav_freq = 22050;
            else wav_freq = 44100;
        }
        if (button(490, 440, false, wav_bits + "-BIT", 19)) {
            if (wav_bits == 16) wav_bits = 8;
            else wav_bits = 16;
        }
        int xpos = 350;
        int ypos = 4;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_env_attack"), 0.0f, false, "ATTACK TIME")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_env_sustain"), 0.3f, false, "SUSTAIN TIME")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_env_punch"), 0.0f, false, "SUSTAIN PUNCH")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_env_decay"), 0.4f, false, "DECAY TIME")) do_play = true;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_base_freq"), 0.3f, false, "START FREQUENCY")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_freq_limit"), 0.0f, false, "MIN FREQUENCY")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_freq_ramp"), 0.0f, true, "SLIDE")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_freq_dramp"), 0.0f, true, "DELTA SLIDE")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_vib_strength"), 0.0f, false, "VIBRATO DEPTH")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_vib_speed"), 0.0f, false, "VIBRATO SPEED")) do_play = true;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_arp_mod"), 0.0f, true, "CHARGE AMOUNT")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_arp_speed"), 0.0f, false, "CHARGE SPEED")) do_play = true;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_duty"), 0.0f, false, "SQUARE DUTY")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_duty_ramp"), 0.0f, true, "DUTY SWEEP")) do_play = true;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_repeat_speed"), 0.0f, false, "REPEAT SPEED")) do_play = true;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_pha_offset"), 0.0f, true, "PHASER OFFSET")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_pha_ramp"), 0.0f, true, "PHASER SWEEP")) do_play = true;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_lpf_freq"), 1.0f, false, "LP FILTER CUTOFF")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_lpf_ramp"), 0.0f, true, "LP FILTER CUTOFF SWEEP")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_lpf_resonance"), 0.0f, false, "LP FILTER RESONANCE")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_hpf_freq"), 0.0f, false, "HP FILTER CUTOFF")) do_play = true;
        if (slider(xpos, (ypos++) * 18, new FieldValue(Application.class, "p_hpf_ramp"), 0.0f, true, "HP FILTER CUTOFF SWEEP")) do_play = true;
        Tools.drawBar(xpos - 190, ypos * 18 - 5, 300, 2, 0x000000);
        Tools.drawBar(xpos - 190, 4 * 18 - 5, 1, (ypos - 4) * 18, 0x000000);
        Tools.drawBar(xpos - 190 + 299, 4 * 18 - 5, 1, (ypos - 4) * 18, 0x000000);
        if (do_play) playSample();
        if (!Main.mouse_left) vcurbutton = -1;
    }
}
