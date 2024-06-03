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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 *
 * @author Alejandro Castro Duarte
 */
public class Curso extends javax.swing.JFrame {

    private final ManejadorBD manejadorBD;
    /**
     * Creates new form Curso
     */
    public Curso() {
        initComponents();        
        this.manejadorBD = new ManejadorBD();
        manejadorBD.conectar("sistemadegestionempresarial");
        llenarVentana("Curso"); 
    
        RellenarCombos re = new RellenarCombos(manejadorBD);
        re.RellenarCombos("ubicacion", "Nombre", jComboBox1);        
    
    }
    public Curso(ManejadorBD manejadorBD) {
        initComponents();
        this.manejadorBD = manejadorBD;
        llenarVentana("Curso");
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
            txtIDCurso.setText(manejadorBD.getCampoRegistroActual("ID_Curso").toString());
            txtNombre.setText(manejadorBD.getCampoRegistroActual("Nombre_Curso").toString());            
            txtProposito.setText(manejadorBD.getCampoRegistroActual("Proposito").toString());
            txtLugar.setText(manejadorBD.getCampoRegistroActual("Lugar").toString());
            txtHorario.setText(manejadorBD.getCampoRegistroActual("Horario").toString());
            txtDuracion.setText(manejadorBD.getCampoRegistroActual("Duracion").toString());
            txtDisponibilidad.setText(manejadorBD.getCampoRegistroActual("Disponibilidad").toString());
            
            
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
        txtIDCurso.setText("");
        txtNombre.setText("");        
        txtProposito.setText("");
        txtLugar.setText("");
        txtHorario.setText("");
        txtDuracion.setText("");
        txtDisponibilidad.setText("");
        

        }

    private void nuevoRegistro() {
        activarCampos();
        limpiarRegistro();

    }

    private void activarCampos() {
        txtIDCurso.setEditable(false);
        txtNombre.setEditable(true);        
        txtProposito.setEditable(true);
        txtLugar.setEditable(true);
        txtHorario.setEditable(true);
        txtDuracion.setEditable(true);
        txtDisponibilidad.setEditable(true);        
        jComboBox1.setVisible(true);

    }

    private void desactivarCampos() {
        txtIDCurso.setEditable(false);
        txtNombre.setEditable(false);        
        txtProposito.setEditable(false);
        txtLugar.setEditable(false);
        txtHorario.setEditable(false);
        txtDuracion.setEditable(false);
        txtDisponibilidad.setEditable(false);        
        jComboBox1.setVisible(false);
    }
    
    private boolean validarCampos() {
        
        boolean correcto = true;

    if (txtNombre.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Se debe introducir el nombre del curso.",
                "Error en el campo curso",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;        
    }       

    if (txtProposito.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingresa cuál es el propósito del curso.",
                "Error en el campo Propósito",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
    
    if (txtLugar.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingresa cuál es el lugar del curso.",
                "Error en el campo lugar",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
    

    if (txtHorario.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingresa el Horario.",
                "Error en el campo Horario",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }
    
    if (txtDisponibilidad.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingresa el Horario.",
                "Error en el campo Horario",
                JOptionPane.ERROR_MESSAGE);
        correcto = false;
    }

    return correcto;
   
    }
    
    private void insertaRegistro() {
        
        if(!validarCampos()){
            
            return;
}

        try{
            
        String ubicacion = jComboBox1.getSelectedItem().toString();
            
        manejadorBD.iniciarBloque("Curso");
        //manejadorBD.insertarCampo("ID_Curso", txtIDCurso.getText());
        manejadorBD.insertarCampo("Nombre_Curso", txtNombre.getText());
        manejadorBD.insertarCampo("Proposito", txtProposito.getText());
        manejadorBD.insertarCampo("Lugar", ubicacion);
        manejadorBD.insertarCampo("Horario", txtHorario.getText());
        manejadorBD.insertarCampo("Disponibilidad", txtDisponibilidad.getText());
        manejadorBD.insertarCampo("Duracion", txtDuracion.getText());        
        
        manejadorBD.cerrarBloque();
        
        limpiarRegistro();
            llenarVentana("Curso");
        
        JOptionPane.showMessageDialog(this,
                "Registro insertado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        
        
            
        }catch(Exception e){
             // Si ocurre un error durante la inserción, mostrar un mensaje de error al usuario
        JOptionPane.showMessageDialog(this,
                "Error al insertar el registro en la base de datos: " + e.getMessage(),
                "Error de incersion",
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

        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnPrimero = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        lblDisponibilidad = new javax.swing.JLabel();
        txtHorario = new javax.swing.JTextField();
        lblLugar = new javax.swing.JLabel();
        lblProposito = new javax.swing.JLabel();
        txtProposito = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtIDCurso = new javax.swing.JTextField();
        lblIDCurso = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        lblDuracion = new javax.swing.JLabel();
        txtDisponibilidad = new javax.swing.JTextField();
        lblHorario = new javax.swing.JLabel();
        txtDuracion = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtLugar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setBackground(new java.awt.Color(153, 0, 153));
        lblTitulo.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Empleado.png"))); // NOI18N
        lblTitulo.setText("Tabla curso");
        lblTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        lblTitulo.setOpaque(true);
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPrimero.setBackground(new java.awt.Color(102, 255, 255));
        btnPrimero.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnPrimero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Primero1.png"))); // NOI18N
        btnPrimero.setText("Primer registro");
        btnPrimero.setToolTipText("Ir al primer registro");
        btnPrimero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, -1, -1));

        btnAnterior.setBackground(new java.awt.Color(102, 255, 255));
        btnAnterior.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Anterior.png"))); // NOI18N
        btnAnterior.setText("Anterior registro");
        btnAnterior.setToolTipText("Ir al registro anterior");
        btnAnterior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, -1, -1));

        btnSiguiente.setBackground(new java.awt.Color(102, 255, 255));
        btnSiguiente.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Next.png"))); // NOI18N
        btnSiguiente.setText("Siguiente registro");
        btnSiguiente.setToolTipText("Ir al siguiente registro");
        btnSiguiente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, -1, -1));

        btnUltimo.setBackground(new java.awt.Color(102, 255, 255));
        btnUltimo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnUltimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Ultimo.png"))); // NOI18N
        btnUltimo.setText("Ultimo registro");
        btnUltimo.setToolTipText("Ir al ultimo registro");
        btnUltimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        jPanel1.add(btnUltimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 480, -1, -1));

        lblDisponibilidad.setBackground(new java.awt.Color(153, 153, 255));
        lblDisponibilidad.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblDisponibilidad.setText("Disponibilidad");
        lblDisponibilidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDisponibilidad.setOpaque(true);
        jPanel1.add(lblDisponibilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 140, 30));

        txtHorario.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtHorario.setToolTipText("Ingresa el horario del curso");
        txtHorario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 160, 30));

        lblLugar.setBackground(new java.awt.Color(153, 153, 255));
        lblLugar.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblLugar.setText("Lugar");
        lblLugar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblLugar.setOpaque(true);
        jPanel1.add(lblLugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 140, 30));

        lblProposito.setBackground(new java.awt.Color(153, 153, 255));
        lblProposito.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblProposito.setText("Proposito");
        lblProposito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblProposito.setOpaque(true);
        jPanel1.add(lblProposito, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 140, 30));

        txtProposito.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtProposito.setToolTipText("Ingresa el proposito del curso");
        txtProposito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtProposito, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 162, 30));

        lblNombre.setBackground(new java.awt.Color(153, 153, 255));
        lblNombre.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNombre.setText("Nombre del curso");
        lblNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNombre.setOpaque(true);
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 140, 30));

        txtNombre.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtNombre.setToolTipText("Ingresa el nombre del curso");
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 162, 30));

        txtIDCurso.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtIDCurso.setToolTipText("Ingresa el ID del curso");
        txtIDCurso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtIDCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 162, 30));

        lblIDCurso.setBackground(new java.awt.Color(153, 153, 255));
        lblIDCurso.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblIDCurso.setText("ID Curso");
        lblIDCurso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblIDCurso.setOpaque(true);
        jPanel1.add(lblIDCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, 30));

        btnActualizar.setBackground(new java.awt.Color(102, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 110, 30));

        btnEditar.setBackground(new java.awt.Color(102, 255, 255));
        btnEditar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 110, 30));

        btnEliminar.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 110, 30));

        btnGuardar.setBackground(new java.awt.Color(102, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 110, 30));

        btnCancelar.setBackground(new java.awt.Color(102, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 110, -1));

        btnNuevo.setBackground(new java.awt.Color(102, 255, 255));
        btnNuevo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 110, 30));

        lblDuracion.setBackground(new java.awt.Color(153, 153, 255));
        lblDuracion.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblDuracion.setText("Duracion");
        lblDuracion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblDuracion.setOpaque(true);
        jPanel1.add(lblDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 140, 30));

        txtDisponibilidad.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtDisponibilidad.setToolTipText("Ingresa la disponibilidad del curso");
        txtDisponibilidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtDisponibilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 160, 30));

        lblHorario.setBackground(new java.awt.Color(153, 153, 255));
        lblHorario.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblHorario.setText("Horario");
        lblHorario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblHorario.setOpaque(true);
        jPanel1.add(lblHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 140, 30));

        txtDuracion.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtDuracion.setToolTipText("Ingresa la duracion del curso");
        txtDuracion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 160, 30));

        btnSalir.setBackground(new java.awt.Color(255, 102, 0));
        btnSalir.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 100, 30));

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 200, 30));

        txtLugar.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        jPanel1.add(txtLugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 200, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()) {
        
            // Iniciar transacción
            manejadorBD.iniciarBloque("Curso");
            
            // Insertar el registro
            insertaRegistro();
            
            // Mover al primer registro después de insertar
            manejadorBD.veAlPrimerRegistro();
            
            // Desactivar campos y botones
            desactivarCampos();
            muestraRegistroActual();
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnActualizar.setEnabled(false);

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(this, "Los datos del Curso han sido guardados.");
            
            // Actualizar registros
            manejadorBD.consultaRegistros("Curso");
            
        
    } else {
        // Mensaje de error si la validación de campos falla
        JOptionPane.showMessageDialog(this,
                "No se pueden guardar los datos del curso. Por favor, complete todos los campos requeridos.",
                "Error al guardar el curso",
                JOptionPane.ERROR_MESSAGE);         
}
    }//GEN-LAST:event_btnGuardarActionPerformed

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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro del curso?",
                "Confirme su respuesta",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            manejadorBD.borraRegistroActual();
            limpiarRegistro();
            llenarVentana("Curso");
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del curso");
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

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarCampos()) {
            
        String ubicacion = jComboBox1.getSelectedItem().toString();
        
        // Actualiza el registro actual en la base de datos
         //manejadorBD.insertarCampo("ID_Curso", txtIDCurso.getText());
        manejadorBD.insertarCampo("Nombre_Curso", txtNombre.getText());
        manejadorBD.insertarCampo("Proposito", txtProposito.getText());
        manejadorBD.insertarCampo("Lugar", ubicacion);
        manejadorBD.insertarCampo("Horario", txtHorario.getText());
        manejadorBD.insertarCampo("Disponibilidad", txtDisponibilidad.getText());
        manejadorBD.insertarCampo("Duracion", txtDuracion.getText());        
            
        manejadorBD.actualizaRegistroActual();

        // Muestra un mensaje de éxito y realiza otras operaciones
        JOptionPane.showMessageDialog(this, "Los datos del instructor han sido actualizados.");
        llenarVentana("Curso");
        desactivarCampos();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
    } else {
        // Muestra un mensaje de error si la validación de campos falla
        JOptionPane.showMessageDialog(this,
                "No se pueden actualizar los datos del curso.",
                "Error al actualizar los datos del curso",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        activarCampos();
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String lugarSeleccionado = jComboBox1.getSelectedItem().toString();
    txtLugar.setText(lugarSeleccionado);
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
            java.util.logging.Logger.getLogger(Curso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Curso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Curso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Curso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Curso().setVisible(true);
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
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDisponibilidad;
    private javax.swing.JLabel lblDuracion;
    private javax.swing.JLabel lblHorario;
    private javax.swing.JLabel lblIDCurso;
    private javax.swing.JLabel lblLugar;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblProposito;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtDisponibilidad;
    private javax.swing.JTextField txtDuracion;
    private javax.swing.JTextField txtHorario;
    private javax.swing.JTextField txtIDCurso;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtProposito;
    // End of variables declaration//GEN-END:variables
}
