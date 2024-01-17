package recompensaeduca.recompensaeduca.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.ConceptoModel;
import recompensaeduca.recompensaeduca.models.repository.ConceptoRepository;
import recompensaeduca.recompensaeduca.services.IConceptoService;

@Service
public class ConceptoService implements IConceptoService {

    @Autowired
    private ConceptoRepository conceptoRepository;

    @Override
    public Iterable<ConceptoModel> buscarTodos() {
        return this.conceptoRepository.findAll();
    }

    @Override
    public ConceptoModel buscarPorId(int id) {
        return this.conceptoRepository.findById(id);
    }
    
}
