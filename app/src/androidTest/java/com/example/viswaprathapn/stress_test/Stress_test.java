package com.example.viswaprathapn.stress_test;

import android.annotation.TargetApi;
import android.inputmethodservice.Keyboard;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.content.Context;
import android.content.Intent;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.intent.*;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiObject;
import android.util.Log;


import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.Test;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.viswaprathapn.stress_test.UiElements;

import static com.example.viswaprathapn.stress_test.Constants.DOWNLOADS;
import static com.example.viswaprathapn.stress_test.Constants.MUSIC;
import static com.example.viswaprathapn.stress_test.Constants.PICTURES;

/**
 * Created by viswaprathap.n on 21-06-2017.
 */

@RunWith(AndroidJUnit4.class)
public class Stress_test extends HelperClass {

    @BeforeClass
    public static void Initialize(){
        Log.i(Constants.TAG, "Initialize");
        Assert.assertTrue("Device Instance is NULL", setUp());
    }

    @Test
    public void test_01() throws UiObjectNotFoundException, IOException, InterruptedException {
        HelperClass.launchApp(Constants.BROWSER);
        /*UiObject tabSelector = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                .resourceId("com.android.browser:id/tab_switcher"));
        UiObject browser_URL = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                .className("android.widget.EditText").resourceId("com.android.browser:id/url"));*/
        String excelFilePath = "/sdcard/Download/Books.xlsx";
        readXLSX(excelFilePath);
    }




        /*UiObject ErrTimedOut= mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                .text("net::ERR_TIMED_OUT"));
        //UiObject OKButton= mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                .text("OK"));*/





    @Test
    public void test_02() throws InterruptedException, UiObjectNotFoundException, IOException {
        HelperClass.launchApp(Constants.BROWSER);
        String example = Environment.getExternalStorageDirectory().getPath();
        Log.i(Constants.TAG, example);
        /*LocalTime localtime = LocalTime.now();
        LocalTime endtime = localtime.plusMinutes(30);
        while (localtime> endtime) {
            String excelFilePath = "/sdcard/Download/Books.xlsx";
            readXLSX(excelFilePath);
            localtime = LocalTime.now();*/

        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + (3*60*1000);//Adding 30 minutes
        Log.i(Constants.TAG, Long.toString(currentTime) + Long.toString(endTime));
        while (currentTime< endTime) {
            String excelFilePath = "/sdcard/Download/Books.xlsx";
            readXLSX(excelFilePath);
            currentTime = System.currentTimeMillis();
            Log.i(Constants.TAG, Long.toString(currentTime) + Long.toString(endTime));
        }
    }
    @Test
    @TargetApi(21)
    public void test_03() throws IOException, UiObjectNotFoundException, InterruptedException, RemoteException {
        //HelperClass.launchApp(Constants.FILE_EXPLORER);
        File source = DOWNLOADS;
        File destination = PICTURES;
        /*UiObject internal_storage = UiElements.file_explorer_option.getChild(new UiSelector().packageName(Constants.FILE_EXPLORER)
                .className("android.widget.TextView").text("Internal storage"));*/
        copyToSDcard(source, destination);
        move(PICTURES, MUSIC);

        for (String file_list:source.list())
            Log.i(Constants.TAG, file_list);
    }

    @Test
    public void test_06() throws InterruptedException, UiObjectNotFoundException {
        //play(MUSIC);
        open(PICTURES);
    }
    @Test
    public void test_04() throws IOException, UiObjectNotFoundException, InterruptedException, RemoteException {
        //registerListener();
        callByDialer(9035087822L, 60);
    }
}
