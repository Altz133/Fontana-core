package com.fontana.backend.dmx;


import jakarta.annotation.PostConstruct;

public class DMXService {

    @PostConstruct
    public void init() {
        try {
            open();
            start();
        } catch (Exception e) {

        }
    }
    void open(){

    }
    void start(){

    }

    Byte[] translateDTo(){
        return null;
    }

    Boolean validateByteArray(){
        return null;
    }




}
