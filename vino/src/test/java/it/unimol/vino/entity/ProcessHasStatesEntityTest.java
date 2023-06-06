package it.unimol.vino.entity;

import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.ProcessHasStates;
import it.unimol.vino.models.entity.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class ProcessHasStatesEntityTest {

    @Mock
    private Process mockProcess;

    @Mock
    private State mockState;

    @Test
    public void testProcessHasStates() {
        Date startDate = new Date();
        Date endDate = new Date();

        ProcessHasStates processHasStates = ProcessHasStates.builder()
                .process(mockProcess)
                .state(mockState)
                .sequence(1)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        Assertions.assertEquals(mockProcess, processHasStates.getProcess());
        Assertions.assertEquals(mockState, processHasStates.getState());
        Assertions.assertEquals(1, processHasStates.getSequence());
        Assertions.assertEquals(startDate, processHasStates.getStartDate());
        Assertions.assertEquals(endDate, processHasStates.getEndDate());
    }
}

