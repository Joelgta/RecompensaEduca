package recompensaeduca.recompensaeduca.services;


import recompensaeduca.recompensaeduca.models.PerfilModel;

public interface IPerfilService {
    
    public PerfilModel buscarPorId(int id);

    public Iterable<PerfilModel> buscarTodos();
}
