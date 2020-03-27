package NetworkMessages;

import Databases.IDataBase;
import Utils.Protocol;
import Utils.Return;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PasswordRecoveryMessage implements ServerMessage
{
	private static final long serialVersionUID = 8L;

	public PasswordRecoveryMessage()
	{
		super();
	}

	private String username;

	public PasswordRecoveryMessage(String username)
	{
		this.username = username;
	}

	@Override public ClientDisplayMessage perform(IDataBase db, Protocol protocol)
	{
		// Get the password
		ResultSet rs = db.query("SELECT PASSWORD, EMAIL FROM USERS WHERE USERNAME = '" + username + "'");

		try
		{
			System.out.println(rs.getMetaData().getColumnCount());

			if (rs.next())
			{
				String recoveredPassword = rs.getString(1);
				String toEmail = rs.getString(2);

				String fromServerEmail = "teambelieveandreceive@gmail.com";
				String fromServerPassword =  "CSC3351234";

				Properties props = new Properties();

				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

				Session session = Session.getInstance(props, new Authenticator()
				{
					@Override protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(fromServerEmail, fromServerPassword);
					}
				});

				MimeMessage msg = new MimeMessage(session);

				InternetAddress[] address = InternetAddress.parse(toEmail, true);

				msg.setRecipients(Message.RecipientType.TO, address);

				String timeStamp = new SimpleDateFormat("20191114_07:37:00").format(new Date());
				msg.setSubject("Password Recovery: " + timeStamp);
				msg.setSentDate(new Date());

				// -- set the message text
				msg.setText("Your Recovered Password is : " + recoveredPassword);
				msg.setHeader("XPriority", "1");

				// -- send the message
				Transport.send(msg);

				Return updateLock = db.update("UPDATE USERS set LOCKED = 0 WHERE USERNAME = '" + username + "'");

				return protocol.genSuccess("Password Recovery Email Sent");
			}
			else
			{
				return protocol.genFail("Please Try Again, Password Unable to be Recovered");
			}

		} catch (SQLException | MessagingException e)
		{
			e.printStackTrace();
			return protocol.genFail("Please Try Again, Password Unable to be Recovered");
		}
	}
}
