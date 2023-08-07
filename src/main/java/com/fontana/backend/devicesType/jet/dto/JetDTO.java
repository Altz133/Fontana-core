package com.fontana.backend.devicesType.jet.dto;

import lombok.Data;

@Data
public class JetDTO {

        private String name;
        private Boolean value;

        public JetDTO() {
        }

        public JetDTO(String name, boolean value) {
            this.name = name;
            this.value = value;
        }

}
