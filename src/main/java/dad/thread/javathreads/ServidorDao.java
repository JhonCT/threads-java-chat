package dad.thread.javathreads;

public interface ServidorDao {
	public void levantarConexion();
	public void flujos();
	public void recibirDatos();
	public void enviar(String s);
	public void escribirDatos(String mensaje);
	public void cerrarConexion();
	public void ejecutarConexion();
}
