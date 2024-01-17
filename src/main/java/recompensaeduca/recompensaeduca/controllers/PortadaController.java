package recompensaeduca.recompensaeduca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import recompensaeduca.recompensaeduca.models.MetricaGlobalModel;
import recompensaeduca.recompensaeduca.services.impl.PuntajeTransaccionesService;

@Controller
@RequestMapping("/portada")
public class PortadaController {

  @Autowired
  private PuntajeTransaccionesService puntajeTransaccionesService;
    
  @GetMapping
  public String portada(Model model)
  {
    Object resultados = this.puntajeTransaccionesService.buscarMetricaGlobal();
    
    MetricaGlobalModel metrica = new MetricaGlobalModel();
    if (resultados != null) {
      Object[] resultado = (Object[]) resultados;
      metrica.setRespeto(Integer.parseInt(resultado[0].toString()));
      metrica.setInclusion(Integer.parseInt(resultado[1].toString()));
      metrica.setResolucion(Integer.parseInt(resultado[2].toString()));
      metrica.setParticipacionDemocratica(Integer.parseInt(resultado[3].toString()));
      metrica.setParticipacionClases(Integer.parseInt(resultado[4].toString()));
  }
    model.addAttribute("metrica", metrica);

    return "custom/portada/portada";
  }  
}
