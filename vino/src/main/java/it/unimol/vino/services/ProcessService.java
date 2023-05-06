package it.unimol.vino.services;

import it.unimol.vino.exceptions.*;
import it.unimol.vino.models.entity.*;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.request.AddStateToProcessRequest;
import it.unimol.vino.models.request.NewProcessRequest;
import it.unimol.vino.repository.ItemRepository;
import it.unimol.vino.repository.ProcessRepository;
import it.unimol.vino.repository.StateRepository;
import it.unimol.vino.utils.Sorter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;
    private final StateRepository stateRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long createNewProcess(NewProcessRequest request) {
        HashMap<State, Integer> stateSequenceMap = new HashMap<>();
        request.getStateIdSequence().forEach((stateId, sequence) -> {
            State state = this.stateRepository.findById(stateId).orElseThrow(
                    () -> new StateNotFoundException("Stato con id " + stateId + " non trovato")
            );
            stateSequenceMap.put(state, sequence);
        });
        Sorter.sortMapByValue(stateSequenceMap);

        HashMap<Item, Integer> itemQuantityMap = new HashMap<>();
        request.getItemIdUsedQuantity().forEach((itemId, quantity) -> {
            Item item = this.itemRepository.findById(itemId).orElseThrow(
                    () -> new ItemNotFoundException("Item con id " + itemId + " non trovato")
            );
            Integer totalQuantity = item.getQuantity();
            if(totalQuantity < quantity)
                throw new QuantityNotAvailableException("Quantità non sufficiente per l'item " + item.getDescription() + " richiesta: " + quantity + " disponibile: " + totalQuantity);
            item.setQuantity(totalQuantity - quantity);
            itemQuantityMap.put(item, quantity);
        });



        Process process = new Process(stateSequenceMap, itemQuantityMap);
        return this.processRepository.save(process).getId();
    }


    public void addState(AddStateToProcessRequest request) {
        Process process = this.processRepository.findById(request.getProcessId()).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );

        State state = this.stateRepository.findById(request.getStateId()).orElseThrow(
                () -> new StateNotFoundException("Stato non trovato")
        );

        process.addState(state, request.getSequence());
    }

    public void startProcess(Long processId) {
        Process process = this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );

        if (process.getStates().isEmpty())
            throw new ProcessHasNoStatesException("Il processo non ha stati");

        if (process.getCurrentState().isPresent())
            throw new ProcessAlreadyStarted("Il processo è gia stato avviato");

        ProcessHasStates initialState = process.getStates().get(0);
        initialState.setStartDate(new Date());
        this.processRepository.save(process);
    }


    public String progressState(Long processId) {
        Process process = this.processRepository.findById(processId).orElseThrow(
                () -> new ProcessNotFoundException("Processo non trovato")
        );

        if (process.getCurrentState().isEmpty())
            throw new ProcessAlreadyStarted("Il processo non è stato avviato, quindi non può progredire");

        process.getCurrentState().get().setEndDate(new Date());

        ProcessHasStates nextState = process.getNextState();
        nextState.setStartDate(new Date());
        return nextState.getState().getName();
    }
}
