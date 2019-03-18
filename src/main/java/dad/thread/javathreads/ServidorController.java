package dad.thread.javathreads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServidorController {
	
	@Autowired
	ServidorService servidorService;
	
	@GetMapping("/chat")
	public void conexion() {		 			    	    
	    servidorService.ejecutarConexion();	    
	}
	
	@GetMapping("/chat/{mensaje}")
	public void chat(@PathVariable("mensaje")String mensaje) {
		servidorService.escribirDatos(mensaje);		
		servidorService.recibirDatos();
	}	
}