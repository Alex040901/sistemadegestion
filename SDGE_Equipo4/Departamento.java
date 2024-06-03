/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SDGE_Equipo4;

import Manejador.ManejadorBD;
import java.sql.SQLException;
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

/**
 *
 * @author Alejandro Castro Duarte
 */
public class Departamento extends javax.swing.JFrame {

    private final ManejadorBD manejadorBD;
    /**
     * Creates new form Departamento
     */
    public Departamento() {
        initComponents();
        this.manejadorBD = new ManejadorBD();
        manejadorBD.conectar("sistemadegestionempresarial");

        llenarVentana("Departamento");   
    }   

    public Departamento(ManejadorBD manejadorBD) {
        initComponents();
        this.manejadorBD = manejadorBD;
        llenarVentana("Departamento");
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
            txtIDDepto.setText(manejadorBD.getCampoRegistroActual("ID_Depto").toString());
            txtNombre.setText(manejadorBD.getCampoRegistroActual("Nombre_Depto").toString());
            txtUbicacion.setText(manejadorBD.getCampoRegistroActual("Ubicacion").toString());
            txtTel.setText(manejadorBD.getCampoRegistroActual("Telefono").toString());
            //txtEncargado.setText(manejadorBD.getCampoRegistroActual("Nombre_Encargado").toString());
            txtEmail.setText(manejadorBD.getCampoRegistroActual("Correo").toString());
//            txtNombre.setText(manejadorBD.getCampoRegistroActual("Nombre").toString());
//            txtApellidoP.setText(manejadorBD.getCampoRegistroActual("ApellidoP").toString());
                            
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
        txtIDDepto.setText("");
        txtNombre.setText("");
        txtUbicacion.setText("");
        txtTel.setText("");
        //txtEncargado.setText("");
        txtEmail.setText("");
        

    }

    private void nuevoRegistro() {
        activarCampos();
        limpiarRegistro();

    }

    private void activarCampos() {
        txtIDDepto.setEditable(false);
        txtNombre.setEditable(true);
        txtUbicacion.setEditable(true);
        txtTel.setEditable(true);
        //txtEncargado.setEditable(true);
        txtEmail.setEditable(true);
        

    }

    private void desactivarCampos() {
        txtIDDepto.setEditable(false);
        txtNombre.setEditable(false);
        txtUbicacion.setEditable(false);
        txtTel.setEditable(false);
        //txtEncargado.setEditable(false);
        txtEmail.setEditable(false);
        


    }

    private boolean validarCampos() {
        
        boolean correcto = true;
        //try {
            //Integer.parseInt(txtIDDepto.getText()); //numeros Enteros
            //Float.parseFloat(txtCredito.getText());//nmeros decimales
        //} catch (NumberFormatException nfe) {
            //JOptionPane.showMessageDialog(this,
                    //"Se debe introducir un valor entero para el id del Departamento",
                    //"Error en el campo ID_Depto",
                    //JOptionPane.ERROR_MESSAGE);
            //correcto = false;
            //return correcto;
        
        
         if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se deben introducir el nombre del departamento.",
                    "Error en el campo Nombre_Depto",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
        }
         
         if (txtUbicacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar una breve descripcion del departamento.",
                    "Error en el campo Descripcion",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
    }
         
         if (txtTel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa el numero telefonico del departamento al que te quieres comunicar.",
                    "Error en el campo Telefonno",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            return correcto;
    //}else if (txtTel.getText().length() != 2) {
            //JOptionPane.showMessageDialog(this,
                    //"Se deben introducir solo 2 caracteres para la extension del departamento.",
                    //"Error en el campo Tel_Ext",
                    //JOptionPane.ERROR_MESSAGE);
            //correcto = false;
            //return correcto;
    }         
    
        if (txtEmail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Introduce el Email del empleado.",
                        "Error en el campo Correo",
                        JOptionPane.ERROR_MESSAGE);
                correcto = false;
                return correcto;
    }
    
       return correcto;
    }
    private void insertaRegistro() {
        
        if(!validarCampos()){
            return;
        }
        
        try{
            
        manejadorBD.iniciarBloque("Departamento");
        //manejadorBD.insertarCampo("ID_Depto", txtIDDepto.getText());
        manejadorBD.insertarCampo("Nombre_Depto", txtNombre.getText());
        manejadorBD.insertarCampo("Ubicacion", txtUbicacion.getText());
        manejadorBD.insertarCampo("Telefono", txtTel.getText());
        //manejadorBD.insertarCampo("Nombre_Encargado", txtEncargado.getText());
        manejadorBD.insertarCampo("Correo", txtEmail.getText());
        
        manejadorBD.cerrarBloque();
        
        limpiarRegistro();
            llenarVentana("Departamento");
        
        }catch(Exception e){
            
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

        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnPrimero = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        lblTelefono = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        txtUbicacion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtIDDepto = new javax.swing.JTextField();
        lblUbicacion = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblIDDepto = new javax.swing.JLabel();
        btnMenuPrincipal = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        lblCorreo1 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setBackground(new java.awt.Color(153, 0, 153));
        lblTitulo.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Empleado.png"))); // NOI18N
        lblTitulo.setText("Tabla departamento");
        lblTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        lblTitulo.setOpaque(true);
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSiguiente.setBackground(new java.awt.Color(153, 255, 255));
        btnSiguiente.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnSiguiente.setText("Siguiente registro");
        btnSiguiente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 140, 30));

        btnUltimo.setBackground(new java.awt.Color(153, 255, 255));
        btnUltimo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnUltimo.setText("Ultimo registro");
        btnUltimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        jPanel1.add(btnUltimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 140, 30));

        btnPrimero.setBackground(new java.awt.Color(153, 255, 255));
        btnPrimero.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnPrimero.setText("Primer registro");
        btnPrimero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 140, 30));

        btnAnterior.setBackground(new java.awt.Color(153, 255, 255));
        btnAnterior.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnAnterior.setText("Anterior registro");
        btnAnterior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 140, 30));

        lblTelefono.setBackground(new java.awt.Color(153, 153, 255));
        lblTelefono.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblTelefono.setText("Telefono");
        lblTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblTelefono.setOpaque(true);
        jPanel1.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, 30));

        txtTel.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtTel.setToolTipText("Ingresa la extension del depto");
        txtTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 140, 30));

        txtUbicacion.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtUbicacion.setToolTipText("Ingresa la descripcion del depto");
        txtUbicacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 140, 30));

        txtNombre.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtNombre.setToolTipText("Ingresa el nombre del depto");
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 140, 30));

        txtIDDepto.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtIDDepto.setToolTipText("Ingresa el ID del departamento");
        txtIDDepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtIDDepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 140, 30));

        lblUbicacion.setBackground(new java.awt.Color(153, 153, 255));
        lblUbicacion.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblUbicacion.setText("Ubicacion");
        lblUbicacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblUbicacion.setOpaque(true);
        jPanel1.add(lblUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 150, 30));

        lblNombre.setBackground(new java.awt.Color(153, 153, 255));
        lblNombre.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblNombre.setText("Nombre");
        lblNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblNombre.setOpaque(true);
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 30));

        lblIDDepto.setBackground(new java.awt.Color(153, 153, 255));
        lblIDDepto.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblIDDepto.setText("ID departamento");
        lblIDDepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblIDDepto.setOpaque(true);
        jPanel1.add(lblIDDepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, 30));

        btnMenuPrincipal.setBackground(new java.awt.Color(153, 255, 255));
        btnMenuPrincipal.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnMenuPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Regresar.png"))); // NOI18N
        btnMenuPrincipal.setText("Menu principal");
        btnMenuPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPrincipalActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenuPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, 150, 40));

        btnActualizar.setBackground(new java.awt.Color(153, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(391, 330, 150, -1));

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
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 380, 150, -1));

        btnNuevo.setBackground(new java.awt.Color(153, 255, 255));
        btnNuevo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(392, 63, 150, 30));

        btnEditar.setBackground(new java.awt.Color(153, 255, 255));
        btnEditar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 150, -1));

        btnCancelar.setBackground(new java.awt.Color(153, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 150, -1));

        btnGuardar.setBackground(new java.awt.Color(153, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manejador/Aceptar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 150, -1));

        lblCorreo1.setBackground(new java.awt.Color(153, 153, 255));
        lblCorreo1.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        lblCorreo1.setText("Email");
        lblCorreo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblCorreo1.setOpaque(true);
        jPanel1.add(lblCorreo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 150, 30));

        txtEmail.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtEmail.setToolTipText("Ingresa el total de empleados");
        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 140, 30));

        btnSalir.setBackground(new java.awt.Color(255, 102, 0));
        btnSalir.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnMenuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuPrincipalActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_PrincipalAdmin(manejadorBD).setVisible(true);
            }
        });

        dispose();
    }//GEN-LAST:event_btnMenuPrincipalActionPerformed

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
        if (validarCampos()) {
        manejadorBD.iniciarBloque("Departamento");
        insertaRegistro();
        manejadorBD.veAlPrimerRegistro();
        desactivarCampos();
        muestraRegistroActual();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);       
        
        JOptionPane.showMessageDialog(this, "Los datos del departamento han sido guardados.");
        manejadorBD.consultaRegistros("Departamento");
       
    } else {
        JOptionPane.showMessageDialog(this,
                "No se pueden guardar los datos del departamento.",
                "Error al guardar el departamento",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro del departamento?",
                "Confirme su respuesta",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            manejadorBD.borraRegistroActual();
            limpiarRegistro();
            llenarVentana("Departamento");
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del departamento");
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
            
        manejadorBD.insertarCampo("Nombre_Depto", txtNombre.getText());
        manejadorBD.insertarCampo("Ubicacion", txtUbicacion.getText());
        manejadorBD.insertarCampo("Telefono", txtTel.getText());
        //manejadorBD.insertarCampo("Nombre_Encargado", txtEncargado.getText());
        manejadorBD.insertarCampo("Correo", txtEmail.getText());
        
        manejadorBD.actualizaRegistroActual();
      
            
            //insertaRegistro();
            JOptionPane.showMessageDialog(this, "Los datos del departamento han sido actualizados.");            
            llenarVentana("Departamento");
            desactivarCampos();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pueden actualizar los datos del departamento.",
                    "Error al actualizar el departamento",
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
            java.util.logging.Logger.getLogger(Departamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Departamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Departamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Departamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Departamento().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCorreo1;
    private javax.swing.JLabel lblIDDepto;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUbicacion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIDDepto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txtUbicacion;
    // End of variables declaration//GEN-END:variables
}
