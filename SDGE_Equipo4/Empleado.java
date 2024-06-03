/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SDGE_Equipo4;

import Manejador.ManejadorBD;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Alejandro Castro Duarte
 */
public class Empleado extends javax.swing.JFrame {
    
    private final ManejadorBD manejadorBD;    

    /**
     * Creates new form Empleado
     */
    public Empleado() {
        initComponents();
        this.manejadorBD = new ManejadorBD();
        manejadorBD.conectar("sistemadegestionempresarial");
//        this.manejadorBD.conectar("Tiendita", "root", "root", "localhost", 3306);
        llenarVentana("Empleado");
    }
    public Empleado(ManejadorBD manejadorBD) {
        initComponents();
        this.manejadorBD = manejadorBD;
        llenarVentana("Empleado");
    }

    private void llenarVentana(String tabla) {
        desactivarCampos();        
        btnEliminar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        if (manejadorBD.consultaRegistros(tabla) != 0) {
            muestraRegistroActual();
            btnPrimerRegistro.setEnabled(true);
            btnSiguienteRegistro.setEnabled(true);
            btnAnteriorRegistro.setEnabled(true);
            btnUltimoRegistro.setEnabled(true);
            btnEditar.setEnabled(true);

            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);
        }
    }
    
    private void muestraRegistroActual() {
        if (manejadorBD.indiceActual() != 0) {
            manejadorBD.refrescaRegistroActual();
            btnEditar.setEnabled(true);
            txtRFC.setText(manejadorBD.getCampoRegistroActual("RFC_Empleado").toString());
            txtGenero.setText(manejadorBD.getCampoRegistroActual("Sexo").toString());
            txtNombre.setText(manejadorBD.getCampoRegistroActual("Nombre").toString());
            txtApellidoP.setText(manejadorBD.getCampoRegistroActual("Apellido_P").toString());
            txtApellidoM.setText(manejadorBD.getCampoRegistroActual("Apellido_M").toString());
            txtFechaNac.setText(manejadorBD.getCampoRegistroActual("Fecha_Nac").toString());
            txtDomicilio.setText(manejadorBD.getCampoRegistroActual("Domicilio").toString());
            txtEmail.setText(manejadorBD.getCampoRegistroActual("Correo").toString());
            txtNumTel.setText(manejadorBD.getCampoRegistroActual("Num_Tel").toString());
            txtCURP.setText(manejadorBD.getCampoRegistroActual("CURP").toString());
            txtNSS.setText(manejadorBD.getCampoRegistroActual("NSS").toString());            
            txtNacionalidad.setText(manejadorBD.getCampoRegistroActual("Nacionalidad").toString());
            txtIDDepto.setText(manejadorBD.getCampoRegistroActual("ID_Depto").toString());
            txtIDCurso.setText(manejadorBD.getCampoRegistroActual("ID_Curso").toString());
            
            
            //Le coloca la fecha al jDateChooser
            SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fecha;
            try {
                fecha = formatoF.parse(manejadorBD.getCampoRegistroActual("Fecha_Nac").toString().substring(0, 10));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "La fecha de la BD no tiene formato yyyy-MM-dd.",
                        "Error en el campo fecha registro",
                        JOptionPane.ERROR_MESSAGE);
                fecha = null;
            }
            jdc1.setDate(fecha);
                                    
            
            } else {
            nuevoRegistro();
            btnEliminar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnActualizar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);

        }
    }
    private void primerRegistro() {
        manejadorBD.veAlPrimerRegistro();
        muestraRegistroActual();
    }

    private void ultimoRegistro() {
        manejadorBD.veAlUltimoRegistro();
        muestraRegistroActual();                
    }

    private void siguienteRegistro() {
        manejadorBD.veAlSiguienteRegistro();
        muestraRegistroActual();
    }

    private void anteriorRegistro() {
        manejadorBD.veAlAnteriorRegistro();
        muestraRegistroActual();
    }
    
    private void limpiarRegistro() {
        txtRFC.setText("");
        txtDomicilio.setText("");
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");        
        txtFechaNac.setText("");
        txtNumTel.setText("");
        txtEmail.setText("");   
        txtCURP.setText("");
        txtNSS.setText("");
        txtGenero.setText("");
        txtNacionalidad.setText("");
        txtIDDepto.setText("");
        txtIDCurso.setText("");
        jdc1.setDate(null);
        txtGenero.setText("");
        
        
    }
    private void nuevoRegistro() {
        activarCampos();
        limpiarRegistro();

    }
    private void activarCampos() {
        txtRFC.setEditable(true);
        //jdcFechaNac.setEnabled(true);
        txtDomicilio.setEditable(true);
        txtNombre.setEditable(true);
        txtApellidoP.setEditable(true);
        txtApellidoM.setEditable(true);        
        //txtFechaNac.setVisible(false);  
        txtFechaNac.setEditable(false);
        txtNumTel.setEditable(true);
        txtEmail.setEditable(true);
        txtCURP.setEditable(true);
        txtNSS.setEditable(true);        
        txtNacionalidad.setEditable(true);
        txtIDDepto.setEditable(true);
        txtIDCurso.setEditable(true);
        jdc1.setEnabled(true);
        txtGenero.setEditable(true);        
        jComboBox1.setVisible(true);
        //jComboBox1.setEditable(true);
        //txtEdad.setEditable(true); nunca se activa, será calcualdo por el programa        
    }
    private void desactivarCampos() {
        txtRFC.setEditable(false);
        txtDomicilio.setEditable(false);
        txtNombre.setEditable(false);
        txtApellidoP.setEditable(false);
        txtApellidoM.setEditable(false);                
        txtFechaNac.setEditable(true);         
        txtNumTel.setEditable(false);
        txtEmail.setEditable(false);          
        txtCURP.setEditable(false);
        txtNSS.setEditable(false);        
        txtNacionalidad.setEditable(false);
        txtIDDepto.setEditable(false);
        txtIDCurso.setEditable(false);
        jdc1.setEnabled(false);
        txtGenero.setEditable(false);
        jComboBox1.setVisible(false);
        
        
    }
    private boolean validarCampos() {                       
        
        boolean correcto = true;
    
    // Validar RFC
    String rfc = txtRFC.getText().trim();
    if (rfc.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "El campo RFC del empleado no puede estar vacío",
            "Error en el campo RFC_Empleado",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    // Validar Nombre
    if (txtNombre.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se deben introducir el nombre del empleado.",
            "Error en el campo Nombres",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
    
    if (txtGenero.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se deben introducir el sexo del empleado.",
            "Error en el campo Sexo",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    // Validar Domicilio
    if (txtDomicilio.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se deben introducir el domicilio del empleado.",
            "Error en el campo Domicilio",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    // Validar Apellido Paterno
    if (txtApellidoP.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se deben introducir el Apellido Paterno del empleado.",
            "Error en el campo Apellido Paterno",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    // Validar Apellido Materno
    if (txtApellidoM.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se deben introducir el Apellido Materno del empleado.",
            "Error en el campo Apellido Materno",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }        
    
     // Validar Fecha de Nacimiento (usando JDateChooser)
    java.util.Date fechaNacimiento = jdc1.getDate();
    if (fechaNacimiento == null) {
        JOptionPane.showMessageDialog(this,
                "Se debe seleccionar la fecha de nacimiento.",
                "Error en el campo Fecha_Nac",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validar Teléfono
    String numTel = txtNumTel.getText().trim();
    if (numTel.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se deben introducir el teléfono del empleado.",
            "Error en el campo Teléfono",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    } else if (numTel.length() != 10) {
        JOptionPane.showMessageDialog(this,
            "El teléfono del empleado debe tener 10 caracteres.",
            "Error en el campo Teléfono",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
        

    // Validar Correo Electrónico
    if (txtEmail.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se debe introducir el correo electrónico del empleado.",
            "Error en el campo Correo Electrónico",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    } else if (!ValidarEmail(txtEmail.getText().trim())) {
        JOptionPane.showMessageDialog(this,
            "El formato del correo electrónico no es válido.",
            "Error en el campo Correo Electrónico",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    // Validar CURP
    if (txtCURP.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se debe introducir el CURP del empleado.",
            "Error en el campo CURP",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    // Validar NSS
    if (txtNSS.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se debe introducir el NSS del empleado.",
            "Error en el campo NSS",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
    
    if (txtIDCurso.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se debe introducir la nacionalidad del empleado.",
            "Error en el campo Nacionalidad",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
    
    if (txtIDDepto.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se debe introducir la nacionalidad del empleado.",
            "Error en el campo Nacionalidad",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    // Validar Nacionalidad
    if (txtNacionalidad.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Se debe introducir la nacionalidad del empleado.",
            "Error en el campo Nacionalidad",
            JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
    
    return correcto;

    }
    private static boolean ValidarEmail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9\\+]+(\\.[_A-Za-z0-9]+)*@[A-Za-z]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }
    
    private void insertaRegistro() {
        
      if (!validarCampos()) {
          
          return;

}
    
        try {
            // Obtener la fecha de nacimiento y formatearla
            java.util.Date fechaNacimiento = jdc1.getDate();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNacimientoString = formatoFecha.format(fechaNacimiento);           

            String genero = jComboBox1.getSelectedItem().toString();
            //System.out.println("Género seleccionado: " + genero);
            // Insertar los campos en la base de datos
            manejadorBD.iniciarBloque("Empleado");

            manejadorBD.insertarCampo("RFC_Empleado", txtRFC.getText());
            manejadorBD.insertarCampo("Sexo", genero);
            manejadorBD.insertarCampo("Nombre", txtNombre.getText());
            manejadorBD.insertarCampo("Apellido_P", txtApellidoP.getText());
            manejadorBD.insertarCampo("Apellido_M", txtApellidoM.getText());
            manejadorBD.insertarCampo("Fecha_Nac", fechaNacimientoString);
            manejadorBD.insertarCampo("Domicilio", txtDomicilio.getText());
            manejadorBD.insertarCampo("Num_Tel", txtNumTel.getText());
            manejadorBD.insertarCampo("Correo", txtEmail.getText());
            manejadorBD.insertarCampo("CURP", txtCURP.getText());
            manejadorBD.insertarCampo("NSS", txtNSS.getText());            
            manejadorBD.insertarCampo("Nacionalidad", txtNacionalidad.getText());
            manejadorBD.insertarCampo("ID_Depto", txtIDDepto.getText());
            manejadorBD.insertarCampo("ID_Curso", txtIDCurso.getText());

            manejadorBD.cerrarBloque();
            limpiarRegistro();
            llenarVentana("Empleado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al insertar el registro en la base de datos: " + e.getMessage(),
                    "Error de inserción",
                    JOptionPane.ERROR_MESSAGE);
        }
    
}



    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblApellidoP = new javax.swing.JLabel();
        btnPrimerRegistro = new javax.swing.JButton();
        btnAnteriorRegistro = new javax.swing.JButton();
        btnSiguienteRegistro = new javax.swing.JButton();
        btnUltimoRegistro = new javax.swing.JButton();
        txtApellidoP = new javax.swing.JTextField();
        lblApellidoM = new javax.swing.JLabel();
        txtApellidoM = new javax.swing.JTextField();
        txtNumTel = new javax.swing.JTextField();
        lblNumTel = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        lblDomicilio = new javax.swing.JLabel();
        lblFechaNac = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDomicilio = new javax.swing.JTextField();
        lblTablaEmpleados = new javax.swing.JLabel();
        btnNuevoRegistro = new javax.swing.JButton();
        btnRegresarMenu = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtFechaNac = new javax.swing.JTextField();
        lblRFC = new javax.swing.JLabel();
        lblCURP = new javax.swing.JLabel();
        lblNSS = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblNacionalidad = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        txtCURP = new javax.swing.JTextField();
        txtNSS = new javax.swing.JTextField();
        txtNacionalidad = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jdc1 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtGenero = new javax.swing.JTextField();
        lblIDDepto = new javax.swing.JLabel();
        lblIDCurso = new javax.swing.JLabel();
        txtIDDepto = new javax.swing.JTextField();
        txtIDCurso = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombre.setBackground(new java.awt.Color(153, 153, 255));
        lblNombre.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNombre.setText("Nombre");
        lblNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNombre.setOpaque(true);
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 30));

        lblApellidoP.setBackground(new java.awt.Color(153, 153, 255));
        lblApellidoP.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblApellidoP.setText("Apellido_P");
        lblApellidoP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblApellidoP.setOpaque(true);
        jPanel1.add(lblApellidoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 150, 30));

        btnPrimerRegistro.setBackground(new java.awt.Color(153, 255, 255));
        btnPrimerRegistro.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnPrimerRegistro.setText("Primero");
        btnPrimerRegistro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrimerRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimerRegistroActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimerRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 80, 30));

        btnAnteriorRegistro.setBackground(new java.awt.Color(153, 255, 255));
        btnAnteriorRegistro.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnAnteriorRegistro.setText("Anterior");
        btnAnteriorRegistro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnteriorRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorRegistroActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnteriorRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 80, 30));

        btnSiguienteRegistro.setBackground(new java.awt.Color(153, 255, 255));
        btnSiguienteRegistro.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnSiguienteRegistro.setText("Siguiente");
        btnSiguienteRegistro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSiguienteRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteRegistroActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguienteRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 90, 30));

        btnUltimoRegistro.setBackground(new java.awt.Color(153, 255, 255));
        btnUltimoRegistro.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnUltimoRegistro.setText("Ultimo");
        btnUltimoRegistro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUltimoRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoRegistroActionPerformed(evt);
            }
        });
        jPanel1.add(btnUltimoRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 510, 90, 30));

        txtApellidoP.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtApellidoP.setToolTipText("Ingresa el apellido paterno del empleado");
        txtApellidoP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtApellidoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 140, 30));

        lblApellidoM.setBackground(new java.awt.Color(153, 153, 255));
        lblApellidoM.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblApellidoM.setText("Apellido_M");
        lblApellidoM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblApellidoM.setOpaque(true);
        jPanel1.add(lblApellidoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, 30));

        txtApellidoM.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtApellidoM.setToolTipText("Ingresa el apellido materno del empleado");
        txtApellidoM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtApellidoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 140, 30));

        txtNumTel.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtNumTel.setToolTipText("Ingresa el numero de telefono del empleado");
        txtNumTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNumTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 140, 30));

        lblNumTel.setBackground(new java.awt.Color(153, 153, 255));
        lblNumTel.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNumTel.setText("Num_Telefono");
        lblNumTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNumTel.setOpaque(true);
        jPanel1.add(lblNumTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 150, 30));

        txtEmail.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtEmail.setToolTipText("Ingressa el email del empleado");
        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 140, 30));

        lblEmail.setBackground(new java.awt.Color(153, 153, 255));
        lblEmail.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblEmail.setText("Email");
        lblEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblEmail.setOpaque(true);
        jPanel1.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 150, 30));

        lblDomicilio.setBackground(new java.awt.Color(153, 153, 255));
        lblDomicilio.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblDomicilio.setText("Domicilio");
        lblDomicilio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDomicilio.setOpaque(true);
        jPanel1.add(lblDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 150, 30));

        lblFechaNac.setBackground(new java.awt.Color(153, 153, 255));
        lblFechaNac.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblFechaNac.setText("Fecha_Nacimiento");
        lblFechaNac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblFechaNac.setOpaque(true);
        jPanel1.add(lblFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 150, 30));

        txtNombre.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtNombre.setToolTipText("Ingresa el nombre del empleado");
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 140, 30));

        txtDomicilio.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtDomicilio.setToolTipText("Ingresa el domicilio del empleado");
        txtDomicilio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtDomicilio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 140, 30));

        lblTablaEmpleados.setBackground(new java.awt.Color(153, 0, 153));
        lblTablaEmpleados.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        lblTablaEmpleados.setText("Tabla empleados");
        lblTablaEmpleados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        lblTablaEmpleados.setOpaque(true);
        jPanel1.add(lblTablaEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        btnNuevoRegistro.setBackground(new java.awt.Color(153, 255, 255));
        btnNuevoRegistro.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnNuevoRegistro.setText("Nuevo");
        btnNuevoRegistro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevoRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoRegistroActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, 120, 30));

        btnRegresarMenu.setBackground(new java.awt.Color(153, 255, 255));
        btnRegresarMenu.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnRegresarMenu.setText("Regresar al menu");
        btnRegresarMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegresarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarMenuActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresarMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 510, 140, 30));

        btnActualizar.setBackground(new java.awt.Color(153, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 460, 120, 30));

        btnEditar.setBackground(new java.awt.Color(153, 255, 255));
        btnEditar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 460, 120, 30));

        btnEliminar.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 460, 120, 30));

        btnCancelar.setBackground(new java.awt.Color(153, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 510, 120, 30));

        btnGuardar.setBackground(new java.awt.Color(153, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 510, 130, 30));

        txtFechaNac.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtFechaNac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 140, 30));

        lblRFC.setBackground(new java.awt.Color(153, 153, 255));
        lblRFC.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblRFC.setText("RFC_Empleado");
        lblRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblRFC.setOpaque(true);
        jPanel1.add(lblRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, 30));

        lblCURP.setBackground(new java.awt.Color(153, 153, 255));
        lblCURP.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblCURP.setText("CURP");
        lblCURP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblCURP.setOpaque(true);
        jPanel1.add(lblCURP, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 150, 30));

        lblNSS.setBackground(new java.awt.Color(153, 153, 255));
        lblNSS.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNSS.setText("NSS");
        lblNSS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNSS.setOpaque(true);
        jPanel1.add(lblNSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 150, 30));

        lblSexo.setBackground(new java.awt.Color(153, 153, 255));
        lblSexo.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblSexo.setText("Sexo");
        lblSexo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblSexo.setOpaque(true);
        jPanel1.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, 150, 30));

        lblNacionalidad.setBackground(new java.awt.Color(153, 153, 255));
        lblNacionalidad.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNacionalidad.setText("Nacionalidad");
        lblNacionalidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNacionalidad.setOpaque(true);
        jPanel1.add(lblNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 150, 30));

        txtRFC.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtRFC.setToolTipText("Ingresa el ID del empleado");
        txtRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 140, 30));

        txtCURP.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtCURP.setToolTipText("Ingresa el CURP del empleado");
        txtCURP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtCURP, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 140, 30));

        txtNSS.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtNSS.setToolTipText("Ingresa el NSS del empleado");
        txtNSS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNSS, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 140, 30));

        txtNacionalidad.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtNacionalidad.setToolTipText("Ingresa la nacionalidad del empleado");
        txtNacionalidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 310, 140, 30));

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton1.setText("Salir");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 90, 30));
        jPanel1.add(jdc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 130, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Masculino", "Femenino" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 162, 140, 30));
        jPanel1.add(txtGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 140, 30));

        lblIDDepto.setBackground(new java.awt.Color(153, 153, 255));
        lblIDDepto.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblIDDepto.setText("ID departamento");
        lblIDDepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblIDDepto.setOpaque(true);
        jPanel1.add(lblIDDepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, 150, 30));

        lblIDCurso.setBackground(new java.awt.Color(153, 153, 255));
        lblIDCurso.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblIDCurso.setText("ID curso");
        lblIDCurso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblIDCurso.setOpaque(true);
        jPanel1.add(lblIDCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 410, 150, 30));

        txtIDDepto.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtIDDepto.setToolTipText("Ingresa la nacionalidad del empleado");
        txtIDDepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtIDDepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 360, 140, 30));

        txtIDCurso.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtIDCurso.setToolTipText("Ingresa la nacionalidad del empleado");
        txtIDCurso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtIDCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 410, 140, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrimerRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimerRegistroActionPerformed
        primerRegistro();
    }//GEN-LAST:event_btnPrimerRegistroActionPerformed

    private void btnAnteriorRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorRegistroActionPerformed
        anteriorRegistro();
    }//GEN-LAST:event_btnAnteriorRegistroActionPerformed

    private void btnSiguienteRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteRegistroActionPerformed
        siguienteRegistro();
    }//GEN-LAST:event_btnSiguienteRegistroActionPerformed

    private void btnUltimoRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoRegistroActionPerformed
        ultimoRegistro();
    }//GEN-LAST:event_btnUltimoRegistroActionPerformed

    private void btnNuevoRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoRegistroActionPerformed
        nuevoRegistro();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnNuevoRegistroActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()) {
        
            manejadorBD.iniciarBloque("Empleado");
            insertaRegistro();
            manejadorBD.veAlPrimerRegistro();
            desactivarCampos();
            muestraRegistroActual();
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);

            JOptionPane.showMessageDialog(this, "Los datos del empleado han sido guardados.");
                manejadorBD.consultaRegistros("Empleado");
                    
    } else {
        JOptionPane.showMessageDialog(this,
            "No se pueden guardar los datos del empleado.",
            "Error al guardar el empleado",
            JOptionPane.ERROR_MESSAGE);
    }
    

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Desea eliminar el registro del empleado?",
            "Confirme su respuesta",
            JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            manejadorBD.borraRegistroActual();
            limpiarRegistro();
            llenarVentana("Empleado");
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del empleado");
        }
        desactivarCampos();

        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desactivarCampos();
        muestraRegistroActual();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        activarCampos();
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
         if (validarCampos()) {  
             
              // Obtener la fecha de nacimiento y formatearla
        java.util.Date fechaNacimiento = jdc1.getDate();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaNacimientoString = formatoFecha.format(fechaNacimiento);     
        
        String genero = jComboBox1.getSelectedItem().toString();


            manejadorBD.insertarCampo("RFC_Empleado", txtRFC.getText());
            manejadorBD.insertarCampo("Sexo", genero);
            manejadorBD.insertarCampo("Nombre", txtNombre.getText());
            manejadorBD.insertarCampo("Apellido_P", txtApellidoP.getText());
            manejadorBD.insertarCampo("Apellido_M", txtApellidoM.getText());
            manejadorBD.insertarCampo("Fecha_Nac", fechaNacimientoString);
            manejadorBD.insertarCampo("Domicilio", txtDomicilio.getText());
            manejadorBD.insertarCampo("Num_Tel", txtNumTel.getText());
            manejadorBD.insertarCampo("Correo", txtEmail.getText());
            manejadorBD.insertarCampo("CURP", txtCURP.getText());
            manejadorBD.insertarCampo("NSS", txtNSS.getText());            
            manejadorBD.insertarCampo("Nacionalidad", txtNacionalidad.getText());
            manejadorBD.insertarCampo("ID_Depto", txtIDDepto.getText());
            manejadorBD.insertarCampo("ID_Curso", txtIDCurso.getText());

            manejadorBD.actualizaRegistroActual();
            
            JOptionPane.showMessageDialog(this, "Los datos del empleado han sido actualizados.");
            llenarVentana("Empleado");
            desactivarCampos();            
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);        
                    
    } else {
        JOptionPane.showMessageDialog(this,
            "No se pueden actualizar los datos del empleado.",
            "Error al actualizar el empleado",
            JOptionPane.ERROR_MESSAGE);
    }
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnRegresarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarMenuActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_PrincipalAdmin(manejadorBD).setVisible(true);
            }
        });

        dispose();
    }//GEN-LAST:event_btnRegresarMenuActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String generoSeleccionado = jComboBox1.getSelectedItem().toString();
    txtGenero.setText(generoSeleccionado);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Empleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAnteriorRegistro;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoRegistro;
    private javax.swing.JButton btnPrimerRegistro;
    private javax.swing.JButton btnRegresarMenu;
    private javax.swing.JButton btnSiguienteRegistro;
    private javax.swing.JButton btnUltimoRegistro;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jdc1;
    private javax.swing.JLabel lblApellidoM;
    private javax.swing.JLabel lblApellidoP;
    private javax.swing.JLabel lblCURP;
    private javax.swing.JLabel lblDomicilio;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFechaNac;
    private javax.swing.JLabel lblIDCurso;
    private javax.swing.JLabel lblIDDepto;
    private javax.swing.JLabel lblNSS;
    private javax.swing.JLabel lblNacionalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumTel;
    private javax.swing.JLabel lblRFC;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTablaEmpleados;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JTextField txtCURP;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFechaNac;
    private javax.swing.JTextField txtGenero;
    private javax.swing.JTextField txtIDCurso;
    private javax.swing.JTextField txtIDDepto;
    private javax.swing.JTextField txtNSS;
    private javax.swing.JTextField txtNacionalidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumTel;
    private javax.swing.JTextField txtRFC;
    // End of variables declaration//GEN-END:variables
}
