/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1p2q4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author josue
 */
public class javalook {

    private static EmailAccount[] cuentas = new EmailAccount[50]; 
    private static EmailAccount cuentaActual = null; 
    private static int cuentaIndex = 0; 
    private static JTextField nameField;
    private static JFrame frame;
    private static JTextArea textArea;
    private static JTextField emailField, passwordField, subjectField, contentField, recipientField, positionField;

    public static void main(String[] args) {
        crearInterfaz();
    }

    private static void crearInterfaz() {
        frame = new JFrame("Gestión de Correos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

       
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Nombre:")); 
        nameField = new JTextField(); 
        panel.add(nameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        JButton createButton = new JButton("Crear Cuenta");
        createButton.addActionListener(e -> crearCuenta());
        panel.add(createButton);

        
        panel.add(new JLabel("Destinatario:"));
        recipientField = new JTextField();
        panel.add(recipientField);

        panel.add(new JLabel("Asunto:"));
        subjectField = new JTextField();
        panel.add(subjectField);

        panel.add(new JLabel("Contenido:"));
        contentField = new JTextField();
        panel.add(contentField);

        JButton sendButton = new JButton("Enviar Correo");
        sendButton.addActionListener(e -> enviarCorreo());
        panel.add(sendButton);

       
        panel.add(new JLabel("Leer Correo en posición:"));
        positionField = new JTextField();
        panel.add(positionField);

        JButton readButton = new JButton("Leer Correo");
        readButton.addActionListener(e -> leerCorreo());
        panel.add(readButton);

        
        JButton clearButton = new JButton("Limpiar Inbox");
        clearButton.addActionListener(e -> limpiarInbox());
        panel.add(clearButton);

        JButton refreshButton = new JButton("Refrescar Inbox");
        refreshButton.addActionListener(e -> mostrarInbox());
        panel.add(refreshButton);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void login() {
        String email = emailField.getText();
        String password = passwordField.getText();

        for (EmailAccount cuenta : cuentas) {
            if (cuenta != null && cuenta.getCorreo().equals(email) && cuenta.getPassword().equals(password)) {
                cuentaActual = cuenta;
                mostrarInbox();
                return;
            }
        }
        mostrarMensaje("Email o contraseña incorrectos.");
    }

    private static void crearCuenta() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String nombre = nameField.getText();

        if (cuentaIndex < cuentas.length) {
            cuentas[cuentaIndex++] = new EmailAccount(email, password, nombre);
            mostrarMensaje("Cuenta creada exitosamente.");
            limpiarCampos();
        } else {
            mostrarMensaje("No se puede crear más cuentas.");
        }
    }

    private static void enviarCorreo() {
        if (cuentaActual == null) {
            mostrarMensaje("Debes iniciar sesión primero.");
            return;
        }

        String destinatario = recipientField.getText();
        String asunto = subjectField.getText();
        String contenido = contentField.getText();

        for (EmailAccount cuenta : cuentas) {
            if (cuenta != null && cuenta.getCorreo().equals(destinatario)) {
                Email email = new Email(cuentaActual.getCorreo(), asunto, contenido);
                if (cuenta.recibirEmail(email)) {
                    mostrarMensaje("Correo enviado exitosamente.");
                    limpiarCampos();
                } else {
                    mostrarMensaje("El inbox del destinatario está lleno.");
                }
                return;
            }
        }
        mostrarMensaje("Dirección de email del destinatario no existe.");
    }

    private static void leerCorreo() {
        if (cuentaActual == null) {
            mostrarMensaje("Debes iniciar sesión primero.");
            return;
        }

        try {
            int pos = Integer.parseInt(positionField.getText()) - 1; 
            String contenido = cuentaActual.leerEmail(pos);
            if (contenido != null) {
                mostrarMensaje(contenido);
            } else {
                mostrarMensaje("No existe correo en la posición: " + (pos + 1)); 
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor ingrese un número válido.");
        }
    }

    private static void limpiarInbox() {
        if (cuentaActual == null) {
            mostrarMensaje("Debes iniciar sesión primero.");
            return;
        }

        cuentaActual.borrarLeidos();
        mostrarMensaje("Correos leídos han sido eliminados.");
        limpiarCampos();
    }

    private static void mostrarInbox() {
        if (cuentaActual != null) {
            textArea.setText(cuentaActual.printInbox()); 
        } else {
            mostrarMensaje("Debes iniciar sesión primero.");
        }
    }

    private static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void limpiarCampos() {
        emailField.setText("");
        passwordField.setText("");
        recipientField.setText("");
        subjectField.setText("");
        contentField.setText("");
        positionField.setText("");
        nameField.setText("");
    }
}
