package it.unimol.vino.entity;

import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

class ProcessEntityTest {

    private Process process;
    private Map<State, Integer> stateSequenceMap;

    @BeforeEach
    void setUp() {
        stateSequenceMap = new HashMap<>();
        State fermentingState = State.builder().name("Fermenting").doesProduceWaste(true).build();
        stateSequenceMap.put(fermentingState, 1);
        stateSequenceMap.put(State.builder().name("Aging").doesProduceWaste(true).build(), 2);
        stateSequenceMap.put(State.builder().name("Bottling").doesProduceWaste(false).build(), 3);
        process = new Process(stateSequenceMap);
    }

    public void setCurrentState(Process process, State currentState) throws Exception {
        Field field = Process.class.getDeclaredField("currentState");
        field.setAccessible(true);
        field.set(process, currentState);
    }
    @Test
    void testAddState() {
        State newState = State.builder().name("Filtering").doesProduceWaste(false).build();
        int sequence = process.getStates().size() + 1;
        process.addState(newState, sequence);
        Assertions.assertEquals(process.getStates().size(), 4);
        Assertions.assertEquals(process.getStates().get(3).getState(), newState);
        Assertions.assertEquals(process.getStates().get(3).getSequence(), sequence);
    }

}

