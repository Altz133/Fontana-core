package com.fontana.backend.snapshots.service;

import com.fontana.backend.globalVariables.CurrentFountainState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotsService {
    private final CurrentFountainState currentFountainState;

    @Autowired
    public SnapshotsService(CurrentFountainState currentFountainState) {
        this.currentFountainState = currentFountainState;
    }

    public void updateSnapshot(Integer index, Integer value) {
        if (index < 0 || index >= currentFountainState.getSnapshot().size()) {
            // Logowanie błędu lub rzucenie wyjątku
            return;
        }
        currentFountainState.getSnapshot().set(index, value);
        // Możesz tutaj dodać również logikę aktualizacji wartości w bazie danych
    }
}
