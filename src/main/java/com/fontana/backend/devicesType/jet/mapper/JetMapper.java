package com.fontana.backend.devicesType.jet.mapper;

import com.fontana.backend.devicesType.jet.dto.JetDTO;
import com.fontana.backend.devicesType.jet.entity.Jet;
import org.springframework.stereotype.Service;

@Service
public class JetMapper {

    public JetDTO JetToDTO(Jet jet){
        /*na podstawie bazy daanych TODO//*/
        return new JetDTO();
    }

    public Jet DTOToJet(JetDTO jetDTO){

        /*na podstawie bazy daanych TODO//*/
        return new Jet();
    }
}
