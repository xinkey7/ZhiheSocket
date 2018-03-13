package com.example.tecpie.ZhiheSocket.bean;

import java.util.List;

/**
 * Created by xsy35 on 2017/12/20.
 */

public class MessageShowList {
    private List<MessageShowBean> MessageShowBeanList;

    public List<MessageShowBean> getMessageShowBeanList() {
        return MessageShowBeanList;
    }

    public void setMessageShowBeanList(List<MessageShowBean> messageShowBeanList) {
        MessageShowBeanList = messageShowBeanList;
    }

    @Override
    public String toString() {
        return "MessageShowList{" +
                "MessageShowBeanList=" + MessageShowBeanList +
                '}';
    }
}
