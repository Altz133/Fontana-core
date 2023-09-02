package com.fontana.backend.frame.service;

import com.fontana.backend.frame.entity.Frame;
import org.springframework.stereotype.Service;

@Service
public class FrameService {

    public void updateFrame(Frame frame, Integer id, byte value) {
        frame.setId(id);
        frame.setValue(value);
    }
}