package recompensaeduca.recompensaeduca.services;

import recompensaeduca.recompensaeduca.models.ConceptoModel;

public interface IConceptoService {
    
    public Iterable<ConceptoModel> buscarTodos();

    public ConceptoModel buscarPorId(int id);
}
