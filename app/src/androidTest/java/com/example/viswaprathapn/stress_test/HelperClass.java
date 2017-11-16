package com.example.viswaprathapn.stress_test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.SearchCondition;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.v4.app.ActivityCompat;
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
import org.junit.Before;

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
import static com.example.viswaprathapn.stress_test.Constants.SETTINGS_PACKAGE;
import static com.example.viswaprathapn.stress_test.Constants.SMS;
import static com.example.viswaprathapn.stress_test.UiElements.Download;
import static com.example.viswaprathapn.stress_test.UiElements.More_Options;
import static com.example.viswaprathapn.stress_test.UiElements.Music;
import static com.example.viswaprathapn.stress_test.UiElements.OK_button;
import static com.example.viswaprathapn.stress_test.UiElements.Options;
import static com.example.viswaprathapn.stress_test.UiElements.Pictures;
import static com.example.viswaprathapn.stress_test.UiElements.SD_card;
import static com.example.viswaprathapn.stress_test.UiElements.Settings;
import static com.example.viswaprathapn.stress_test.UiElements.attach;
import static com.example.viswaprathapn.stress_test.UiElements.attachmentList;
import static com.example.viswaprathapn.stress_test.UiElements.buttonPanel;
import static com.example.viswaprathapn.stress_test.UiElements.cameraShutter;
import static com.example.viswaprathapn.stress_test.UiElements.chromeToolbar;
import static com.example.viswaprathapn.stress_test.UiElements.copy;
import static com.example.viswaprathapn.stress_test.UiElements.dial;
import static com.example.viswaprathapn.stress_test.UiElements.dial_pad;
import static com.example.viswaprathapn.stress_test.UiElements.directory_structure;
import static com.example.viswaprathapn.stress_test.UiElements.end_call;
import static com.example.viswaprathapn.stress_test.UiElements.enter_no;
import static com.example.viswaprathapn.stress_test.UiElements.file_list;
import static com.example.viswaprathapn.stress_test.UiElements.flightMode;
import static com.example.viswaprathapn.stress_test.UiElements.internal_storage;
import static com.example.viswaprathapn.stress_test.UiElements.file_list;
import static com.example.viswaprathapn.stress_test.UiElements.messageBox;
import static com.example.viswaprathapn.stress_test.UiElements.newMessage;
import static com.example.viswaprathapn.stress_test.UiElements.recipientList;
import static com.example.viswaprathapn.stress_test.UiElements.select_all;
import static com.example.viswaprathapn.stress_test.UiElements.sendMMS;
import static com.example.viswaprathapn.stress_test.UiElements.sendMessage;
import static com.example.viswaprathapn.stress_test.UiElements.status_Bar;
import static com.example.viswaprathapn.stress_test.UiElements.thumbnail;
import static com.example.viswaprathapn.stress_test.UiElements.unlock_button;

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

    public static boolean setUp() throws RemoteException, UiObjectNotFoundException {
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


    public static boolean launchApp(String AppName){
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(AppName);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(AppName).depth(0)),
                LAUNCH_TIMEOUT);
        check = true;
        return check;

    }

     public static boolean setDisplayTime() throws UiObjectNotFoundException, InterruptedException {
         launchApp(Constants.SETTINGS_PACKAGE);
         UiElements.Settings.getChildByText(new UiSelector().className("android.widget.TextView"), "Display").click();
         delay(2);
         UiElements.Settings.getChildByText(new UiSelector().className("android.widget.TextView"), "Sleep").click();
         delay(2);
         UiElements.list.getChildByText(new UiSelector().resourceId("com.android.settings:id/text1"),"30 minutes").click();
         delay(2);
         mDevice.pressHome();
         return true;
     }

    public static void clearAllRecentApps() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        mDevice.pressRecentApps();
        Thread.sleep(1000);
        UiObject recentApps = mDevice.findObject(new UiSelector().packageName(Constants.SYSTEM_UI)
                .resourceId("com.android.systemui:id/task_view_content"));
        recentApps.waitForExists(3000);
        UiObject clearApp = mDevice.findObject(new UiSelector().packageName(Constants.SYSTEM_UI)
                .resourceId("com.android.systemui:id/dismiss_task"));
        while (recentApps.exists()){
                clearApp.click();
        }

    }

    public static boolean clearAllBrowserTabs() throws UiObjectNotFoundException, InterruptedException {
        //tabSelector.waitForExists(2000);
        //tabSelector.click();
        //Thread.sleep(1000);
        /*UiObject closeTab= chromeToolbar.getChild(new UiSelector().packageName(Constants.BROWSER)
                .resourceId("com.android.browser:id/closetab"));
        closeTab.waitForExists(2000);
        while (closeTab.exists()){
            closeTab.click();
            Thread.sleep(2000);
        }
        launchApp(Constants.BROWSER);*/
        UiObject tabSelector = chromeToolbar.getChild(new UiSelector().className("android.widget.ImageButton").resourceId("com.android.chrome:id/tab_switcher_button"));
        tabSelector.click();
        UiObject chromeOptions = chromeToolbar.getChild(new UiSelector().resourceId("com.android.chrome:id/menu_button").description("More options"));
        chromeOptions.click();
        Thread.sleep(2000);
        Options.getChildByText(new UiSelector().className("android.widget.LinearLayout"), "Close all tabs").click();
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
        Log.i(Constants.TAG, "Website no =" + website_ID + "%20=" + (website_ID % 20));
        UiElements.browser_URL.click();
        Thread.sleep(1000);
        UiElements.browser_URL.setText(website_URL);
        //mDevice.click(445,765);
        mDevice.pressEnter();
        Thread.sleep(5000);
        UiObject tabSelector = chromeToolbar.getChild(new UiSelector().className("android.widget.ImageButton").resourceId("com.android.chrome:id/tab_switcher_button"));
        UiObject new_TAB = chromeToolbar.getChild(new UiSelector().className("android.widget.Button").resourceId("com.android.chrome:id/new_tab_button"));



        if ((website_ID % 20) == 0) {
            clearAllBrowserTabs();
        } else {
            //website_ID=website_ID++;
            tabSelector.click();
            Thread.sleep(2000);
                /*UiObject new_TAB = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
                        .className("android.widget.ImageButton").resourceId("com.android.browser:id/newtab"));*/
            new_TAB.click();
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

    public static void open(File source) throws UiObjectNotFoundException, InterruptedException {
        launchApp(Constants.FILE_EXPLORER);
        if (source == SD_PICTURES)
            SD_card.click();
        else
            internal_storage.click();
        directory_structure.scrollTextIntoView("Pictures");
        Pictures.click();
        Thread.sleep(2000);
        for (String file_name:source.list()){
            Log.i(Constants.TAG, file_name);
            directory_structure.scrollTextIntoView(file_name);
            file_list.getChildByText(new UiSelector().className("android.widget.RelativeLayout"),file_name).click();
            Thread.sleep(2000);
            mDevice.pressBack();

        }


    }

    public static void callByDialer(long number) throws UiObjectNotFoundException, InterruptedException {
        launchApp(Constants.DIALER);
        dial_pad.click();
        enter_no.clearTextField();
        enter_no.setText(Long.toString(number));
        dial.click();
        Thread.sleep(5000);
        //delay(wait);
        //end_call.click();

    }

    public static void delay(int wait) throws InterruptedException {
        Thread.sleep(wait*1000);
    }


    public static boolean sendSMS(String Phone_number, String SMS) throws UiObjectNotFoundException, InterruptedException {
        launchApp(Constants.MESSAGING_PACKAGE);
        newMessage.click();
        Thread.sleep(2000);
        recipientList.setText(Phone_number);
        Thread.sleep(2000);
        messageBox.setText(SMS);
        Thread.sleep(2000);
        sendMessage.click();
        return true;
    }

    public static boolean sendMMS(String Phone_number, String attachments) throws UiObjectNotFoundException, InterruptedException {
        launchApp(Constants.MESSAGING_PACKAGE);
        newMessage.click();
        Thread.sleep(2000);
        recipientList.setText(Phone_number);
        Thread.sleep(2000);
        attach.click();
        if (attachments == "Pictures"){
            attachmentList.getChildByText(new UiSelector().className("android.widget.LinearLayout"), "Pictures").click();
            Thread.sleep(1000);
            thumbnail.click();
        }
        else if (attachments == "Capture picture"){
            attachmentList.getChildByText(new UiSelector().className("android.widget.LinearLayout"), "Capture picture").click();
            Thread.sleep(1000);
            cameraShutter.click();
            Thread.sleep(1000);
            mDevice.findObject(new UiSelector().resourceId("org.codeaurora.snapcam:id/btn_done")).click();
        }
        else if (attachments == "Videos"){
            attachmentList.getChildByText(new UiSelector().className("android.widget.LinearLayout"), "Videos").click();
            Thread.sleep(1000);
            thumbnail.click();
        }
        else if (attachments == "Capture video"){
            attachmentList.getChildByText(new UiSelector().className("android.widget.LinearLayout"), "Capture video").click();
            Thread.sleep(1000);
            cameraShutter.click();
            Thread.sleep(3000);
            cameraShutter.click();
            Thread.sleep(1000);
            mDevice.findObject(new UiSelector().resourceId("org.codeaurora.snapcam:id/btn_done")).click();
        }
        messageBox.setText(SMS);
        Thread.sleep(2000);
        sendMMS.click();

        return true;

    }

    public boolean isNetworkAvailable() {
        Context context = InstrumentationRegistry.getContext();
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public int callState(){
        Context context = InstrumentationRegistry.getContext();
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephony.getCallState();
    }



    public void simStatusChange(int SIM_NO) throws UiObjectNotFoundException, InterruptedException {
        /*Settings.getChildByText(new UiSelector().resourceId("com.android.settings:id/dashboard_tile")
                .className("android.widget.LinearLayout"), "SIM cards").click();*/
        Log.i(Constants.TAG, "c"+ Options.getChildCount(new UiSelector().className("android.widget.LinearLayout")));
        UiObject SIM_status = Options.getChild(new UiSelector().className("android.widget.LinearLayout").index(SIM_NO)).getChild(new UiSelector().className("android.widget.LinearLayout").resourceId("android:id/widget_frame"))
                .getChild(new UiSelector().className("android.widget.Switch").resourceId("com.qualcomm.qti.simsettings:id/sub_switch_widget"));

        //UiObject SIM2 = Options.getChild(new UiSelector().className("android.widget.LinearLayout").index(2));
        //UiObject SIM_status = SIM.getChild(new UiSelector().resourceId("com.qualcomm.qti.simsettings:id/sub_switch_widget").className("android.widget.Switch"));
        //UiObject SIM2_status = SIM2.getChild(new UiSelector().resourceId("com.qualcomm.qti.simsettings:id/sub_switch_widget").className("android.widget.Switch"));
        boolean simStatus = SIM_status.isChecked();
        SIM_status.click();
        Thread.sleep(2000);
        //buttonPanel.getChildByText(new UiSelector().className("android.widget.Button"), "OK");
        if (simStatus) {
            buttonPanel.getChildByText(new UiSelector().className("android.widget.Button"), "OK").click();
            Thread.sleep(2000);
        }
        mDevice.wait(Until.hasObject(By.clazz("android.widget.Button")),10000);
        buttonPanel.getChildByText(new UiSelector().className("android.widget.Button"), "OK").click();
        Thread.sleep(1000);
    }


    public boolean flightMode() throws UiObjectNotFoundException, InterruptedException {
        mDevice = getDevice();
        mDevice.openQuickSettings();
        flightMode.click();
        Thread.sleep(5000);
        return flightMode.isChecked();
    }



    //srinivas
    public void takeScreenshot(String name) {
        File dir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "app_screenshots");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.d("Screenshot Test", "Oops! Failed create directory");
            }
        }

        File file = new File(dir.getPath() + File.separator + name + ".jpg");

        mDevice.takeScreenshot(file);
    }

    public String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
    public final boolean launch_App(String app_name){
        mDevice.pressHome();
        Context context=InstrumentationRegistry.getContext();
        final Intent intent=context.getPackageManager().getLaunchIntentForPackage(app_name);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(app_name).depth(0)),6000);
        return true;
    }
    public static long randonnumberforContact(){
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return number;
    }
    public void contactsAdd(int i) throws InterruptedException, UiObjectNotFoundException {
        launchApp(Constants.CONTACTS_PACKAGE);

        Thread.sleep(10000);
        //launch_App("com.android.contacts");
        for (int j = 0; j < i; j++) {

            UiObject newContact = mDevice.findObject(new UiSelector().description("add new contact"));
            newContact.click();
            UiObject contactName = mDevice.findObject(new UiSelector().text("Name"));
            contactName.setText("Test" + j);
            mDevice.findObject(new UiSelector().text("Phone").className("android.widget.EditText")).setText(String.valueOf(randonnumberforContact()));
            //contactNumber.setText("1234567890");
            Thread.sleep(2000);
            UiObject saveContact = mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/save_menu_item"));
            Thread.sleep(2000);
            saveContact.clickAndWaitForNewWindow();

            mDevice.pressBack();
            Thread.sleep(2000);

        }
    }
    public UiScrollable scrolling() throws UiObjectNotFoundException {
        UiScrollable scrollable=new UiScrollable(new UiSelector().scrollable(true));
        scrollable.scrollToEnd(5);
        scrollable.scrollToBeginning(5);
        return scrollable;
    }
    public void delectContacts() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().description("More options")).click();
        mDevice.findObject(new UiSelector().text("Delete")).click();
        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/select_all_check")).click();
        takeScreenshot("all contacts");
        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/btn_ok")).click();
        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();
    }
    public void swipeLock(int i) throws RemoteException, InterruptedException, UiObjectNotFoundException {
        security_Setting();
        mDevice.findObject(new UiSelector().text("Swipe")).clickAndWaitForNewWindow();
        mDevice.sleep();
        Log.i(Constants.TAG, "Device is locked : " + i);
        mDevice.wakeUp();
        Thread.sleep(2000);
        //UiDevice.swipe(250, 770, 250, 250, 40);
        mDevice.swipe(250, 770, 250, 250, 40);
        Log.i(Constants.TAG, "Device is unlocked : " + i);
    }
    public  void pinLockUnlock(int i) throws UiObjectNotFoundException, RemoteException, InterruptedException {
        security_Setting();
        UiObject object=mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));
        if (object.exists()) {
            object.setText(Constants.PIN);
            mDevice.findObject(new UiSelector().text("Next")).clickAndWaitForNewWindow();
        } else {
            mDevice.findObject(new UiSelector().text("PIN")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText(Constants.PIN);
            mDevice.findObject(new UiSelector().text("Continue")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText(Constants.PIN);
            mDevice.findObject(new UiSelector().text("OK")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().text("Done")).clickAndWaitForNewWindow();
        }
        mDevice.sleep();
        mDevice.wakeUp();
        Thread.sleep(2000);
        mDevice.swipe(250, 770, 250, 250, 40);
        Thread.sleep(2000);
        UiObject pin=mDevice.findObject(new UiSelector().description("PIN area"));
        pin.clickAndWaitForNewWindow();
        pin.legacySetText(Constants.PIN);
        Thread.sleep(2000);
        mDevice.findObject(new UiSelector().resourceId("com.android.systemui:id/key_enter")).clickAndWaitForNewWindow();
        lock_None(Constants.PIN);
        /*launch_App(Constants.SETTINGS_PACKAGE);
        UiScrollable scrollable1=scrolling();
        scrollable1.scrollTextIntoView("Security");
        mDevice.findObject(new UiSelector().text("Security")).clickAndWaitForNewWindow();
        UiObject object1=mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));

        mDevice.findObject(new UiSelector().text("Screen lock")).clickAndWaitForNewWindow();
        if (object1!=null){
            object.setText(PIN);
            mDevice.findObject(new UiSelector().text("Next")).clickAndWaitForNewWindow();
        }
        mDevice.findObject(new UiSelector().text("None")).clickAndWaitForNewWindow();
        mDevice.findObject(new UiSelector().text("OK")).clickAndWaitForNewWindow();*/
    }
    public  void password_Lock(int i) throws UiObjectNotFoundException, RemoteException, InterruptedException {
        security_Setting();
        UiObject object=mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));
        if (object.exists()) {
            object.setText(Constants.PIN);
            mDevice.findObject(new UiSelector().text("Next")).clickAndWaitForNewWindow();
        } else {
            mDevice.findObject(new UiSelector().text("Password")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText(Constants.password_lock);
            mDevice.findObject(new UiSelector().text("Continue")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText(Constants.password_lock);
            mDevice.findObject(new UiSelector().text("OK")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().text("Done")).clickAndWaitForNewWindow();
        }
        mDevice.sleep();
        mDevice.wakeUp();
        Thread.sleep(2000);
        mDevice.swipe(250, 770, 250, 250, 40);
        Thread.sleep(2000);
        UiObject pin=mDevice.findObject(new UiSelector().resourceId("com.android.systemui:id/passwordEntry"));
        pin.clickAndWaitForNewWindow();
        pin.legacySetText(Constants.password_lock);
        mDevice.pressEnter();
        lock_None(Constants.password_lock);
    }
    public void lock_None(String unlock) throws UiObjectNotFoundException {
        security_Setting();
        UiObject object=mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));

        if (object!=null){
            object.setText(unlock);
            mDevice.findObject(new UiSelector().text("Next")).clickAndWaitForNewWindow();
        }
        mDevice.findObject(new UiSelector().text("None")).clickAndWaitForNewWindow();
        mDevice.findObject(new UiSelector().text("OK")).clickAndWaitForNewWindow();
    }

    public void security_Setting() throws UiObjectNotFoundException {
        launch_App(Constants.SETTINGS_PACKAGE);
        UiScrollable scrollable=scrolling();
        scrollable.scrollTextIntoView("Security");
        mDevice.findObject(new UiSelector().text("Security")).clickAndWaitForNewWindow();
        mDevice.findObject(new UiSelector().text("Screen lock")).clickAndWaitForNewWindow();
    }
//Srinivas
}
