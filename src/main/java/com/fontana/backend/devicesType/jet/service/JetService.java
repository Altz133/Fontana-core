package com.fontana.backend.devicesType.jet.service;

public class JetService {

    public byte isEnabled(boolean enabler){
        if(enabler){
            return (byte) 200;
        }
        return (byte) 0;

    }

}
