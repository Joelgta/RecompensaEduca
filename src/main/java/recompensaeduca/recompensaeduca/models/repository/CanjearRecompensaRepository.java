package recompensaeduca.recompensaeduca.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.CanjearRecompensaModel;

@Repository
public interface CanjearRecompensaRepository extends CrudRepository<CanjearRecompensaModel, Integer> {
    
    public List<CanjearRecompensaModel> findAllByEstudianteIdOrderByCanjeadoAscRecompensa_IdAsc(int id);

    @Query("SELECT cr, e FROM CanjearRecompensaModel cr JOIN EstudianteEntity e ON ( cr.estudianteId = e.id ) ORDER BY cr.canjeado, cr.actualizadoEn DESC")
    List<Object[]> findCanjearRecompensas();

    public CanjearRecompensaModel findById(int id);
}
