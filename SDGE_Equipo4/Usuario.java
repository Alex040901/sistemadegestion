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
import javax.swing.JComboBox;
import javax.swing.*;


/**
 *
 * @author Alejandro Castro Duarte
 */
public class Usuario extends javax.swing.JFrame {
    
    private final ManejadorBD manejadorBD;  

    /**
     * Creates new form Usuario
     */
    public Usuario() {
        initComponents();
        this.manejadorBD = new ManejadorBD();
        manejadorBD.conectar("sistemadegestionempresarial");

        llenarVentana("Usuario");
        
        RellenarCombos re = new RellenarCombos(manejadorBD);
        re.RellenarCombos("empleado", "RFC_Empleado", jComboBox1);
    }
    
    public Usuario(ManejadorBD manejadorBD) {
        initComponents();
        this.manejadorBD = manejadorBD;
        llenarVentana("Usuario");
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
            txtRFC.setText(manejadorBD.getCampoRegistroActual("RFC_Empleado").toString());
            txtContraseña.setText(manejadorBD.getCampoRegistroActual("Contraseña").toString());
            txtRol.setText(manejadorBD.getCampoRegistroActual("Rol").toString());   
            txtUsuario.setText(manejadorBD.getCampoRegistroActual("NombreUser").toString());   
            //txtNombreUser.setText(manejadorBD.getCampoRegistroActual("Nom_Usuario").toString());     
            
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
        //txtNombreUser.setText("");
        txtContraseña.setText("");
        txtRol.setText("");
        txtRFC.setText("");
        txtUsuario.setText("");
        
        
    }
    
    private void nuevoRegistro() {
        activarCampos();
        limpiarRegistro();

    }
    
    private void activarCampos() {
        txtRFC.setEditable(false);
        jComboBox1.setVisible(true);
        txtContraseña.setEditable(true);
        txtRol.setEditable(true);
        txtUsuario.setEditable(true);
        jComboBox1.setVisible(true);
                        
    }
    
    private void desactivarCampos() {
        txtRFC.setEditable(false);
        jComboBox1.setVisible(false);
        txtContraseña.setEditable(false);
        txtRol.setEditable(false);  
        txtUsuario.setEditable(false);
        jComboBox1.setVisible(false);

    }
    
     private boolean validarCampos() {
        
        boolean correcto = true;
        
        
        
         if (txtContraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se deben introducir la contraseña para el usuario.",
                    "Error en el campo Contraseña",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            
        }          
         
         if (txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar el nombre de usuario.",
                    "Error en el campo Nombre de usuario",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            
    }                    
         
         if (txtRol.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar el rol del usuario.",
                    "Error en el campo Rol",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            
    }                    
         
         if (txtRFC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar el RFC del usuario.",
                    "Error en el campo RFC",
                    JOptionPane.ERROR_MESSAGE);
            correcto = false;
            
    }                    
    
       return correcto;
    }
    private void insertaRegistro(){
        
       if(!validarCampos()){
            return;
        }
        
        try{
            
            String rfcEmpleado = jComboBox1.getSelectedItem().toString();
            
            
        manejadorBD.iniciarBloque("Usuario");
        //manejadorBD.insertarCampo("ID_Depto", txtIDDepto.getText());
        //manejadorBD.insertarCampo("Nombre_Depto", txtNombre.getText());
        manejadorBD.insertarCampo("Contraseña", txtContraseña.getText());
        manejadorBD.insertarCampo("Rol", txtRol.getText());
        manejadorBD.insertarCampo("NombreUser", txtUsuario.getText());
        manejadorBD.insertarCampo("RFC_Empleado", rfcEmpleado);
        
        manejadorBD.cerrarBloque();
        
        limpiarRegistro();
            llenarVentana("Usuario");
        
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblRFC = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        txtRol = new javax.swing.JTextField();
        btnUltimo = new javax.swing.JButton();
        btnPrimero = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnMenuPrincipal = new javax.swing.JButton();
        txtContraseña = new javax.swing.JPasswordField();
        btnSiguiente = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jcbMostrar = new javax.swing.JCheckBox();
        btnSalir = new javax.swing.JButton();
        lblContraseña1 = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(153, 0, 204));
        jLabel1.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Usuario");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 140, 36));

        lblRFC.setBackground(new java.awt.Color(153, 153, 255));
        lblRFC.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        lblRFC.setText("RFC del usuario");
        lblRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblRFC.setOpaque(true);
        jPanel1.add(lblRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 160, 40));

        lblRol.setBackground(new java.awt.Color(153, 153, 255));
        lblRol.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        lblRol.setText("Rol");
        lblRol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblRol.setOpaque(true);
        jPanel1.add(lblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 160, 40));

        txtRol.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtRol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 230, 40));

        btnUltimo.setBackground(new java.awt.Color(102, 255, 255));
        btnUltimo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnUltimo.setText("Ultimo registro");
        btnUltimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        jPanel1.add(btnUltimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 440, 150, 30));

        btnPrimero.setBackground(new java.awt.Color(102, 255, 255));
        btnPrimero.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnPrimero.setText("Primer registro");
        btnPrimero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 140, 30));

        btnEliminar.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, 110, 30));

        btnNuevo.setBackground(new java.awt.Color(102, 255, 255));
        btnNuevo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 100, 30));

        btnActualizar.setBackground(new java.awt.Color(102, 255, 255));
        btnActualizar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 100, 30));

        btnMenuPrincipal.setBackground(new java.awt.Color(102, 255, 255));
        btnMenuPrincipal.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnMenuPrincipal.setText("Menu principal");
        btnMenuPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPrincipalActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenuPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, 110, 30));

        txtContraseña.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtContraseña.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 230, 40));

        btnSiguiente.setBackground(new java.awt.Color(102, 255, 255));
        btnSiguiente.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnSiguiente.setText("Siguiente registro");
        btnSiguiente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 440, 140, 30));

        btnAnterior.setBackground(new java.awt.Color(102, 255, 255));
        btnAnterior.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnAnterior.setText("Anterior registro");
        btnAnterior.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 150, 30));

        btnGuardar.setBackground(new java.awt.Color(102, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 100, 30));

        btnCancelar.setBackground(new java.awt.Color(102, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, 100, 30));

        btnEditar.setBackground(new java.awt.Color(102, 255, 255));
        btnEditar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, 100, 30));

        jcbMostrar.setBackground(new java.awt.Color(153, 255, 255));
        jcbMostrar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jcbMostrar.setText("Mostrar contraseña");
        jcbMostrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jcbMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMostrarActionPerformed(evt);
            }
        });
        jPanel1.add(jcbMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 140, 20));

        btnSalir.setBackground(new java.awt.Color(255, 102, 0));
        btnSalir.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 370, 100, 30));

        lblContraseña1.setBackground(new java.awt.Color(153, 153, 255));
        lblContraseña1.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        lblContraseña1.setText("Contraseña");
        lblContraseña1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblContraseña1.setOpaque(true);
        jPanel1.add(lblContraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 160, 40));

        txtRFC.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 230, 40));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccionar-" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 230, 30));

        lblUsuario.setBackground(new java.awt.Color(153, 153, 255));
        lblUsuario.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        lblUsuario.setText("Usuario");
        lblUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblUsuario.setOpaque(true);
        jPanel1.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 160, 40));

        txtUsuario.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
        txtUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 230, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        primerRegistro();
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        siguienteRegistro();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        ultimoRegistro();
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        anteriorRegistro();
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarCampos()) {
            
        String rfcEmpleado = jComboBox1.getSelectedItem().toString();  
            
        manejadorBD.insertarCampo("Contraseña", txtContraseña.getText());
        manejadorBD.insertarCampo("NombreUser", txtUsuario.getText());
        manejadorBD.insertarCampo("Rol", txtRol.getText());
        manejadorBD.insertarCampo("RFC_Empleado", rfcEmpleado);
        
        manejadorBD.actualizaRegistroActual();
      
            
            //insertaRegistro();
            JOptionPane.showMessageDialog(this, "Los datos del usuariao han sido actualizados.");            
            llenarVentana("Usuario");
            desactivarCampos();
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pueden actualizar los datos del usuario.",
                    "Error al actualizar el usuario",
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoRegistro();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()){
            
            manejadorBD.iniciarBloque("Usuario");
            
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
            JOptionPane.showMessageDialog(this, "Los datos del usuario han sido guardados.");
            
            // Actualizar registros
            manejadorBD.consultaRegistros("Usuario");
                
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor corrige los errores antes de guardar.",
                    "Error al guardar el usuario",
                    JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro del usuario?",
                "Confirme su respuesta",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == 0) {
            manejadorBD.borraRegistroActual();
            limpiarRegistro();
            llenarVentana("Usuario");
        } else {
            JOptionPane.showMessageDialog(this, "Se canceló la eliminación del registro del usuario");
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

    private void jcbMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMostrarActionPerformed
        JCheckBox comboBox = (JCheckBox) evt.getSource();
    if (comboBox.isSelected()) {
        txtContraseña.setEchoChar((char) 0); // Mostrar la contraseña
    } else {
        txtContraseña.setEchoChar('*'); // Ocultar la contraseña
    }
    }//GEN-LAST:event_jcbMostrarActionPerformed

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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String rfcempleado = jComboBox1.getSelectedItem().toString();
    txtRFC.setText(rfcempleado);
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
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario().setVisible(true);
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
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox jcbMostrar;
    private javax.swing.JLabel lblContraseña1;
    private javax.swing.JLabel lblRFC;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtRFC;
    private javax.swing.JTextField txtRol;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
