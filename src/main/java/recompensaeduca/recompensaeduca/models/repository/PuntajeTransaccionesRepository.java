package recompensaeduca.recompensaeduca.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import recompensaeduca.recompensaeduca.models.PuntajeTransaccionesModel;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

@Repository
public interface PuntajeTransaccionesRepository extends CrudRepository<PuntajeTransaccionesModel, Integer> {

    @Query("SELECT pt, e FROM PuntajeTransaccionesModel pt JOIN EstudianteEntity e ON ( pt.estudianteId = e.id ) ORDER BY pt.validado, pt.personal")
    List<Object[]> findTransacciones();

    @Query("SELECT pt, e FROM PuntajeTransaccionesModel pt JOIN EstudianteEntity e ON ( pt.estudianteId = e.id AND pt.personal = :personal ) ORDER BY pt.validado, pt.personal")
    List<Object[]> findTransaccionesPorPersonal(PersonalEntity personal);
    @Query("SELECT SUM(CASE WHEN pt.concepto = 1 THEN 1 ELSE 0 END) AS respeto, " +
       "SUM(CASE WHEN pt.concepto = 2 THEN 1 ELSE 0 END) AS inclusion, " +
       "SUM(CASE WHEN pt.concepto = 3 THEN 1 ELSE 0 END) AS resolucion, " +
       "SUM(CASE WHEN pt.concepto = 4 THEN 1 ELSE 0 END) AS participacion_democratica, " +
       "SUM(CASE WHEN pt.concepto = 5 THEN 1 ELSE 0 END) AS participacion_clases " +
       "FROM PuntajeTransaccionesModel pt WHERE pt.validado = 1")
    public Object findMetricaGlobal();

    public List<PuntajeTransaccionesModel> findAllByOrderByValidadoAscPersonal_ApellidoPaternoAsc();

    public PuntajeTransaccionesModel findByEstudianteIdAndValidado(int id, int v);

    public PuntajeTransaccionesModel findById(int id);
}
