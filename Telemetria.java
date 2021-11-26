import java.util.Date;

public class Telemetria {
	private String indice;
	private int codigoEvento;
	private Date fechaEvento;
	private Date horaEvento;
	private String latitud;
	private String longitud;
	private double velocidad;
	private int orientacion;
	private int ignicion;
	private int extPw;
	private double aceleracion;
	private int idle;
	private int odometro;
	private long imei;

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public int getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(int codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public Date getHoraEvento() {
		return horaEvento;
	}

	public void setHoraEvento(Date horaEvento) {
		this.horaEvento = horaEvento;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public int getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(int orientacion) {
		this.orientacion = orientacion;
	}

	public int getIgnicion() {
		return ignicion;
	}

	public void setIgnicion(int ignicion) {
		this.ignicion = ignicion;
	}

	public int getExtPw() {
		return extPw;
	}

	public void setExtPw(int extPw) {
		this.extPw = extPw;
	}

	public double getAceleracion() {
		return aceleracion;
	}

	public void setAceleracion(double aceleracion) {
		this.aceleracion = aceleracion;
	}

	public int getIdle() {
		return idle;
	}

	public void setIdle(int idle) {
		this.idle = idle;
	}

	public int getOdometro() {
		return odometro;
	}

	public void setOdometro(int odometro) {
		this.odometro = odometro;
	}

	public long getImei() {
		return imei;
	}

	public void setImei(long imei) {
		this.imei = imei;
	}
}
