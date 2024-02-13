package com.library.Library;
 
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import org.springframework.stereotype.Component;
 
 
@Component
public class MailService  {
 
	   //Importante hacer la inyección de dependencia de JavaMailSender:

    private JavaMailSender mailSender;

    //Pasamos por parametro: destinatario, asunto y el mensaje
    @Autowired
    public MailService () {
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(25);    
        mailSender.setUsername("userdeprueba483@gmail.com");
        mailSender.setPassword("dohozwrwcenznacz");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        this.mailSender = mailSender;
    }
    // USER
    public  void sendRegistrationNotification(UserLib user) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(user.getMail());
        email.setSubject("Registrado en Libreria");
        email.setText("Bienvenido a Libreria " + user.getName()+
        		"\nAhora ya puedes realizar prestamos." );
        mailSender.send(email);
    }
    
    public  void sendUpdateUserNotification(UserLib user) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(user.getMail());
        email.setSubject("Actualizacion de datos en libreria");
        email.setText("Tus datos de han actualizado de manera correcta en la libreria.");
        mailSender.send(email);
    }
    // LOAN 
    public  void sendLoanNotification(UserLib user, Loan loan, Book book) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getMail());
        email.setSubject("Nuevo prestamo en Libreria");
        email.setText("Se acaba de crear un nuevo prestamo en la libreria." +
        		"\n El libro prestado es:"+
        		"\nTitulo: " + book.getTitle() +
        		"\nAutor: " + book.getAuthor() +
        		"\nIsbn: " + book.getIsbn() +
        		"\nFecha de devolucion: " + loan.getEnd_date() +
        		"\nPor favor recuerda devolver el libro antes de la fecha de devolucion"
        		);
        mailSender.send(email);
    }
    
    public  void sendReturnNotification(UserLib user, Loan loan, Book book) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(user.getMail());
        email.setSubject("Devolucion realizada en Libreria");
        email.setText("Se ha realizado una devolicion en Libreria."+
        		"\n El libro devuelto es:"+
        		"\nTitulo: " + book.getTitle() +
        		"\nAutor: " + book.getAuthor() +
        		"\nIsbn: " + book.getIsbn()
        		);
        mailSender.send(email);
    }
    public  void sendRememberNotification(UserLib user, Loan loan, Book book) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(user.getMail());
        email.setSubject("Recuerdo de devolucion en Libreria");
        email.setText("La fecha de devolucion de un prestamo esta cerca, por favor realiza la devolucion."+
        		"\n El libro es:"+
        		"\nTitulo: " + book.getTitle() +
        		"\nAutor: " + book.getAuthor() +
        		"\nIsbn: " + book.getIsbn()
        		);
        mailSender.send(email);
    }
    
    //PENALTY
    public  void sendPenaltyNotification(UserLib user, Penalty penalty, Book book) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(user.getMail());
        email.setSubject("Nueva penalizacion en libreria");
        email.setText("Tienes una nueva penalizacion en Libreria al no haber devuelto un libro antes de la fecha de devolucion."+
        		"\n El libro es:"+
        		"\nTitulo: " + book.getTitle() +
        		"\nAutor: " + book.getAuthor() +
        		"\nIsbn: " + book.getIsbn()+
        		"\nLa penalizacion sera desde hoy hasta :"+ penalty.getEnd_date()
        		);
        mailSender.send(email);
    }
  

    public  void sendPenaltyDeleteNotification(UserLib user, Penalty penalty) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(user.getMail());
        email.setSubject("Fin de penalizacion en Libreria");
        email.setText("Tu penalizacion en Libreria ha finalizado."+
        		"\nYa puedes volver a realizar nuevos prestamos."
        		);
        mailSender.send(email);
    }
}
