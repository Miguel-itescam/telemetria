import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Vista extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	// JPanel
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;

	// JButton
	private JButton btnCargarArchivo;

	// JTable
	private JTable tablaTelemetriaBasica;
	private JTable tablaTelemetriaExtendida;

	// JFileChooser
	private JFileChooser selectorArchivos;

	// DefaultTableModel
	private DefaultTableModel modeloTelemetriaBasica;
	private DefaultTableModel modeloTelemetriaExtendida;

	// Arreglos
	private Object[] datosFilaTelemetriaBasica;
	private Object[] datosFilaTelemetriaExtendida;
	private String[] columnasTelemetriaBasica;
	private String[] columnasTelemetriaExtendida;

	// JLabel
	private JLabel lblTitulo1;
	private JLabel lblTitulo2;
	private JLabel lblTitulo3;
	private JLabel lblElementos;
	private JLabel lblErrores;

	// Scanner
	private Scanner entrada;

	public Vista() {
		setTitle("Telemetria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1500, 800));
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		init();

	}

	private void init() {

		selectorArchivos = new JFileChooser();

		lblTitulo1 = new JLabel("Parseo GPS");
		lblTitulo1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitulo1, BorderLayout.NORTH);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		lblErrores = new JLabel("Elementos Invalidos: ");
		panel.add(lblErrores);

		lblElementos = new JLabel("0");
		panel.add(lblElementos);

		btnCargarArchivo = new JButton("Cargar Archivo");
		btnCargarArchivo.addActionListener(this);
		panel.add(btnCargarArchivo);

		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 83, 46, 1, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 56, 308, -76, 344, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblTitulo2 = new JLabel("Telemetria Basica");
		lblTitulo2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitulo2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTitulo2 = new GridBagConstraints();
		gbc_lblTitulo2.gridwidth = 8;
		gbc_lblTitulo2.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo2.gridx = 0;
		gbc_lblTitulo2.gridy = 0;
		panel_1.add(lblTitulo2, gbc_lblTitulo2);

		JScrollPane scrollTelemetriaBasica = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollTelemetriaBasica, gbc_scrollPane);

		// Tabla Telemetria Basica
		columnasTelemetriaBasica = new String[] { "Indice Mensaje", "Codigo Evento", "Fecha Evento", "Hora Evento",
				"Latitud", "Longitud", "Velocidad", "Orientacion" };

		datosFilaTelemetriaBasica = new Object[columnasTelemetriaBasica.length];

		tablaTelemetriaBasica = new JTable();
		modeloTelemetriaBasica = new DefaultTableModel();
		modeloTelemetriaBasica.setColumnIdentifiers(columnasTelemetriaBasica);

		scrollTelemetriaBasica.setViewportView(tablaTelemetriaBasica);

		tablaTelemetriaBasica.setModel(modeloTelemetriaBasica);

		lblTitulo3 = new JLabel("Telemetria Extendida");
		lblTitulo3.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTitulo3 = new GridBagConstraints();
		gbc_lblTitulo3.gridwidth = 8;
		gbc_lblTitulo3.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo3.gridx = 0;
		gbc_lblTitulo3.gridy = 2;
		panel_1.add(lblTitulo3, gbc_lblTitulo3);

		JScrollPane scrollTelemetriaExtendida = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 8;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 3;
		panel_1.add(scrollTelemetriaExtendida, gbc_scrollPane_1);

		// Tabla Telemetria Extendida
		columnasTelemetriaExtendida = new String[] { "Indice Mensaje", "Codigo Evento", "Fecha Evento", "Hora Evento",
				"Latitud", "Longitud", "Velocidad", "Orientacion", "Ignicion", "Fuen. Alim", "Aceleracion", "IDLE" ,"Odometro","IMEI" };
		datosFilaTelemetriaExtendida = new Object[columnasTelemetriaExtendida.length];

		tablaTelemetriaExtendida = new JTable();
		modeloTelemetriaExtendida = new DefaultTableModel();
		modeloTelemetriaExtendida.setColumnIdentifiers(columnasTelemetriaExtendida);

		scrollTelemetriaExtendida.setViewportView(tablaTelemetriaExtendida);

		tablaTelemetriaExtendida.setModel(modeloTelemetriaExtendida);

	}
	
	private void agregarTelemetriaBasica(Telemetria telemetria) {
		datosFilaTelemetriaBasica[0] = telemetria.getIndice();
		datosFilaTelemetriaBasica[1] = listaEvento(telemetria.getCodigoEvento());
		datosFilaTelemetriaBasica[2] = showFecha(telemetria.getFechaEvento());
		datosFilaTelemetriaBasica[3] = showHora(telemetria.getHoraEvento());
		datosFilaTelemetriaBasica[4] = telemetria.getLatitud();
		datosFilaTelemetriaBasica[5] = telemetria.getLongitud();
		datosFilaTelemetriaBasica[6] = telemetria.getVelocidad();
		datosFilaTelemetriaBasica[7] = telemetria.getOrientacion();
		
		modeloTelemetriaBasica.addRow(datosFilaTelemetriaBasica);
	}
	
	private void agregarTelemetriaExtendida(Telemetria telemetria) {
		datosFilaTelemetriaExtendida[0] = telemetria.getIndice();
		datosFilaTelemetriaExtendida[1] = listaEvento(telemetria.getCodigoEvento());
		datosFilaTelemetriaExtendida[2] = showFecha(telemetria.getFechaEvento());
		datosFilaTelemetriaExtendida[3] = showHora(telemetria.getHoraEvento());
		datosFilaTelemetriaExtendida[4] = telemetria.getLatitud();
		datosFilaTelemetriaExtendida[5] = telemetria.getLongitud();
		datosFilaTelemetriaExtendida[6] = telemetria.getVelocidad();
		datosFilaTelemetriaExtendida[7] = telemetria.getOrientacion();
		datosFilaTelemetriaExtendida[8] = telemetria.getIgnicion();
		datosFilaTelemetriaExtendida[9] = telemetria.getExtPw();
		datosFilaTelemetriaExtendida[10] = telemetria.getAceleracion();
		datosFilaTelemetriaExtendida[11] = telemetria.getIdle();
		datosFilaTelemetriaExtendida[12] = telemetria.getOdometro();
		datosFilaTelemetriaExtendida[13] = telemetria.getImei();
		
		modeloTelemetriaExtendida.addRow(datosFilaTelemetriaExtendida);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCargarArchivo) {
			selectorArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
			selectorArchivos.setAcceptAllFileFilterUsed(false);
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt");
	        selectorArchivos.addChoosableFileFilter(filter);
	        
			selectorArchivos.showOpenDialog(this);

			File archivo = selectorArchivos.getSelectedFile();
			
			if ((archivo == null) || (archivo.getName().strip().equals("")) || !archivo.exists() || !archivo.isFile()) {
				JOptionPane.showMessageDialog(this, "Nombre de archivo inválido", "Nombre de archivo inválido",
						JOptionPane.ERROR_MESSAGE);
			} else {

				try {
					entrada = new Scanner(archivo);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				String linea;
				Parseo parseo = null;
				int err = 0;
				
				while (entrada.hasNext()) {
					linea = entrada.nextLine();
					try {
						parseo = new Parseo(linea);						
					} catch (Exception e2) {
						lblElementos.setText(String.valueOf(++err));
						continue;
					}
					
					if(parseo.getValido()) {
						if(parseo.getBasico()) {
							agregarTelemetriaBasica(parseo.getTelemetria());
						}else {
							agregarTelemetriaExtendida(parseo.getTelemetria());
						}
					}else {
						lblElementos.setText(String.valueOf(++err));
					}
				}
				
				entrada.close();
			}
		}
	}
	
	private String showFecha(Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}
	
	private String showHora(Date hora) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(hora);
	}
	
	private String listaEvento(int codigo) {
		switch (codigo) {
		case 0:
			return "Evento Base";
		case 1:
			return "Evento de Conexion";
		case 2:
			return "Ignicion Encendida";
		case 3:
			return "Ignicion Apagada";
		case 4:
			return "IDLE Inicio";
		case 5:
			return "IDLE Fin";
		case 6:
			return "Desconexion de Bateria";
		case 7:
			return "Conexion de Bateria";
		case 8:
			return "Aceleracion Agresiva";
		case 9:
			return "Frenado Brusco";
		case 10:
			return "Posible Colision";
		case 11:
			return "Giro Brusco";
		case 12:
			return "Conduccion Agresiva";
		case 13:
			return "Exceso de Velocidad";
		case 14:
			return "Boton de Panico";
		case 15:
			return "Desconexion Validador";
		case 16:
			return "Conexion Validador";
		case 17:
			return "Desconexion Contador Maestro";
		case 18:
			return "Conexion Contador Maestro";
		case 19:
			return "Contador";
		case 20:
			return "Perdida de Comunicacion con Contadores Vinden";
			
		default:
			return "Generico";
		}
	}
}
