package com.fontana.backend.devicesType.jet.dto;

import lombok.Data;

@Data
public class JetDTO {

        private String name;
        private byte value;

        public JetDTO() {
        }

        public JetDTO(String name, byte value) {
            this.name = name;
            this.value = value;
        }

}
