/*package it.unimol.vino.service;

import it.unimol.vino.exceptions.ProcessAlreadyStarted;
import it.unimol.vino.exceptions.ProcessHasNoStatesException;
import it.unimol.vino.exceptions.StateNotFoundException;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.ProcessHasStates;
import it.unimol.vino.models.entity.State;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.repository.ProcessRepository;
import it.unimol.vino.repository.StateRepository;
import it.unimol.vino.services.ProcessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
//TODO alcuni casi che lanciano l'eccezioni null pointer ma non dovrebbe essere lanciata quella
@Test
void createNewProcess_ValidRequest_ReturnsNewProcessId() {
    NewProcessRequest request = NewProcessRequest.builder().stateIdSequence(new HashMap<>()).build();
    request.getStateIdSequence().put(1L, 1);
    request.getStateIdSequence().put(2L, 2);
    request.getStateIdSequence().put(3L, 3);
    State state1 = new State();
    state1.setId(1L);
    State state2 = new State();
    state2.setId(2L);
    State state3 = new State();
    state3.setId(3L);
    Mockito.when(stateRepository.findById(1L)).thenReturn(Optional.of(state1));
    Mockito.when(stateRepository.findById(2L)).thenReturn(Optional.of(state2));
    Mockito.when(stateRepository.findById(3L)).thenReturn(Optional.of(state3));

    // Act
    Long processId = processService.createNewProcess(request);

    // Assert
    assertNotNull(processId);
    assertTrue(processId > 0);
}

    @Test
    void addState_ValidRequest_AddsStateToProcess() {
        // Arrange
        AddStateToProcessRequest request = new AddStateToProcessRequest();
        request.setProcessId(1L);
        request.setStateId(2L);
        request.setSequence(2);
        Process process = new Process();
        process.setId(1L);
        State state = new State();
        state.setId(2L);
        Mockito.when(processRepository.findById(1L)).thenReturn(Optional.of(process));
        Mockito.when(stateRepository.findById(2L)).thenReturn(Optional.of(state));

        // Act
        processService.addState(request);

        // Assert
        assertEquals(1, process.getStates().size());
        assertEquals(state, process.getStates().get(0).getState());
        assertEquals(request.getSequence(), process.getStates().get(0).getSequence());
    }

    @Test
    void startProcess_ProcessHasNoStates_ThrowsException() {
        Process process = new Process();
        process.setId(1L);
        Mockito.when(processRepository.findById(1L)).thenReturn(Optional.of(process));
        assertThrows(ProcessHasNoStatesException.class, () -> processService.startProcess(1L));
    }

    @Test
    void startProcess_ValidProcessId_SetsInitialStateStartDate() {
        // Arrange
        Process process = new Process();
        process.setId(1L);
        State state = new State();
        state.setId(1L);
        ProcessHasStates initialState = new ProcessHasStates();
        initialState.setState(state);
        List<ProcessHasStates> states = new ArrayList<>();
        states.add(initialState);
        process.setStates(states);
        Mockito.when(processRepository.findById(1L)).thenReturn(Optional.of(process));

        // Act
        processService.startProcess(1L);

        // Assert
        assertNotNull(initialState.getStartDate());
    }


    @Test
    void startProcess_ProcessAlreadyStarted_ThrowsException() {
        // Arrange
        Process process = new Process();
        process.setId(1L);
        State state = new State();
        state.setId(1L);
        ProcessHasStates initialState = new ProcessHasStates();
        initialState.setState(state);
        initialState.setStartDate(new Date());
        List<ProcessHasStates> states = new ArrayList<>();
        states.add(initialState);
        process.setStates(states);
        Mockito.when(processRepository.findById(1L)).thenReturn(Optional.of(process));

        // Act + Assert
        assertThrows(ProcessAlreadyStarted.class, () -> processService.startProcess(1L));
    }

    @Test
    void progressState_ValidProcessId_ProgressesToNextStateAndReturnsStateName() {
        // Arrange
        Process process = new Process();
        process.setId(1L);


    }}*/