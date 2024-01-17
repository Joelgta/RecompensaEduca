package recompensaeduca.recompensaeduca.services;

import java.util.List;

import recompensaeduca.recompensaeduca.models.CursoModel;

public interface ICursoService {
    
    public List<CursoModel> buscarCursosPorActivoOrdenadoPorNombreCurso(int e);

    public CursoModel buscarCurso(int id);

    public CursoModel buscarCursoPorTipoYGradoYLetraPorActivo(int tipoEnsenanza, int grado, String letra, int e);

    public boolean guardarCurso(CursoModel curso);
}
