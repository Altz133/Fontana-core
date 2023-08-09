package com.fontana.backend.dmxHandler;

import com.fontana.backend.dmxHandler.validator.DMXValidator;
import com.fontana.backend.frame.entity.Frame;
import jakarta.annotation.PostConstruct;
import jd2xx.JD2XX;
import jd2xx.JD2XXOutputStream;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
@Service
@RequiredArgsConstructor
public class DMXService {

    private boolean connectionOpened = false;
    private JD2XX jd;
    private JD2XXOutputStream ostream;
    private byte[] dmxData;
    private TaskScheduler taskScheduler;
    private DMXValidator dmxValidator;

    @PostConstruct
    public void init() {
        try {
            initialSetup();
            //TODO it should stay commented
            System.out.println(Arrays.toString(dmxData));
//           openConnection();

//            startScheduler();
        } catch (Exception e) {
        }
    }

    private void startScheduler() {
        taskScheduler.scheduleAtFixedRate(() -> {
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
        }, 250L);
    }

    void initialSetup() throws IOException {
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
        //ostream.write(dmxData);
    }


    public void setDMXDataField(Frame frame) {
        if (dmxValidator.validate(dmxData)) {
            dmxData[frame.getId()] = frame.getValue();
        } else {
            //TODO logera trzeba zrobic
            System.out.println("Validation failed");
        }
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
}
