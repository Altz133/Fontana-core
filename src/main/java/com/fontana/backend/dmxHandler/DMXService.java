package com.fontana.backend.dmxHandler;

import com.fontana.backend.devices.dto.DeviceDTO;
import com.fontana.backend.dmxHandler.currentStateDTO.mapper.CurrentStateDTOMapper;
import com.fontana.backend.dmxHandler.validator.service.DMXValidatorService;
import com.fontana.backend.frame.entity.Frame;
import com.fontana.backend.schedule.service.player.SchedulePlayerService;
import jakarta.annotation.PostConstruct;
import jd2xx.JD2XX;
import jd2xx.JD2XXOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class DMXService {

    private static byte[] dmxData;
    private boolean connectionOpened = false;
    private JD2XX jd;
    private JD2XXOutputStream ostream;
    @Autowired
    private DMXValidatorService dmxValidatorService;
    @Autowired
    private CurrentStateDTOMapper currentStateDTOMapper;
    private final SchedulePlayerService schedulePlayerService;

    public static byte[] getDMXData() {
        return dmxData;
    }

    @PostConstruct
    public void init() throws IOException {
        try {
//            openConnection();
            initialSetup();
            startScheduler();
        } catch (Exception e) {
        }
    }

    @Scheduled(fixedRate = 250L)
    private void startScheduler() {
        try {
            if (connectionOpened) {
                jd.resetDevice();
                jd.setTimeouts(16, 50);
                jd.setBaudRate(250000);
                jd.setDataCharacteristics(8, JD2XX.STOP_BITS_2, JD2XX.PARITY_NONE);
                jd.setFlowControl(JD2XX.FLOW_NONE, 11, 13);
                jd.setBreakOn();
                jd.setBreakOff();

                if (SchedulePlayerService.isPlaying()) {
                    ostream.write(DMXValidatorService.validateArrayCyclic(schedulePlayerService.nextDmxData()));
                } else {
                    ostream.write(dmxData);
                }
            }
        } catch (IOException e) {
            try {
                refreshConnection();
            } catch (IOException ex) {
            }
        }
    }

    //every 5 minutes check if the dmx data is valid
    @Scheduled(fixedRate = 1000L * 60 * 5)
    private void validateDMXData() throws IOException {
        if (DMXValidatorService.enableApiValidation) {
            dmxData = DMXValidatorService.validateArrayCyclic(dmxData);
        }
    }

    private void initialSetup() throws IOException {
        dmxData = new byte[515];
        for (int j = 0; j < 512; j++) {
            dmxData[j] = 0;
        }

        dmxData[3] = (byte) 0;
        dmxData[6] = (byte) 0;
        dmxData[9] = (byte) 0;
        dmxData[12] = (byte) 0;
        dmxData[15] = (byte) 0;
        dmxData[18] = (byte) 0;
        dmxData[21] = (byte) 0;
        dmxData[24] = (byte) 0;
        dmxData[27] = (byte) 0;
        dmxData[30] = (byte) 0;
        dmxData[49] = (byte) 0;
        dmxData[50] = (byte) 0;
        //pasek
        dmxData[51] = (byte) 0;
        dmxData[52] = (byte) 0;
        dmxData[53] = (byte) 0;
        dmxData[54] = (byte) 0;
        dmxData[55] = (byte) 0;
        dmxData[56] = (byte) 0;
        //light
        dmxData[57] = (byte) 0;
        dmxData[58] = (byte) 0;
        dmxData[59] = (byte) 0;

        dmxData[dmxData.length - 3] = 33;
        dmxData[dmxData.length - 2] = 22;
        dmxData[dmxData.length - 1] = 11;
        ostream.write(dmxData);
    }


    public void setDMXDataField(Frame frame) throws IOException {
        dmxData = dmxValidatorService.validateDmxData(dmxData, frame);
    }

    public List<DeviceDTO> getDMXDataArray() {
        return currentStateDTOMapper.DMXtoCurrentStateDTO(dmxData);
    }

    public void setDMXDataArray(byte[] dmxDataArray) {
        dmxData = dmxDataArray;
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

    public void panic() throws IOException {
        for (int j = 0; j < 512; j++) {
            dmxData[j] = 0;
        }
    }
}