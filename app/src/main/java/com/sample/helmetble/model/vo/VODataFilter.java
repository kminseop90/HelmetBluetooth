package com.sample.helmetble.model.vo;

import android.util.Log;

public class VODataFilter {

    public interface FilterCallBack {
        void sendDataBLE(String data);

        void sendMessage(int x, int y, int z);
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
        Log.d("kms", "filter: " + data);
        String[] hexData = data.split(" ");
        /*int accelerationX  = Integer.parseInt(hexData[0], 16);
        int accelerationY  = Integer.parseInt(hexData[0], 16);
        int accelerationZ  = Integer.parseInt(hexData[0], 16);
        int gyroX = Integer.parseInt(hexData[0], 16);
        int gyroY = Integer.parseInt(hexData[0], 16);
        int gyroZ = Integer.parseInt(hexData[0], 16);*/

        int accelerationX  = Integer.parseInt(hexData[0]);
        int accelerationY  = Integer.parseInt(hexData[1]);
        int accelerationZ  = Integer.parseInt(hexData[2]);
        int gyroX = Integer.parseInt(hexData[3]);
        int gyroY = Integer.parseInt(hexData[4]);
        int gyroZ = Integer.parseInt(hexData[5]);

        if((accelerationMinX <= accelerationX && accelerationX <= accelerationMaxX) && (accelerationMinY <= accelerationY && accelerationY <= accelerationMaxY) && (accelerationMinZ <= accelerationZ && accelerationZ <= accelerationMaxZ)) {
            accelerationCountPlus(accelerationX, accelerationY, accelerationZ); // 가속도 들어오는 값이 최초 컨트롤러에서 설정하는 영역 내에 10번 들어오면 문자 메세지를 보냄
        } else {
            accelerationCount = 0;
        }

        if((gyroMinX <= gyroX && gyroX <= gyroMaxX) && (gyroMinY <= gyroY && gyroY <= gyroMaxY) && (gyroMinZ  <= gyroZ && gyroZ <= gyroMaxZ)) {
            gyroCountPlus(data); // 자이로 들어오는 값이 컨트롤러에서 설정하는 영역 내에 10번 들어오면 보드에 패킷을 write함
        } else {
            gyroCount = 0;
        }

    }

    private void accelerationCountPlus(int x, int y , int z) {
        accelerationCount++;
        if(accelerationCount == 10) {
            accelerationCount = 0;
            callback.sendMessage(x, y, z);
        }
    }

    private void gyroCountPlus(String data) {
        gyroCount++;
        if(gyroCount == 10) {
            String[] hexData = data.split(" ");
            gyroCount = 0;
            callback.sendDataBLE(String.format("%s %s %s %s", hexData[9], hexData[10], hexData[11], hexData[12]));


        }
    }
}
