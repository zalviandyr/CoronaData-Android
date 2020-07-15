package com.zukron.coronadataapp;

import android.app.Instrumentation;
import android.content.Context;


import com.jakewharton.threetenabp.AndroidThreeTen;
import com.zukron.coronadataapp.activity.MainActivity;
import com.zukron.coronadataapp.model.Province;
import com.zukron.coronadataapp.networking.MathdroAPI;
import com.zukron.coronadataapp.tools.Util;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void start() throws InterruptedException {
        int i = 0;
        while (true) {
            Thread.sleep(1000);
            i++;
            System.out.println(i + " Sate");
        }
    }
}