package com.example.tecpie.ZhiheSocket.bean;

import java.util.List;

/**
 * Created by xsy35 on 2017/12/20.
 */

public class AlarmShowList {
    private List<AlarmShowBean> alarmShowBeanList;

    public List<AlarmShowBean> getAlarmShowBeanList() {
        return alarmShowBeanList;
    }

    public void setAlarmShowBeanList(List<AlarmShowBean> alarmShowBeanList) {
        this.alarmShowBeanList = alarmShowBeanList;
    }

    @Override
    public String toString() {
        return "AlarmShowList{" +
                "alarmShowBeanList=" + alarmShowBeanList +
                '}';
    }
}
