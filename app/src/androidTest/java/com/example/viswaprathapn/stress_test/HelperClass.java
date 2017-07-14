package com.example.viswaprathapn.stress_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.example.viswaprathapn.stress_test.Constants.DOWNLOADS;
import static com.example.viswaprathapn.stress_test.Constants.MUSIC;
import static com.example.viswaprathapn.stress_test.Constants.PICTURES;
import static com.example.viswaprathapn.stress_test.Constants.SD_DOWNLOADS;
import static com.example.viswaprathapn.stress_test.Constants.SD_MUSIC;
import static com.example.viswaprathapn.stress_test.Constants.SD_PICTURES;
import static com.example.viswaprathapn.stress_test.UiElements.Download;
import static com.example.viswaprathapn.stress_test.UiElements.More_Options;
import static com.example.viswaprathapn.stress_test.UiElements.Music;
import static com.example.viswaprathapn.stress_test.UiElements.OK_button;
import static com.example.viswaprathapn.stress_test.UiElements.Options;
import static com.example.viswaprathapn.stress_test.UiElements.Pictures;
import static com.example.viswaprathapn.stress_test.UiElements.SD_card;
import static com.example.viswaprathapn.stress_test.UiElements.copy;
import static com.example.viswaprathapn.stress_test.UiElements.dial;
import static com.example.viswaprathapn.stress_test.UiElements.dial_pad;
import static com.example.viswaprathapn.stress_test.UiElements.directory_structure;
import static com.example.viswaprathapn.stress_test.UiElements.end_call;
import static com.example.viswaprathapn.stress_test.UiElements.enter_no;
import static com.example.viswaprathapn.stress_test.UiElements.file_list;
import static com.example.viswaprathapn.stress_test.UiElements.internal_storage;
import static com.example.viswaprathapn.stress_test.UiElements.file_list;
import static com.example.viswaprathapn.stress_test.UiElements.select_all;

/**
 * Created by viswaprathap.n on 21-06-2017.
 */

public class HelperClass {

    public static UiDevice mDevice;
    public static int LAUNCH_TIMEOUT = 6000;
    public static boolean check = false;

    public static UiDevice getDevice(){
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        return mDevice;
    }

    public static boolean setUp() {
        // Initialize UiDevice instance
        mDevice = getDevice();
        if (mDevice != null) {
            mDevice.pressHome();
            mDevice.pressBack();
            mDevice.wait(Until.hasObject(By.pkg(Constants.HOME_SCREEN).depth(0)), 6000);
            mDevice.waitForIdle();
            Log.i(Constants.TAG, "SetUp Complete");
            return true;
        }
        return false;
    }

    public static void registerListener(){
        Context context = InstrumentationRegistry.getContext();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //Log.i(Constants.TAG, getClass().getSimpleName());
        telephonyManager.listen(new CustomPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static boolean launchApp(String AppName){
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(AppName);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        /*TelephonyManager sMgr;
        PhoneStateListener phoneListener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                Log.d("s@urav", "s@urav CallStateChanged" + state);
            }
        };
        sMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        sMgr.listen(phoneListener,
                PhoneStateListener.LISTEN_CALL_STATE);*/


        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(AppName).depth(0)),
                LAUNCH_TIMEOUT);
        check = true;
        return check;

    }

    public static void clearAllRecentApps() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        mDevice.pressRecentApps();
        Thread.sleep(1000);
        UiObject recentApps = mDevice.findObject(new UiSelector().packageName("com.android.systemui")
                .resourceId("com.android.systemui:id/task_view_content"));
        recentApps.waitForExists(3000);
        UiObject clearApp = mDevice.findObject(new UiSelector().packageName("com.android.systemui")
                .resourceId("com.android.systemui:id/dismiss_task"));
        while (recentApps.exists()){
                clearApp.click();
        }

    }

    public static boolean clearAllBrowserTabs() throws UiObjectNotFoundException, InterruptedException {
        UiObject tabSelector = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                .resourceId("com.android.browser:id/tab_switcher"));
        tabSelector.waitForExists(2000);
        tabSelector.click();
        Thread.sleep(1000);
        UiObject closeTab= mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                .resourceId("com.android.browser:id/closetab"));
        closeTab.waitForExists(2000);
        while (closeTab.exists()){
            closeTab.click();
            Thread.sleep(2000);
        }
        launchApp(Constants.BROWSER);
        return true;
    }

    public static void readXLSX(String excelFilePath) throws IOException, InterruptedException, UiObjectNotFoundException {
        int website_ID = 0;
        String website_URL = "";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

           Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();


        while (iterator.hasNext() && website_ID < 5) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        website_URL = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        website_ID = (int) cell.getNumericCellValue();
                        break;
                }
            }
            setBrowserURL(website_ID, website_URL);
        }
            workbook.close();
            inputStream.close();
            clearAllBrowserTabs();
    }

    public static void setBrowserURL(int website_ID, String website_URL) throws UiObjectNotFoundException, InterruptedException {
        //boolean check=false;
        Log.i(Constants.TAG, "Website no =" + website_ID + "%16=" + (website_ID % 16));
        UiElements.browser_URL.click();
        Thread.sleep(1000);
        UiElements.browser_URL.setText(website_URL);
        //mDevice.click(445,765);
        mDevice.pressEnter();
        Thread.sleep(5000);

        if ((website_ID % 16) == 0) {
            clearAllBrowserTabs();
        } else {
            //website_ID=website_ID++;
            UiElements.tabSelector.click();
            Thread.sleep(2000);
                /*UiObject new_TAB = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                        .className("android.widget.ImageButton").resourceId("com.android.browser:id/newtab"));*/
            UiElements.new_TAB.click();
            Thread.sleep(2000);
            //check= true;
            //return check;
        }
    }

    public static void copyToSDcard(File source, File destination) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        launchApp(Constants.FILE_EXPLORER);
        if (source == SD_DOWNLOADS || source == SD_MUSIC || source == SD_PICTURES){
            SD_card.click();}
        else
            internal_storage.click();
            if (source == DOWNLOADS) {
                directory_structure.scrollTextIntoView("Download");
                Download.click();
        }
            else if (source == PICTURES){
                directory_structure.scrollTextIntoView("Pictures");
                Pictures.click();
        }
            else if (source == MUSIC){
                directory_structure.scrollTextIntoView("Music");
                Music.click();
        }
        Thread.sleep(1000);
        More_Options.click();
        Thread.sleep(2000);
        Options.getChildByText(new UiSelector().className("android.widget.LinearLayout"), "Copy").click();
        //copy.click();
        Thread.sleep(1000);
        select_all.click();
        Thread.sleep(1000);
        OK_button.click();
        Thread.sleep(1000);
        if (destination == SD_DOWNLOADS || destination == SD_MUSIC || destination == SD_PICTURES){
            SD_card.click();
        }
        else
            internal_storage.click();
        if (destination == DOWNLOADS) {
            directory_structure.scrollTextIntoView("Download");
            Download.click();
        }
        else if (destination == PICTURES){
            directory_structure.scrollTextIntoView("Pictures");
            Pictures.click();
        }
        else if (destination == MUSIC){
            directory_structure.scrollTextIntoView("Music");
            Music.click();
        }
        Thread.sleep(1000);
        OK_button.click();
        Thread.sleep(1000);
        clearAllRecentApps();
    }

    public static void move(File source, File destination) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        launchApp(Constants.FILE_EXPLORER);
        if (source == SD_DOWNLOADS || source == SD_MUSIC || source == SD_PICTURES){
            SD_card.click();}
        else
            internal_storage.click();
        if (source == DOWNLOADS) {
            directory_structure.scrollTextIntoView("Download");
            Download.click();
        }
        else if (source == PICTURES){
            directory_structure.scrollTextIntoView("Pictures");
            Pictures.click();
        }
        else if (source == MUSIC){
                directory_structure.scrollTextIntoView("Music");
                Music.click();
        }
        Thread.sleep(1000);
        More_Options.click();
        Thread.sleep(2000);
        Options.getChildByText(new UiSelector().className("android.widget.LinearLayout"), "Move").click();
        //copy.click();
        Thread.sleep(1000);
        select_all.click();
        Thread.sleep(1000);
        OK_button.click();
        Thread.sleep(1000);
        if (destination == SD_DOWNLOADS || destination == SD_MUSIC || destination == SD_PICTURES){
            SD_card.click();
        }
        else
            internal_storage.click();
        if (destination == DOWNLOADS) {
            directory_structure.scrollTextIntoView("Download");
            Download.click();
        }
        else if (destination == PICTURES){
            directory_structure.scrollTextIntoView("Pictures");
            Pictures.click();
        }
        else if (destination == MUSIC){
            directory_structure.scrollTextIntoView("Music");
            Music.click();
        }
        Thread.sleep(1000);
        OK_button.click();
        Thread.sleep(1000);
        clearAllRecentApps();
    }


    public static void play(File source) throws UiObjectNotFoundException, InterruptedException {
        launchApp(Constants.FILE_EXPLORER);
        if (source == SD_MUSIC)
            SD_card.click();
        else
            internal_storage.click();
        directory_structure.scrollTextIntoView("Music");
        Music.click();
        file_list.getChild(new UiSelector().className("android.widget.RelativeLayout").index(1)).click();
        delay(120);
        mDevice.pressBack();
    }

    public static void open(File source) throws UiObjectNotFoundException {
        launchApp(Constants.FILE_EXPLORER);
        if (source == SD_PICTURES)
            SD_card.click();
        else
            internal_storage.click();
        directory_structure.scrollTextIntoView("Pictures");
        Pictures.click();
        for (String file_name:source.list()){
            directory_structure.scrollTextIntoView(file_name);
            file_list.getChildByText(new UiSelector().className("android.widget.RelativeLayout"),file_name).click();
        }


    }

    public static void callByDialer(long number, int wait) throws UiObjectNotFoundException, InterruptedException {
        launchApp(Constants.DIALER);
        dial_pad.click();
        enter_no.clearTextField();
        enter_no.setText(Long.toString(number));
        dial.click();
        delay(wait);
        //Context context = InstrumentationRegistry.getContext();

        //int call_state = telephone_manager.getCallState();
        //Log.i(Constants.TAG,Integer.toString(call_state));
        end_call.click();

    }

    public static void delay(int wait) throws InterruptedException {
        Thread.sleep(wait*1000);
    }
    /*static PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.d(Constants.TAG,"####Test Call state is"+state + " :Incoming number:"+incomingNumber);
        }
    };*/
}
