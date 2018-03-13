package com.example.tecpie.ZhiheSocket.entity;

import java.util.Arrays;

/**
 * Created by xsy35 on 2018/3/6.
 */

public class AddGroup {
    private String netName;
    private  SocketEntity[] sockets;

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public SocketEntity[] getSockets() {
        return sockets;
    }

    public void setSockets(SocketEntity[] sockets) {
        this.sockets = sockets;
    }

    @Override
    public String toString() {
        return "AddGroup{" +
                "netName='" + netName + '\'' +
                ", sockets=" + Arrays.toString(sockets) +
                '}';
    }
}
