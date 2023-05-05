package it.unimol.vino.services;

import it.unimol.vino.exceptions.StateNotFoundException;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.ProcessHasStates;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.repository.ProcessRepository;
import it.unimol.vino.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProcessServiceTest {

    private ProcessService processService;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private ProcessRepository processRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.processService = new ProcessService(processRepository, stateRepository);
    }
//TODO da implementare

}