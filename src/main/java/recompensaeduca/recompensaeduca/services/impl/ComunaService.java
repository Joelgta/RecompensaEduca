package recompensaeduca.recompensaeduca.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.ComunaModel;
import recompensaeduca.recompensaeduca.models.repository.ComunaRepository;
import recompensaeduca.recompensaeduca.services.IComunaService;

@Service
public class ComunaService implements IComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Override
    public Iterable<ComunaModel> buscarComunas() {
        return this.comunaRepository.findAll();
    }
    
}
