package recompensaeduca.recompensaeduca.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.PerfilModel;
import recompensaeduca.recompensaeduca.models.repository.PerfilRepository;
import recompensaeduca.recompensaeduca.services.IPerfilService;

@Service
public class PerfilService implements IPerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public PerfilModel buscarPorId(int id) {
        return this.perfilRepository.findById(id);
    }

    @Override
    public Iterable<PerfilModel> buscarTodos() {
        return this.perfilRepository.findAll();
    }
    
}
