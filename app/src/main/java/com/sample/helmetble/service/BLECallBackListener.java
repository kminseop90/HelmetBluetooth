package com.sample.helmetble.service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Owner on 11-09.
 */

public interface BLECallBackListener {

    void getScanDevices(ArrayList<ArrayList<HashMap<String, String>>> scanDevices);

}
