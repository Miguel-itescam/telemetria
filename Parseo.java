import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Parseo {
	private String cadena;
	private Telemetria telemetria;
	private boolean valido;
	private boolean basico;
	private List<Telemetria> listTelemetria;
	private String subCadenaBas;
	private String[] subCadenaExt;

	public Parseo(String cadena) {
		this.cadena = cadena;
		listTelemetria = new ArrayList<Telemetria>();
	}

	private List<String> div(String cadena) {
		List<String> list = null;

		if (cadena.indexOf("<>") > 0) {
			list = new ArrayList<String>(Arrays.asList(cadena.split("<>")));
			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).startsWith(">")) {
					list.set(i, ">" + list.get(i));
				}

				if (!list.get(i).endsWith("<")) {
					list.set(i, list.get(i) + "<");
				}
			}
		}

		return list;
	}

	public void operacion() throws Exception {
		valido = comprobar();

		if (cadena.indexOf("<>") > 0) {

			List<String> listCadena = div(cadena);

			for (String linea : listCadena) {
				if(comprobar(linea)){
					listTelemetria.add(logica(linea, false));
				}
			}

		} else {

			if (valido) {

				if (cadena.length() == 42) {
					listTelemetria.add(logica(cadena, true));
				} else {
					listTelemetria.add(logica(cadena, false));
				}
			}
		}
	}

	private Telemetria logica(String cadena, boolean estado) throws Exception {
		telemetria = new Telemetria();

		if (cadena.startsWith(">RTX")) {
			int idx = cadena.indexOf("EV");
			cadena = cadena.substring(idx, cadena.length());
			cadena = ">R" + cadena;
		}

		basico = estado;
		subCadenaBas = cadena.substring(1, 41);
		subCadenaExt = cadena.substring(42, cadena.length() - 1).split(";");

		if (subCadenaBas.charAt(0) == 'R') {
			telemetria.setIndice("Response");
		} else if (subCadenaBas.charAt(0) == 'Q') {
			telemetria.setIndice("Query");
		} else {
			telemetria.setIndice("Set");
		}

		telemetria.setCodigoEvento(Integer.parseInt(subCadenaBas.substring(3, 5)));

		try {
			telemetria.setHoraEvento(obtenerHora(subCadenaBas.substring(10, 15)));
		} catch (ParseException e) {
			throw new Exception();
		}

		try {
			telemetria
					.setFechaEvento(
							obtenerFecha(subCadenaBas.substring(5, 9), subCadenaBas.substring(9, 10)));
		} catch (ParseException e) {
			throw new Exception();
		}

		telemetria
				.setLatitud(lonLat(subCadenaBas.substring(15, 18) + "." + subCadenaBas.substring(18, 23)));

		telemetria
				.setLongitud(lonLat(subCadenaBas.substring(23, 27) + "." + subCadenaBas.substring(27, 32)));

		telemetria.setVelocidad(Double.parseDouble(
				String.format("%.2f", (Double.parseDouble(subCadenaBas.substring(32, 35)) * 1.609))));

		telemetria.setOrientacion(Integer.parseInt(subCadenaBas.substring(35, 38)));

		if (!basico) {

			int a;

			for (int i = 0; i < subCadenaExt.length; i++) {
				if (subCadenaExt[i].charAt(0) == 'A' && subCadenaExt[i].charAt(1) == 'C') {
					if (Integer.parseInt(subCadenaExt[i].substring(3)) < 0) {
						throw new Exception();
					} else {
						double aceleracion = Double.parseDouble(subCadenaExt[i].substring(3)) * 1.609;
						telemetria.setAceleracion(aceleracion);
					}
				} else if (subCadenaExt[i].charAt(0) == 'I' && subCadenaExt[i].charAt(1) == 'O') {
					a = Integer.parseInt(String.valueOf(subCadenaExt[i].charAt(3)));
					if (a == 0) {
						telemetria.setIgnicion(0);
						telemetria.setExtPw(0);
					} else if (a == 1) {
						telemetria.setIgnicion(1);
						telemetria.setExtPw(0);
					} else if (a == 2) {
						telemetria.setIgnicion(0);
						telemetria.setExtPw(1);
					}
				} else if (subCadenaExt[i].charAt(0) == 'C' && subCadenaExt[i].charAt(1) == 'L') {
					telemetria.setIdle(Integer.parseInt(subCadenaExt[i].substring(3)));
				} else if (subCadenaExt[i].charAt(0) == 'V' && subCadenaExt[i].charAt(1) == 'O') {
					telemetria.setOdometro(Integer.parseInt(subCadenaExt[i].substring(3)));
				} else if (subCadenaExt[i].charAt(0) == 'I' && subCadenaExt[i].charAt(1) == 'D') {
					telemetria.setImei(Long.parseLong(subCadenaExt[i].substring(3)));
				}
			}
		}

		return telemetria;
	}

	private boolean comprobar() {
		if (cadena.length() < 41)
			return false;
		if (cadena.charAt(0) != '>' || cadena.charAt(cadena.length() - 1) != '<')
			return false;

		return true;
	}

	private boolean comprobar(String cadena) {
		if (cadena.length() < 41)
			return false;
		if (cadena.charAt(0) != '>' || cadena.charAt(cadena.length() - 1) != '<')
			return false;

		return true;
	}

	private Date obtenerHora(String cadena) throws ParseException {
		int sec = Integer.parseInt(cadena);
		int hora = sec / 3600 - 6;
		sec %= 3600;
		int min = sec / 60;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date dateHora = sdf.parse(hora + ":" + min);

		return dateHora;
	}

	private Date obtenerFecha(String cadena, String dias) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaInicio = sdf.parse("06-01-1980");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio);
		calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(cadena) * 7 + Integer.parseInt(dias));

		calendar.add(Calendar.HOUR, telemetria.getHoraEvento().getHours());
		calendar.add(Calendar.HOUR, telemetria.getHoraEvento().getMinutes());

		calendar.add(Calendar.HOUR, -48);

		return calendar.getTime();
	}

	private String lonLat(String cadena) {

		if (cadena.startsWith("+")) {
			return "+" + Double.parseDouble(cadena.substring(1));
		} else {
			return String.valueOf(Double.parseDouble(cadena));
		}
	}

	public boolean getValido() {
		return valido;
	}

	public boolean getBasico() {
		return basico;
	}

	public List<Telemetria> getListTelemetria() {
		return listTelemetria;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}
}