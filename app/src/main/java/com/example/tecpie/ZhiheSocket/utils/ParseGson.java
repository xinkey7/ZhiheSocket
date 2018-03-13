package com.example.tecpie.ZhiheSocket.utils;

import com.example.tecpie.ZhiheSocket.entity.NetEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsy35 on 2018/3/12.
 */

public class ParseGson {
    public static  List<NetEntity> parseNetEntityJasonArray(String netEntityJasonArray){
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(netEntityJasonArray).getAsJsonArray();
        List<NetEntity> netEntities = new ArrayList<NetEntity>();
        for (JsonElement n : jsonArray) {
            //使用GSON，直接转成Bean对象
            NetEntity netEn = gson.fromJson(n, NetEntity.class);
            netEntities.add(netEn);
        }
        return  netEntities;

    }
}
