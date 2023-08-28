package com.fontana.backend.snapshot.dto;

import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.led.dto.LedDTO;
import com.fontana.backend.devices.light.dto.LightDTO;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevicesRequestDto {
    List<JetDTO> jets;
    List<PumpDTO> pumps;
    List<LedDTO> leds;
    List<LightDTO> lights;
    List<JetDTO> foams;
}
