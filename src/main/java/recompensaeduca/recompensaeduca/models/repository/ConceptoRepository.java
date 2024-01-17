package recompensaeduca.recompensaeduca.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.ConceptoModel;

@Repository
public interface ConceptoRepository extends CrudRepository<ConceptoModel, Integer> {
    
    public ConceptoModel findById(int id);
}
