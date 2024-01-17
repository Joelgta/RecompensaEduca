package recompensaeduca.recompensaeduca.models.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.ColegioModel;

@Repository
public interface ColegioRepository extends CrudRepository<ColegioModel, Integer> {
    
    public ColegioModel findById(int id);

}
