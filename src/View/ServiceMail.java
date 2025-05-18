public class EmailService {

    private final String username = "colonel.diallo19@gmail.com";
    private final String password = "smeo pdyg taaf vlir";
    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public boolean envoyerEmail(String destinataire, String sujet, String contenu) {
        try {
            Message message = new MimeMessage(createSession());
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            Transport.send(message);
            System.out.println("✅ Email envoyé à : " + destinataire);
            return true;
        } catch (MessagingException e) {
            System.out.println("❌ Erreur lors de l'envoi de l'email : " + e.getMessage());
            return false;
        }
    }
}