package com.fontana.backend.devicesType.led.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Led {

        private int ColorRID;
        private byte ColorRValue;
        private int ColorGID;
        private byte ColorGValue;
        private int ColorBID;
        private byte ColorBValue;
        private int ColorWID;
        private byte ColorWValue;

        private int StrobeEnabledID;
        private byte StrobeEnabledValue;

        private int StrobeFreqID;
        private byte StrobeFreqValue;
}
