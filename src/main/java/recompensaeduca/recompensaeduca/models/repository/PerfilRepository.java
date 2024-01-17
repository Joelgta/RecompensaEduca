package recompensaeduca.recompensaeduca.models.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.PerfilModel;

@Repository
public interface PerfilRepository extends CrudRepository<PerfilModel, Integer> {
    
    public PerfilModel findById(int id);
}
