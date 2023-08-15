package com.fontana.backend.dmxHandler;

import com.fontana.backend.dmxHandler.validator.DMXValidator;
import com.fontana.backend.frame.entity.Frame;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import jd2xx.JD2XX;
import jd2xx.JD2XXOutputStream;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@EnableScheduling
public class DMXService {

    private boolean connectionOpened = false;
    private JD2XX jd;
    private JD2XXOutputStream ostream;
    private byte[] dmxData;
    //@Autowired
    //private TaskScheduler taskScheduler;
    @Autowired
    private DMXValidator dmxValidator;
    @PostConstruct
    public void init() {
        try {
//            openConnection();
            initialSetup();
            startScheduler();
        } catch (Exception e) {

        }
    }
    @Scheduled(fixedRate = 250L)
    private void startScheduler() {
        System.out.println(Arrays.toString(dmxData));
            try {

                if (connectionOpened){
                jd.resetDevice();
                jd.setTimeouts(16, 50);
                jd.setBaudRate(250000);
                jd.setDataCharacteristics(8, JD2XX.STOP_BITS_2, JD2XX.PARITY_NONE);
                jd.setFlowControl(JD2XX.FLOW_NONE, 11, 13);
                jd.setBreakOn();
                jd.setBreakOff();
                ostream.write(dmxData);}
            } catch (IOException e) {
                try {
                    refreshConnection();
                } catch (IOException ex) {
                }
            }
    }

    private void initialSetup() throws IOException {
        dmxData = new byte[515];
        for (int j = 0; j < 512; j++) {
            dmxData[j] = 0;
        }

        dmxData[3] = (byte) 255;
        dmxData[6] = (byte) 255;
        dmxData[9] = (byte) 255;
        dmxData[12] = (byte) 255;
        dmxData[15] = (byte) 255;
        dmxData[18] = (byte) 255;
        dmxData[21] = (byte) 255;
        dmxData[24] = (byte) 255;
        dmxData[27] = (byte) 255;
        dmxData[30] = (byte) 255;
        dmxData[49] = (byte) 150;
        dmxData[50] = (byte) 150;

        dmxData[dmxData.length - 3] = 33;
        dmxData[dmxData.length - 2] = 22;
        dmxData[dmxData.length - 1] = 11;
        ostream.write(dmxData);
    }


    public void setDMXDataField(Frame frame) throws IOException {
        dmxData = dmxValidator.validateDmxData(dmxData,frame);
    }

    public byte[] getDMXDataArray() {
        return dmxData;
    }

    public void closeConnection() throws IOException {
        ostream.jd2xx.close();
        ostream.close();
        jd.close();
        connectionOpened = false;
    }

    public void openConnection() throws IOException {
        jd = new JD2XX();
        String serial = new JD2XX().listDevicesBySerialNumber()[0].toString();
        jd.openBySerialNumber(serial);
        ostream = new JD2XXOutputStream(jd);
        connectionOpened = true;
    }

    private void refreshConnection() throws IOException {
        closeConnection();
        openConnection();
    }

    public void setDMXDataArray(byte[] dmxDataArray) {
        this.dmxData = dmxDataArray;
    }
}
