package recompensaeduca.recompensaeduca.models.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.ComunaModel;

@Repository
public interface ComunaRepository extends CrudRepository<ComunaModel, Integer> {
    
}
