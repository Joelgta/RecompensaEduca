package recompensaeduca.recompensaeduca.services;

import java.util.List;

import recompensaeduca.recompensaeduca.models.CanjearRecompensaModel;

public interface ICanjearRecompensaService {
    
    public List<CanjearRecompensaModel> buscarTodosPorEstudianteIdOrdenadoPorCanjeadoAscRecompensa_IdAsc(int id);

    public List<Object[]> buscarTodosPorOrdenadoPorCanjeadoDescRecompensa_IdAscActualizadoEnAsc();

    public CanjearRecompensaModel buscarPorId(int id);

    public boolean guardar(CanjearRecompensaModel canjearRecompensa);
}
