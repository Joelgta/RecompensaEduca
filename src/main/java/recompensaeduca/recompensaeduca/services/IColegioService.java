package recompensaeduca.recompensaeduca.services;


import recompensaeduca.recompensaeduca.models.ColegioModel;

public interface IColegioService {

    public ColegioModel buscarColegioPorId(int id);

    public Iterable<ColegioModel> buscarColegios();
    
}
