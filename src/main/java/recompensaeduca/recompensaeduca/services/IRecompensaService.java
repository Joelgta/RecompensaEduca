package recompensaeduca.recompensaeduca.services;

import java.util.List;

import recompensaeduca.recompensaeduca.models.RecompensaModel;

public interface IRecompensaService {
    
    public List<RecompensaModel> buscarTodosPorActivo(int e);

    public RecompensaModel buscarPorNombreYActivo(String nombre, int e);

    public RecompensaModel buscarPorIdYActivo(int id, int e);

    public boolean guardar(RecompensaModel recompensa);
}
