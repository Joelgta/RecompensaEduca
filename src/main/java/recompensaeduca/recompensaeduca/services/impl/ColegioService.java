package recompensaeduca.recompensaeduca.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.ColegioModel;
import recompensaeduca.recompensaeduca.models.repository.ColegioRepository;
import recompensaeduca.recompensaeduca.services.IColegioService;

@Service
public class ColegioService implements IColegioService{

    @Autowired
    private ColegioRepository colegioRepository;

    @Override
    public ColegioModel buscarColegioPorId(int id) {
        return this.colegioRepository.findById(id);
    }

    @Override
    public Iterable<ColegioModel> buscarColegios() {
        return this.colegioRepository.findAll();
    }
    
}
