package it.unimol.vino.service;

import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.NewStateRequest;
import it.unimol.vino.repository.StateRepository;
import it.unimol.vino.services.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateService stateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNewState() {

        NewStateRequest newStateRequest = new NewStateRequest("Test State", true);
        State state = State.builder()
                .id(1L)
                .name(newStateRequest.getStateName())
                .doesProduceWaste(newStateRequest.getDoesProduceWaste())
                .build();
        when(stateRepository.save(any(State.class))).thenReturn(state);
        Long stateId = stateService.newState(newStateRequest).getId();
        verify(stateRepository, times(1)).save(any(State.class));
        assertEquals(stateId, state.getId());
    }

}
