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
/**
 *
 * @author Alejandro Castro Duarte
 */
public class Instructor extends javax.swing.JFrame {
    
    private final ManejadorBD manejadorBD;    

    /**
     * Creates new form Instructor
     */
    public Instructor() {
        initComponents();
        this.manejadorBD = new ManejadorBD();
        manejadorBD.conectar("sistemadegestionempresarial");
//        this.manejadorBD.conectar("Tiendita", "root", "root", "localhost", 3306);
        llenarVentana("Instructor");
        
       
    }
    
     public Instructor(ManejadorBD manejadorBD) {
        initComponents();
        this.manejadorBD = manejadorBD;
        llenarVentana("Instructor");
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
            btnPrimero.setEnabled(true);
            btnSiguiente.setEnabled(true);
            btnAnterior.setEnabled(true);
            btnUltimo.setEnabled(true);
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
            txtRFC.setText(manejadorBD.getCampoRegistroActual("RFC_Instructor").toString());
            txtNombre.setText(manejadorBD.getCampoRegistroActual("Nombre").toString());
            txtApellidoP.setText(manejadorBD.getCampoRegistroActual("Apellido_P").toString());
            txtApellidoM.setText(manejadorBD.getCampoRegistroActual("Apellido_M").toString());
            txtFechaNac.setText(manejadorBD.getCampoRegistroActual("Fecha_Nac").toString());
            txtDireccion.setText(manejadorBD.getCampoRegistroActual("Direccion").toString());            
            txtTel.setText(manejadorBD.getCampoRegistroActual("Telefono").toString());
            txtEspecialidad.setText(manejadorBD.getCampoRegistroActual("Especialidad").toString());
            
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
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtFechaNac.setText("");
        txtDireccion.setText("");
        txtTel.setText("");
        txtEspecialidad.setText("");
        jdc1.setDate(null); // Limpiar fecha en el JDateChooser
        
        
    }
    
    private void nuevoRegistro() {
        activarCampos();
        limpiarRegistro();

    }
    
    private void activarCampos() {
        txtRFC.setEditable(true);
        txtNombre.setEditable(true);
        txtApellidoP.setEditable(true);
        txtApellidoM.setEditable(true);
        txtFechaNac.setEditable(false);
        txtDireccion.setEditable(true);
        txtTel.setEditable(true);
        txtEspecialidad.setEditable(true);
        jdc1.setEnabled(true);

    }

    private void desactivarCampos() {
        txtRFC.setEditable(false);
        txtNombre.setEditable(false);
        txtApellidoP.setEditable(false);
        txtApellidoM.setEditable(false);
        txtFechaNac.setEditable(false);
        txtDireccion.setEditable(false);
        txtTel.setEditable(false);
        txtEspecialidad.setEditable(false);
        jdc1.setEnabled(false);        

    }
    
     private boolean validarCampos() {
        
         boolean correcto = true;

    //Validar RFC
    String rfc = txtRFC.getText().trim();
    if (rfc.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "El campo RFC del instructor no puede estar vacío",
                "Error en el campo RFC_Instructor",
                JOptionPane.ERROR_MESSAGE);
        return false;
     //Validar Nombre
}
    if (txtNombre.getText().isEmpty()){
        JOptionPane.showMessageDialog(this,
                "Se debe ingresar el nombre.",
                "Error en el campo Nombre",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validar Apellido Paterno
    if (txtApellidoP.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Se debe ingresar el apellido paterno.",
                "Error en el campo Apellido_P",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validar Apellido Materno
    if (txtApellidoM.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Se debe ingresar el apellido materno.",
                "Error en el campo Apellido_M",
                JOptionPane.ERROR_MESSAGE);
        return false;
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

    // Validar Dirección
    if (txtDireccion.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Se debe ingresar la dirección.",
                "Error en el campo Direccion",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validar Teléfono
    if (txtTel.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingresa el número telefónico del departamento al que te quieres comunicar.",
                "Error en el campo Teléfono",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validar Especialidad
    if (txtEspecialidad.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Introduce la especialidad del instructor.",
                "Error en el campo Especialidad",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return correcto;
}
    private void insertaRegistro() {
        // Primero, validar todos los campos
    if (!validarCampos()){
        // Si la validación falla, no intentar la inserción
        return;
    }
    
    // Obtener la fecha del JDateChooser y convertirla a String
    try{
        java.util.Date fechaNacimiento = jdc1.getDate();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaNacimientoString = formatoFecha.format(fechaNacimiento);
        
        // Iniciar el bloque para la inserción
        manejadorBD.iniciarBloque("Instructor");
        
        manejadorBD.insertarCampo("RFC_Instructor", txtRFC.getText());
        manejadorBD.insertarCampo("Nombre", txtNombre.getText());
        manejadorBD.insertarCampo("Apellido_P", txtApellidoP.getText());
        manejadorBD.insertarCampo("Apellido_M", txtApellidoM.getText());        
        manejadorBD.insertarCampo("Direccion", txtDireccion.getText());
        manejadorBD.insertarCampo("Telefono", txtTel.getText());
        manejadorBD.insertarCampo("Especialidad", txtEspecialidad.getText());        
        manejadorBD.insertarCampo("Fecha_Nac", fechaNacimientoString);
        
        // Cerrar el bloque después de la inserción
        manejadorBD.cerrarBloque();
        
        // Si la inserción es exitosa, limpiar los campos de la interfaz de usuario
        limpiarRegistro();
        llenarVentana("Instructor");
    } catch(Exception e) {
        // Si ocurre un error durante la inserción, mostrar un mensaje de error al usuario
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
        jLabel1 = new javax.swing.JLabel();
        lblRFC = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblApellidoP = new javax.swing.JLabel();
        lblApellidoM = new javax.swing.JLabel();
        lblFechaNac = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTel = new javax.swing.JLabel();
        lblEspecialidad = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidoP = new javax.swing.JTextField();
        txtFechaNac = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTel = new javax.swing.JTextField();
        txtEspecialidad = new javax.swing.JTextField();
        btnUltimo = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnMenuPrincipal = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnPrimero = new javax.swing.JButton();
        txtApellidoM = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        jdc1 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(153, 0, 153));
        jLabel1.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Instructor");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 6, 134, -1));

        lblRFC.setBackground(new java.awt.Color(153, 153, 255));
        lblRFC.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblRFC.setText("RFC del instructor");
        lblRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblRFC.setOpaque(true);
        jPanel1.add(lblRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 52, 151, 32));

        txtRFC.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 51, 234, 36));

        lblNombre.setBackground(new java.awt.Color(153, 153, 255));
        lblNombre.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNombre.setText("Nombre");
        lblNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNombre.setOpaque(true);
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 106, 151, 33));

        lblApellidoP.setBackground(new java.awt.Color(153, 153, 255));
        lblApellidoP.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblApellidoP.setText("Apellido paterno");
        lblApellidoP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblApellidoP.setOpaque(true);
        jPanel1.add(lblApellidoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 157, 151, 35));

        lblApellidoM.setBackground(new java.awt.Color(153, 153, 255));
        lblApellidoM.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblApellidoM.setText("Apellido materno");
        lblApellidoM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblApellidoM.setOpaque(true);
        jPanel1.add(lblApellidoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 151, 35));

        lblFechaNac.setBackground(new java.awt.Color(153, 153, 255));
        lblFechaNac.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblFechaNac.setText("Fecha de nacimiento");
        lblFechaNac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblFechaNac.setOpaque(true);
        jPanel1.add(lblFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, 33));

        lblDireccion.setBackground(new java.awt.Color(153, 153, 255));
        lblDireccion.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblDireccion.setText("Dirección");
        lblDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDireccion.setOpaque(true);
        jPanel1.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 151, 37));

        lblTel.setBackground(new java.awt.Color(153, 153, 255));
        lblTel.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblTel.setText("Telefóno");
        lblTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblTel.setOpaque(true);
        jPanel1.add(lblTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 151, 34));

        lblEspecialidad.setBackground(new java.awt.Color(153, 153, 255));
        lblEspecialidad.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblEspecialidad.setText("Especialidad");
        lblEspecialidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblEspecialidad.setOpaque(true);
        jPanel1.add(lblEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 151, 37));

        txtNombre.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 106, 234, 33));

        txtApellidoP.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtApellidoP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtApellidoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 157, 234, 35));

        txtFechaNac.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtFechaNac.setToolTipText("Introduce desde la opcion de la derecha");
        txtFechaNac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 234, 30));

        txtDireccion.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 234, 37));

        txtTel.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 234, 34));

        txtEspecialidad.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtEspecialidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 490, 234, 37));

        btnUltimo.setBackground(new java.awt.Color(153, 255, 255));
        btnUltimo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnUltimo.setText("Ultimo registro");
        btnUltimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        jPanel1.add(btnUltimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 370, 100, 30));

        btnSiguiente.setBackground(new java.awt.Color(153, 255, 255));
        btnSiguiente.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnSiguiente.setText("Siguiente registro");
        btnSiguiente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 120, 30));

        btnNuevo.setBackground(new java.awt.Color(153, 255, 255));
        btnNuevo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(502, 52, 98, 36));

        btnGuardar.setBackground(new java.awt.Color(153, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 50, 90, 40));

        btnEliminar.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 100, 40));

        btnCancelar.setBackground(new java.awt.Color(153, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, 90, 40));

        btnEditar.setBackground(new java.awt.Color(153, 255, 255));
        btnEditar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(502, 169, 100, 40));

        btnActualizar.setBackground(new java.awt.Color(153, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, 90, 40));

        btnMenuPrincipal.setBackground(new java.awt.Color(153, 255, 255));
        btnMenuPrincipal.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnMenuPrincipal.setText("Menú principal");
        btnMenuPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPrincipalActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenuPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 240, 130, 40));

        btnAnterior.setBackground(new java.awt.Color(153, 255, 255));
        btnAnterior.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnAnterior.setText("Anterior registro");
        btnAnterior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 120, 30));

        btnPrimero.setBackground(new java.awt.Color(153, 255, 255));
        btnPrimero.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnPrimero.setText("Primer registro");
        btnPrimero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 120, 30));

        txtApellidoM.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtApellidoM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtApellidoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 210, 234, 35));

        btnSalir.setBackground(new java.awt.Color(255, 102, 0));
        btnSalir.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 100, -1));

        jdc1.setToolTipText("Selecciona tu fecha de nacimiento");
        jPanel1.add(jdc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 150, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
       if (validarCampos()) {
        // Obtener la fecha de nacimiento y formatearla
        java.util.Date fechaNacimiento = jdc1.getDate();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaNacimientoString = formatoFecha.format(fechaNacimiento);           
        
        // Actualiza el registro actual en la base de datos
        manejadorBD.insertarCampo("RFC_Instructor", txtRFC.getText());
        manejadorBD.insertarCampo("Nombre", txtNombre.getText());
        manejadorBD.insertarCampo("Apellido_P", txtApellidoP.getText());
        manejadorBD.insertarCampo("Apellido_M", txtApellidoM.getText());        
        manejadorBD.insertarCampo("Direccion", txtDireccion.getText());
        manejadorBD.insertarCampo("Telefono", txtTel.getText());
        manejadorBD.insertarCampo("Especialidad", txtEspecialidad.getText());
        manejadorBD.insertarCampo("Fecha_Nac", fechaNacimientoString);
        
        manejadorBD.actualizaRegistroActual();

        // Muestra un mensaje de éxito y realiza otras operaciones
        JOptionPane.showMessageDialog(this, "Los datos del instructor han sido actualizados.");
        llenarVentana("Instructor");
        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    } else {
        // Muestra un mensaje de error si la validación de campos falla
        JOptionPane.showMessageDialog(this,
                "No se pueden actualizar los datos del Instructor.",
                "Error al actualizar los datos del instructor",
                JOptionPane.ERROR_MESSAGE);

       }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        primerRegistro();
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        anteriorRegistro();
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        siguienteRegistro();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        ultimoRegistro();
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoRegistro();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()){
        manejadorBD.iniciarBloque("Instructor");
        insertaRegistro();
        
        manejadorBD.veAlPrimerRegistro();
        desactivarCampos();
        muestraRegistroActual();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
        
        JOptionPane.showMessageDialog(this, "Los datos del instructor han sido guardados.");
        manejadorBD.consultaRegistros("Instructor");
    } else {
        JOptionPane.showMessageDialog(this,
                "No se pueden guardar los datos del instructor.",
                "Error al guardar el instructor",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro del instructor?",
                "Confirme su respuesta",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            manejadorBD.borraRegistroActual();
            limpiarRegistro();
            llenarVentana("Instructor");
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del instructor");
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

    private void btnMenuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuPrincipalActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_PrincipalAdmin(manejadorBD).setVisible(true);
            }
        });

        dispose();
    }//GEN-LAST:event_btnMenuPrincipalActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

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
            java.util.logging.Logger.getLogger(Instructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Instructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Instructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Instructor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Instructor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMenuPrincipal;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jdc1;
    private javax.swing.JLabel lblApellidoM;
    private javax.swing.JLabel lblApellidoP;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblFechaNac;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRFC;
    private javax.swing.JLabel lblTel;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtFechaNac;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
