package com.fontana.backend.devicesType.led.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Led {
        private String name;

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

        public Led(){
        }

        Led(int ColorRID, byte ColorRValue, int ColorGID, byte ColorGValue, int ColorBID, byte ColorBValue, int ColorWID, byte ColorWValue, int StrobeEnabledID, byte StrobeEnabledValue, int StrobeFreqID, byte StrobeFreqValue){
            this.ColorRID = ColorRID;
            this.ColorRValue = ColorRValue;
            this.ColorGID = ColorGID;
            this.ColorGValue = ColorGValue;
            this.ColorBID = ColorBID;
            this.ColorBValue = ColorBValue;
            this.ColorWID = ColorWID;
            this.ColorWValue = ColorWValue;
            this.StrobeEnabledID = StrobeEnabledID;
            this.StrobeEnabledValue = StrobeEnabledValue;
            this.StrobeFreqID = StrobeFreqID;
            this.StrobeFreqValue = StrobeFreqValue;
        }
}
