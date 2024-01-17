package recompensaeduca.recompensaeduca.configurations;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TareaProgramada {

    @Scheduled(cron = "0 0 0 1 * ?")
    public void reiniciarPuntajesDocentes() {
        System.out.println("Reiniciando puntajes de docentes...");
    }
}
