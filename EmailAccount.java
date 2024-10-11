/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1p2q4;

/**
 *
 * @author josue
 */
public class EmailAccount {

    private String correo;
    private String password;
    private String nombre;
    private Email[] inbox;

    public EmailAccount(String email, String contraseña, String nombre) {
        this.correo = email;
        this.password = contraseña;
        this.nombre = nombre;
        this.inbox = new Email[50];

    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean recibirEmail(Email em) {
        for (int contador = 0; contador < inbox.length; contador++) {
            if (inbox[contador] == null) {
                inbox[contador] = em;
                return true;
            }
        }
        return false;

    }

    public String printInbox() {
        StringBuilder inboxDisplay = new StringBuilder(); 
        inboxDisplay.append("Cuenta: ").append(correo).append(" - Nombre: ").append(nombre).append("\n");
        inboxDisplay.append("Correos recibidos:\n");

        int totalCorreos = 0;
        int unreadEmails = 0;

        for (int contador = 0; contador < inbox.length; contador++) {
            if (inbox[contador] != null) {
                totalCorreos++;
                String read = inbox[contador].getRead() ? "LEIDO" : "SIN LEER";
                if (!inbox[contador].getRead()) {
                    unreadEmails++;
                }
                inboxDisplay.append((contador + 1)).append(" – ").append(inbox[contador].getSavedEmail()).append(" – ")
                        .append(inbox[contador].getAsunto()).append(" – ").append(read).append("\n"); 
            }
        }

        inboxDisplay.append("\nTotal de correos sin leer: ").append(unreadEmails)
                .append("\nTotal de correos recibidos: ").append(totalCorreos);
        
        return inboxDisplay.toString(); 
    }

    public String leerEmail(int pos) {
        pos = pos - 1;
        if (pos >= 0 && pos < inbox.length && inbox[pos] != null) {
            inbox[pos].leido();
            return inbox[pos].getContenido();
        } else {
            return "Correo no existe";
        }

    }

    public void borrarLeidos() {
        for (int i = 0; i < inbox.length; i++) {
            inbox[i] = null;
        }
    }
}
