package recompensaeduca.recompensaeduca.services;

import java.util.List;

import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;

public interface IEstudianteService {

    public EstudianteEntity buscarPorNombreUsuarioYActivo(String nombreUsuario, int e);

    public List<EstudianteEntity> buscarTodosPorActivoOrdenarPorCurso_NombreCurso(int e);

    public EstudianteEntity buscarPorRutYActivo(String rut, int e);

    public EstudianteEntity buscarPorIdYActivo(int id, int e);

    public boolean guardar(EstudianteEntity estudiante);
    
}
