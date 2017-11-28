package com.sample.helmetble.model.vo;

public class VODataFilter {
    private int accelerationMaxX = -1;
    private int accelerationMinX = -1;
    private int accelerationMaxY = -1;
    private int accelerationMinY = -1;
    private int accelerationMaxZ = -1;
    private int accelerationMinZ = -1;
    private int gyroMaxX = -1;
    private int gyroMinX = -1;
    private int gyroMaxY = -1;
    private int gyroMinY = -1;
    private int gyroMaxZ = -1;
    private int gyroMinZ = -1;

    public boolean isEmpty() {
        if (accelerationMaxX != -1
                && accelerationMaxY != -1
                && accelerationMaxZ != -1
                && accelerationMinX != -1
                && accelerationMinY != -1
                && accelerationMinZ != -1
                && gyroMaxX != -1
                && gyroMaxY != -1
                && gyroMaxZ != -1
                && gyroMinX != -1
                && gyroMinY != -1
                && gyroMinZ != -1) {
            return false;
        } else {
            return true;
        }
    }

//    public boolean isFilter(String data) {
//        String[] hexData = data.split(" ");
//        int accelerationX  = Integer.parseInt(hexData[0], 16);
//        int accelerationY  = Integer.parseInt(hexData[0], 16);
//        int accelerationZ  = Integer.parseInt(hexData[0], 16);
//        int gyroX = Integer.parseInt(hexData[0], 16);
//        int gyroY = Integer.parseInt(hexData[0], 16);
//        int gyroZ = Integer.parseInt(hexData[0], 16);
//        if(accelerationMinX < accelerationX && accelerationX < accelerationMaxX ) {
//
//        }
//    }
}
