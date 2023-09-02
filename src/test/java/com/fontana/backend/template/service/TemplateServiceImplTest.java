package com.fontana.backend.template.service;

import com.fontana.backend.devices.jet.dto.JetDTO;
import com.fontana.backend.devices.pump.dto.PumpDTO;
import com.fontana.backend.snapshot.dto.DevicesRequestDto;
import com.fontana.backend.snapshot.dto.SnapshotRequestDto;
import com.fontana.backend.snapshot.entity.Snapshot;
import com.fontana.backend.snapshot.mapper.SnapshotMapper;
import com.fontana.backend.snapshot.repository.SnapshotRepository;
import com.fontana.backend.template.dto.TemplateDto;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.template.mapper.TemplateDtoMapper;
import com.fontana.backend.template.repository.TemplateRepository;
import com.fontana.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class TemplateServiceImplTest {

    @Mock
    private TemplateRepository templateRepositoryMock;

    @Mock
    private TemplateDtoMapper templateDtoMapper;

    @Mock
    private SnapshotRepository snapshotRepository;
    @Mock
    private SnapshotMapper snapshotMapper;
    @Mock
    private TemplateService templateService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        templateService = new TemplateService(templateRepositoryMock, userRepository, templateDtoMapper);
    }

    @Test
    void testAddingTemplate() {
        //given
        TemplateDto testTemplateDto = new TemplateDto();
        SnapshotRequestDto testSnapshotDto = new SnapshotRequestDto();
        when(snapshotMapper.map(Collections.singletonList(any(SnapshotRequestDto.class)))).thenReturn(Collections.singletonList(new Snapshot()));


        List<PumpDTO> testPumpDto = new ArrayList<>();
        PumpDTO pumpDTO = new PumpDTO();
        pumpDTO.setName("pump1");
        pumpDTO.setValue((byte) 255);

        List<JetDTO> testJetDto = new ArrayList<>();
        JetDTO jetDTO = new JetDTO();
        jetDTO.setName("jet1");
        jetDTO.setValue(false);

        JetDTO jetDTO2 = new JetDTO();
        jetDTO2.setName("jet7");
        jetDTO2.setValue(true);

        testPumpDto.add(pumpDTO);
        testJetDto.add(jetDTO);
        testJetDto.add(jetDTO2);

        testSnapshotDto.setDuration(12);

        testSnapshotDto.setDevices(DevicesRequestDto.builder()
                .pumps(testPumpDto)
                .foams(testJetDto)
                .jets(testJetDto)
                .build());


        List<SnapshotRequestDto> testList = new ArrayList<>();
        testList.add(testSnapshotDto);

        testTemplateDto.setName("testName");
        testTemplateDto.setStatus(TemplateStatus.PUBLIC);
        testTemplateDto.setSnapshots(testList);

        //when
        templateService.addTemplate(testTemplateDto);

        //then
        verify(templateRepositoryMock).save(templateDtoMapper.mapNew(testTemplateDto));
    }

}