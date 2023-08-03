package com.fontana.backend.domain.globalVariables;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
public class CurrentFountainState {
    private byte[] snapshot= new byte[50];
}
