package com.fukuyama.fukuyamaapplication;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fukuyama on 2017/07/04.
 */
public class MainActivityTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {
    }

    @Test
    public void onResume() throws Exception {
    }

    @Test
    public void onPause() throws Exception {
    }

    @Test
    public void onActivityResult() throws Exception {
    }

    @Test
    public void onItemClick() throws Exception {
    }

    @Test
    public void onItemLongClick() throws Exception {
    }

    @Test
    public void onClick() throws Exception {
    }

    @Test
    public void getIntValue() throws Exception {
        BitmapUtil.getIntValue(1);

        int 期待値 = 100;
        Assert.assertEquals(期待値, BitmapUtil.getIntValue(1));

        Assert.assertEquals(200, BitmapUtil.getIntValue(2));

        Assert.assertEquals(300, BitmapUtil.getIntValue(3));
    }
}