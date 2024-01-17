package recompensaeduca.recompensaeduca.services;

import java.util.List;

import recompensaeduca.recompensaeduca.models.PuntajeTransaccionesModel;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

public interface IPuntajeTransaccionesService {
    
    public List<Object[]> buscarTodosPorOrdenadoPorValidadoAscPersonal_ApellidoPaternoAsc();

    public List<Object[]> buscarTodosPorPersonalOrdenadoPorValidadoAscPersonal_ApellidoPaternoAsc(PersonalEntity personal);

    public PuntajeTransaccionesModel buscarPorEstudianteIdYValidado(int id, int v);
    
    public PuntajeTransaccionesModel buscarPorId(int id);

    public Object buscarMetricaGlobal();

    public boolean guardar(PuntajeTransaccionesModel puntajeTransacciones);

}
