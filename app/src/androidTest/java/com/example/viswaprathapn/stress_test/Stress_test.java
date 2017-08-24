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
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.Until;
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

import static android.view.KeyEvent.KEYCODE_ENDCALL;
import static com.example.viswaprathapn.stress_test.Constants.CHROME_PACKAGE;
import static com.example.viswaprathapn.stress_test.Constants.DOWNLOADS;
import static com.example.viswaprathapn.stress_test.Constants.MESSAGING_PACKAGE;
import static com.example.viswaprathapn.stress_test.Constants.MUSIC;
import static com.example.viswaprathapn.stress_test.Constants.PICTURES;
import static com.example.viswaprathapn.stress_test.Constants.PICTURE_SIZE;
import static com.example.viswaprathapn.stress_test.Constants.Phone_number;
import static com.example.viswaprathapn.stress_test.Constants.SETTINGS_PACKAGE;
import static com.example.viswaprathapn.stress_test.Constants.SMS;
import static com.example.viswaprathapn.stress_test.Constants.contactName;
//import static com.example.viswaprathapn.stress_test.UiElements.home_screen_pages;
import static com.example.viswaprathapn.stress_test.Constants.multipageSMS;
import static com.example.viswaprathapn.stress_test.UiElements.Settings;
import static com.example.viswaprathapn.stress_test.UiElements.end_call;
import static org.junit.Assert.assertNull;

/**
 * Created by viswaprathap.n on 21-06-2017.
 */

@RunWith(AndroidJUnit4.class)
public class Stress_test extends HelperClass {

    @BeforeClass
    public static void Initialize() throws RemoteException, UiObjectNotFoundException {
        Log.i(Constants.TAG, "Initialize");
        Assert.assertTrue("Device Instance is NULL", setUp());
    }

    @Test
    public void test_01() throws UiObjectNotFoundException, IOException, InterruptedException {
        HelperClass.launchApp(Constants.CHROME_PACKAGE);
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
    public void test_clearAll() throws InterruptedException, UiObjectNotFoundException, IOException {
        boolean status = flightMode();
        if (status != isNetworkAvailable()) {
            Log.i(Constants.TAG, "Testcase is pass");
        } else
            Log.i(Constants.TAG, "Testcase is fail");
    }

    @Test
    public void test_sendsms() throws InterruptedException, UiObjectNotFoundException {
        launchApp(Constants.MESSAGING_PACKAGE);
        sendMMS(Phone_number, "Capture picture");
    }

    @Test
    public void test_simstatus() throws UiObjectNotFoundException, InterruptedException {
        launchApp(Constants.SETTINGS_PACKAGE);
        Settings.getChildByText(new UiSelector().className("android.widget.LinearLayout")
                .resourceId("com.android.settings:id/dashboard_tile"),"SIM cards").click();
        for (int i=0; i<10; i++)
            simStatusChange(2);
    }

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
        callByDialer(9035087822L);
        Thread.sleep(2000);
        if (callState() == 2 ) {
            mDevice.pressHome();
            Thread.sleep(1000);
            sendSMS(Phone_number, SMS);
            delay(55);
            mDevice.pressKeyCode(KEYCODE_ENDCALL);
            //end_call.click();
        }
    }



    //srinivas

    @Test
    public void test_1_modifyContacts() throws UiObjectNotFoundException, InterruptedException, IOException {
        /*launchApp(Constants.CONTACTS_PACKAGE);

                    Thread.sleep(10000);
        //launch_App("com.android.contacts");
        for (int i = 0; i < 2; i++) {
            try {
            UiObject newContact = mDevice.findObject(new UiSelector().description("add new contact"));
            newContact.click();
            UiObject contactName = mDevice.findObject(new UiSelector().text("Name"));
            contactName.setText("Test" + i);
            mDevice.findObject(new UiSelector().text("Phone").className("android.widget.EditText")).setText(String.valueOf(randonnumberforContact()));
            //contactNumber.setText("1234567890");
            Thread.sleep(2000);
            UiObject saveContact = mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/save_menu_item"));
            Thread.sleep(2000);
            saveContact.clickAndWaitForNewWindow();

            mDevice.pressBack();
            Thread.sleep(2000);
            }

            catch(UiObjectNotFoundException e){
                e.printStackTrace();
            }
            }*/
        contactsAdd(100);




        takeScreenshot("Contacts list");
        mDevice.findObject(new UiSelector().description("More options")).click();
        mDevice.findObject(new UiSelector().text("Delete")).click();
        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/select_all_check")).click();
        takeScreenshot("all contacts");
        mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/btn_ok")).click();
        mDevice.findObject(new UiSelector().resourceId("android:id/button1")).click();


    }
    @Test
    public void test_2_sms_Message() throws UiObjectNotFoundException, InterruptedException {
       /* mDevice.pressHome();
        mDevice.wait(Until.hasObject(By.pkg("com.android.launcher").depth(0)),6000);
        mDevice.waitForIdle();
        UiObject message=mDevice.findObject(new UiSelector().text("Messaging"));
        message.clickAndWaitForNewWindow();
       */
        launch_App(Constants.MESSAGING_PACKAGE);

        for (int i=0; i<=20; i++) {
            try{
                mDevice.findObject(new UiSelector().description("New message")).click();
                mDevice.findObject(new UiSelector().text("To")).setText(Phone_number);
                mDevice.findObject(new UiSelector().text("Type message")).setText("Hi this is a test message" + i);
                mDevice.findObject(new UiSelector().resourceId("com.android.mms:id/send_button_sms")).click();
                Log.i(Constants.TAG, "Message has been sent : " +i);
                mDevice.pressBack();
                mDevice.pressBack();
            }

            catch(UiObjectNotFoundException e){
                e.printStackTrace();

            }
            Thread.sleep(5000);
            takeScreenshot("Message_list");

        }


    }
    @Test
    public void test_3_MultipageSMS() throws UiObjectNotFoundException, InterruptedException {
       /* mDevice.pressHome();
        mDevice.wait(Until.hasObject(By.pkg("com.android.launcher").depth(0)),6000);
        mDevice.waitForIdle();
        UiObject message=mDevice.findObject(new UiSelector().text("Messaging"));
        message.clickAndWaitForNewWindow();
       */
        launch_App(Constants.MESSAGING_PACKAGE);

        for (int i = 0; i <= 50; i++) {
            try {
                mDevice.findObject(new UiSelector().description("New message")).click();
                mDevice.findObject(new UiSelector().text("To")).setText(Phone_number);
                mDevice.findObject(new UiSelector().text("Type message")).setText(Constants.multipageSMS+i);
                mDevice.findObject(new UiSelector().resourceId("com.android.mms:id/send_button_sms")).click();
                Log.i(Constants.TAG, "Message has been sent : " + i);
                mDevice.pressBack();
                mDevice.pressBack();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();

            }
            Thread.sleep(5000);
            takeScreenshot("Message_list");

        }
    }
    @Test
    public void test_4_airplaneMode() throws InterruptedException, UiObjectNotFoundException {
        launch_App(Constants.SETTINGS_PACKAGE);

        mDevice.findObject(new UiSelector().text("More")).click();
        UiObject airplane= mDevice.findObject(new UiSelector().text("Airplane mode"));
        check = airplane.isEnabled();
        for(int i=0;i<=20;i++) {
            try{

                if (check ==false) {
                    Thread.sleep(30000);
                    airplane.click();
                    Log.i(Constants.TAG, "Airplane mode is enabled : "+i);
                } else {
                    Thread.sleep(30000);
                    airplane.click();
                    Log.i(Constants.TAG, "Airplane mode is disabled : "+i);

                }
            }

            catch(UiObjectNotFoundException e){
                e.printStackTrace();
            }
        }if (check == true) {
            airplane.click();
            Log.i(Constants.TAG, "Airplane mode is Disabled to continue other tests ");
        }

    }

    @Test
    public void deviceUnlock() throws RemoteException, InterruptedException, RemoteException, UiObjectNotFoundException {
        for (int i=0;i<=2;i++) {
            swipeLock(i);
            Log.i(Constants.TAG,"Device Swipe testing completed successfully");
            pinLockUnlock(i);
            Log.i(Constants.TAG,"Device PIN lock testing completed successfully");
            password_Lock(i);
            Log.i(Constants.TAG,"Device Password lock testing completed successfully");



                /*mDevice.sleep();
                Log.i(TAG, "Device is locked : " + i);
                mDevice.wakeUp();
                Thread.sleep(2000);
                //UiDevice.swipe(250, 770, 250, 250, 40);
                mDevice.swipe(250, 770, 250, 250, 40);
                Log.i(TAG, "Device is unlocked : " + i);*/

        }
    }
    @Test
    public void test_8_check_Email() throws UiObjectNotFoundException, InterruptedException {
        launch_App(Constants.EMAIL_PACKAGE);

        for (int i=0;i<=20;i++) {
            try{
                mDevice.findObject(new UiSelector().description("Compose")).clickAndWaitForNewWindow();
                mDevice.findObject(new UiSelector().resourceId("com.android.email:id/to")).setText("testsonim03@gmail.com");
                mDevice.findObject(new UiSelector().resourceId("com.android.email:id/subject")).setText("This is Test mail");
                mDevice.findObject(new UiSelector().resourceId("com.android.email:id/body")).setText("Hi, This mail is to test sending/receiving of mail for stress testing."+i);
                mDevice.findObject(new UiSelector().resourceId("com.android.email:id/send")).clickAndWaitForNewWindow();
            }

            catch(UiObjectNotFoundException e){
                e.printStackTrace();
            }

        }
        Thread.sleep(2000);
        takeScreenshot("Mail_Received");

    }
    @Test
    public void test_6_sendreceive_MMS() throws UiObjectNotFoundException, InterruptedException {
        launch_App(Constants.MESSAGING_PACKAGE);

        for (int i=0; i<=20; i++) {
            try{
                mDevice.findObject(new UiSelector().description("New message")).click();
                mDevice.findObject(new UiSelector().text("To")).setText(Phone_number);
                mDevice.findObject(new UiSelector().text("Type message")).setText("Hi this is test MMS message" + i);
                mDevice.findObject(new UiSelector().description("Attach")).clickAndWaitForNewWindow();
                Thread.sleep(2000);
                mDevice.findObject(new UiSelector().text("Capture picture")).clickAndWaitForNewWindow();
                Thread.sleep(5000);
                mDevice.findObject(new UiSelector().description("Shutter")).click();
                mDevice.findObject(new UiSelector().resourceId("org.codeaurora.snapcam:id/btn_done")).clickAndWaitForNewWindow();
                mDevice.findObject(new UiSelector().description("Send MMS")).click();
                Log.i(Constants.TAG, "MMS has been sent : " +i);
                mDevice.pressBack();
            }

            catch(UiObjectNotFoundException e){
                e.printStackTrace();
            }

        }
        Thread.sleep(5000);
        takeScreenshot("Message_MMS");


    }
    @Test
    public void test_7_WebsiteCheck() throws InterruptedException, UiObjectNotFoundException {

        launch_App(Constants.CHROME_PACKAGE);
        String url="https://www.google.co.in/?gfe_rd=cr&ei=PNk_WZeqG-rx8Afk84_oDA";
        Thread.sleep(2000);
        for(int i=0;i<=100;i++) {
            try{
                UiObject toolBar = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
                toolBar.click();
                toolBar.setText(url);
                mDevice.pressEnter();
                Thread.sleep(5000);
                String pageUrl = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/url_bar")).getText();
                junit.framework.Assert.assertEquals(url, pageUrl);
                Log.i(Constants.TAG, "Displayed page is" + pageUrl);
                takeScreenshot("Browser" + i);
            }
            catch(UiObjectNotFoundException e){
                e.printStackTrace();
            }
        }

    }
    @Test
    public void test_9_MMSattachwithpicture() throws UiObjectNotFoundException, InterruptedException {
        launch_App(Constants.MESSAGING_PACKAGE);

        for (int i=0; i<=20; i++) {
            try{
                mDevice.findObject(new UiSelector().description("New message")).click();
                mDevice.findObject(new UiSelector().text("To")).setText(Phone_number);
                mDevice.findObject(new UiSelector().text("Type message")).setText("Hi this is a test MMS message" + i);
                mDevice.findObject(new UiSelector().description("Attach")).clickAndWaitForNewWindow();
                Thread.sleep(2000);
                mDevice.findObject(new UiSelector().text("Pictures")).clickAndWaitForNewWindow();
                Thread.sleep(5000);
                mDevice.findObject(new UiSelector().resourceId( "com.android.documentsui:id/icon_mime")).clickAndWaitForNewWindow();
                mDevice.findObject(new UiSelector().description("Send MMS")).click();
                Log.i(Constants.TAG, "MMS has been sent : " +i);
                mDevice.pressBack();
            }

            catch(UiObjectNotFoundException e){
                e.printStackTrace();
            }

        }
        Thread.sleep(5000);
        takeScreenshot("MMS_picture");


    }
    @Test
    public void test_10_manualNetwork() throws UiObjectNotFoundException, InterruptedException {
        launch_App(Constants.SETTINGS_PACKAGE);

        mDevice.findObject(new UiSelector().text("More")).clickAndWaitForNewWindow();
        mDevice.findObject(new UiSelector().text("Cellular networks")).clickAndWaitForNewWindow();
        for(int i=0;i<=50;i++) {
            mDevice.findObject(new UiSelector().text("Network operators")).click();
            UiObject object=mDevice.findObject(new UiSelector().text("OK"));
            object.clickAndWaitForNewWindow();
            object.waitForExists(50000);
            // Thread.sleep(50000);
            takeScreenshot("Network list in manual network search: " + i);

            BySelector object2= By.clazz("android.widget.LinearLayout");
            assertNull(mDevice.wait(Until.hasObject(object2), 5000));
            mDevice.pressBack();
            // mDevice.wait(Until.findObject(new UiSelector().className("android.widget.LinearLayout").index(2)), 5000);
            Thread.sleep(5000);
            takeScreenshot("Network bar indicator_manual"+i);
        }

    }
    @Test
    public void test_11_automaticNetwork() throws UiObjectNotFoundException, InterruptedException {
        launch_App(Constants.SETTINGS_PACKAGE);

        mDevice.findObject(new UiSelector().text("More")).clickAndWaitForNewWindow();
        mDevice.findObject(new UiSelector().text("Cellular networks")).clickAndWaitForNewWindow();
        for(int i=0;i<=50;i++) {
            mDevice.findObject(new UiSelector().text("Network operators")).click();
            mDevice.findObject(new UiSelector().text("OK")).clickAndWaitForNewWindow();

            Thread.sleep(60000);
            mDevice.findObject(new UiSelector().text("Choose automatically")).click();
            Thread.sleep(10000);
            //mDevice.pressBack();
            Thread.sleep(5000);
            takeScreenshot("Network bar indicator_automatic"+i);
        }

    }
    @Test
    public void test_12_contactStability() throws UiObjectNotFoundException, InterruptedException {
        for(int i=0;i<=20;i++) {
            contactsAdd(20);
            UiScrollable scrollable = scrolling();
            scrollable.scrollTextIntoView("Test0");
            mDevice.findObject(new UiSelector().text("Test0")).click();
            mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/menu_edit")).click();
            mDevice.findObject(new UiSelector().text("Test0")).setText(contactName);
            mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/delete_button")).click();
            mDevice.findObject(new UiSelector().className("android.widget.EditText").text("Phone")).setText(Phone_number);
            mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/save_menu_item")).clickAndWaitForNewWindow();
            takeScreenshot("ContactStability_1_"+i);
            mDevice.pressBack();
            scrollable.scrollTextIntoView(contactName);
            mDevice.findObject(new UiSelector().text(contactName)).click();
            mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/icon_alternate")).click();
            mDevice.findObject(new UiSelector().text("Type message")).setText("Hi this is a contact stability test");
            mDevice.findObject(new UiSelector().resourceId("com.android.mms:id/send_button_sms")).click();
            Log.i(Constants.TAG, "Message has been sent : ");
            mDevice.pressBack();
            mDevice.pressBack();
            takeScreenshot("ContactStability_2_"+i);

            mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/menu_search")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/search_close_button")).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().resourceId("com.android.contacts:id/search_view")).setText(contactName);
            mDevice.pressEnter();
            mDevice.findObject(new UiSelector().text(contactName)).clickAndWaitForNewWindow();
            mDevice.findObject(new UiSelector().descriptionStartsWith("Call Mobile")).clickAndWaitForNewWindow();
            Thread.sleep(5000);
            takeScreenshot("ContactStability_3_"+i);
            mDevice.findObject(new UiSelector().resourceId("com.android.dialer:id/floating_end_call_action_button")).clickAndWaitForNewWindow();
            mDevice.pressBack();
            delectContacts();
        }

    }
    @Test
    public  void test_13_cameraStability() throws UiObjectNotFoundException, InterruptedException {
        launch_App(Constants.CAMERA_PACKAGE);
        //mDevice.findObject(new UiSelector().resourceId("org.codeaurora.snapcam:id/menu_timer_indicator")).clickAndWaitForNewWindow();
        for(int j=0; j<=20;j++){
            mDevice.swipe(250, 500, 250, 300, 40);
            Thread.sleep(2000);
            mDevice.findObject(new UiSelector().text("Picture size")).click();
            UiObject object=mDevice.findObject(new UiSelector().resourceId("org.codeaurora.snapcam:id/settingList"));
            object.getChild(new UiSelector().index(0)).clickAndWaitForNewWindow();
            UiObject object1=mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").index(2));
            object1.getChild(new UiSelector().resourceId("org.codeaurora.snapcam:id/setting_check_box")).click();

            UiObject object2=mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").index(3));
            object2.getChild(new UiSelector().resourceId("org.codeaurora.snapcam:id/setting_check_box")).click();

            UiObject object3=mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").index(4));
            object3.getChild(new UiSelector().resourceId("org.codeaurora.snapcam:id/setting_check_box")).click();

            mDevice.findObject(new UiSelector().text("Picture quality")).click();
            mDevice.findObject(new UiSelector().text("Super fine")).click();
            mDevice.findObject(new UiSelector().text("White balance")).click();
            mDevice.findObject(new UiSelector().text("Incandescent")).click();
            takeScreenshot("CameraSettings");
            Log.i(Constants.TAG,"Camera Settings are changed");
            mDevice.pressBack();
            mDevice.findObject(new UiSelector().resourceId("org.codeaurora.snapcam:id/shutter_button")).click();
            Thread.sleep(2000);

            for(int i=0;i<PICTURE_SIZE;i++) {
                mDevice.swipe(250, 500, 250, 300, 40);
                Thread.sleep(2000);
                mDevice.findObject(new UiSelector().text("Picture size")).click();
                object.getChild(new UiSelector().index(i)).clickAndWaitForNewWindow();
                takeScreenshot("CameraSettings_"+i);
                Log.i(Constants.TAG,"Camera Settings are changed for the"+object.getChild(new UiSelector().index(i)).getText()+ "th time");
                mDevice.pressBack();
                mDevice.findObject(new UiSelector().resourceId("org.codeaurora.snapcam:id/shutter_button")).clickAndWaitForNewWindow();
                Thread.sleep(2000);
            }


        }}

    //srinivas
}
