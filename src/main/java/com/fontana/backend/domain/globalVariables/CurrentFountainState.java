package com.fontana.backend.domain.globalVariables;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
public class CurrentFountainState {
    private List<Integer> snapshot = new ArrayList<>(Collections.nCopies(50, 0)); // 50 zer
}
