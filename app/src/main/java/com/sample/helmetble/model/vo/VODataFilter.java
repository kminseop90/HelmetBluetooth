package com.sample.helmetble.model.vo;

public class VODataFilter {

    public interface FilterCallBack {
        void sendDataBLE(int flag);

        void sendMessage();
    }

    public void setFilterCallBack(FilterCallBack filterCallBack) {
        this.callback = filterCallBack;
    }

    private FilterCallBack callback;
    private int accelerationCount = 0;
    private int gyroCount = 0;

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

    public VODataFilter(int accelerationMaxX, int accelerationMinX, int accelerationMaxY, int accelerationMinY, int accelerationMaxZ, int accelerationMinZ, int gyroMaxX, int gyroMinX, int gyroMaxY, int gyroMinY, int gyroMaxZ, int gyroMinZ) {
        this.accelerationMaxX = accelerationMaxX;
        this.accelerationMinX = accelerationMinX;
        this.accelerationMaxY = accelerationMaxY;
        this.accelerationMinY = accelerationMinY;
        this.accelerationMaxZ = accelerationMaxZ;
        this.accelerationMinZ = accelerationMinZ;
        this.gyroMaxX = gyroMaxX;
        this.gyroMinX = gyroMinX;
        this.gyroMaxY = gyroMaxY;
        this.gyroMinY = gyroMinY;
        this.gyroMaxZ = gyroMaxZ;
        this.gyroMinZ = gyroMinZ;
    }

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

    public void filter(String data) {
        String[] hexData = data.split(" ");
        int accelerationX  = Integer.parseInt(hexData[0], 16);
        int accelerationY  = Integer.parseInt(hexData[0], 16);
        int accelerationZ  = Integer.parseInt(hexData[0], 16);
        int gyroX = Integer.parseInt(hexData[0], 16);
        int gyroY = Integer.parseInt(hexData[0], 16);
        int gyroZ = Integer.parseInt(hexData[0], 16);

        if((accelerationMinX <= accelerationX && accelerationX <= accelerationMaxX) && (accelerationMinY <= accelerationY && accelerationY <= accelerationMaxY) && (accelerationMinZ <= accelerationZ && accelerationZ <= accelerationMaxZ)) {
            accelerationCountPlus();
        } else {
            accelerationCount = 0;
        }

        if((gyroMinX <= gyroX && gyroX <= gyroMaxX) && (gyroMinY <= gyroY && gyroY <= gyroMaxY) && (gyroMinZ  <= gyroZ && gyroZ <= gyroMaxZ)) {
            gyroCountPlus();
        } else {
            gyroCount = 0;
        }

    }

    private void accelerationCountPlus() {
        accelerationCount++;
        if(accelerationCount == 10) {
            accelerationCount = 0;
            callback.sendMessage();
        }
    }

    private void gyroCountPlus() {
        gyroCount++;
        if(gyroCount == 10) {
            gyroCount = 0;
            callback.sendDataBLE(0);


        }
    }
}
