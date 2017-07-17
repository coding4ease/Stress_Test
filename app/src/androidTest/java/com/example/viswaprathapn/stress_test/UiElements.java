package com.example.viswaprathapn.stress_test;

import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import static com.example.viswaprathapn.stress_test.HelperClass.mDevice;

/**
 * Created by viswaprathap.n on 28-06-2017.
 */

public class UiElements {

    //Browser
    public static UiObject tabSelector = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
            .resourceId("com.android.browser:id/tab_switcher"));
    public static UiObject browser_URL = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
            .className("android.widget.EditText").resourceId("com.android.browser:id/url"));
    public static UiObject new_TAB = mDevice.findObject(new UiSelector().packageName(Constants.BROWSER)
            .className("android.widget.ImageButton").resourceId("com.android.browser:id/newtab"));

    //File explorer
    public static UiObject internal_storage = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.RelativeLayout").childSelector(new UiSelector().className("android.widget.TextView")
                    .text("Internal storage")));
    public static UiObject SD_card = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.RelativeLayout").childSelector(new UiSelector().className("android.widget.TextView")
                    .text("SD Card")));
    public static UiScrollable directory_structure = new UiScrollable(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.ListView").resourceId("android:id/list"));
    public static UiObject Download = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.RelativeLayout").childSelector(new UiSelector().className("android.widget.TextView")
                    .text("Download")));
    public static UiObject More_Options = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.LinearLayout").childSelector(new UiSelector().className("android.widget.ImageButton")
                    .description("More options")));
    public static UiObject Pictures = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.RelativeLayout").childSelector(new UiSelector().className("android.widget.TextView")
                    .text("Pictures")));
    public static UiObject Music = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.RelativeLayout").childSelector(new UiSelector().className("android.widget.TextView")
                    .text("Music")));
    public static UiCollection Options = new UiCollection(new UiSelector().className("android.widget.ListView"));
    public static UiObject copy = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .className("android.widget.LinearLayout").childSelector(new UiSelector().className("android.widget.RelativeLayout"))
            .childSelector(new UiSelector()).className("android.widget.TextView").text("Copy"));
    public static UiObject select_all = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .resourceId("com.android.qrdfileexplorer:id/layout_top").childSelector(new UiSelector().className("android.widget.CheckBox")
                    .resourceId("com.android.qrdfileexplorer:id/cbox_selectall")));
    public static UiObject OK_button = mDevice.findObject(new UiSelector().packageName(Constants.FILE_EXPLORER)
            .resourceId("com.android.qrdfileexplorer:id/layout_bottom").childSelector(new UiSelector().className("android.widget.Button")
                    .resourceId("com.android.qrdfileexplorer:id/btn_ok")));
    public static UiCollection file_list = new UiCollection(new UiSelector().packageName("com.android.qrdfileexplorer").className("android.widget.ListView")
            .resourceId("android:id/list"));


    //Dialer
    public static UiObject dial_pad = mDevice.findObject(new UiSelector().packageName(Constants.DIALER).descriptionContains("dial pad"));
    public static UiObject enter_no = mDevice.findObject(new UiSelector().packageName(Constants.DIALER).className("android.widget.EditText")
            .resourceId("com.android.dialer:id/digits"));
    public static UiObject dial = mDevice.findObject(new UiSelector().packageName(Constants.DIALER)
            .resourceId("com.android.dialer:id/dialpad_floating_action_button_container"));
    public static UiObject end_call = mDevice.findObject(new UiSelector().packageName(Constants.DIALER)
            .resourceId("com.android.dialer:id/floating_end_call_action_button").description("End"));


    //Home Screen
    public static UiObject2 page_indicator = mDevice.findObject(By).packageName(Constants.HOME_SCREEN_PACKAGE)
            .resourceId("com.google.android.googlequicksearchbox:id/page_indicator"));
    public static UiObject2[] home_screen_pages = page_indicator

}