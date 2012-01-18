package org.mikeSkei.battleship.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import org.mikeSkei.battleship.networking.dto.LoginDTO;
import org.mikeSkei.battleship.networking.dto.UserDTO;
import org.mikeySkei.battleship.user.User;

public class ServerConnectionImpl implements ServerConnection {

	private InetAddress serverAddr = null;
	private int portNo;
	private Socket serverConnection = null;
	private boolean connected;

	
	/* Singleton instance */
	private static final ServerConnectionImpl instance = 
		new ServerConnectionImpl();
	
	public static ServerConnectionImpl getInstance() {
		return instance;
	}
	
	public ServerConnectionImpl() {
		connected = false;
	}

	public void connectToServer(String serverAddr, int portNo)
			throws IOException {

		this.serverAddr = InetAddress.getByName(serverAddr);
		this.portNo = portNo;

		/* Bind to the socket */
		connectToServer();
	}
	
	
	public void connectToServer()
			throws IOException {
		
		serverConnection = new Socket(this.serverAddr, this.portNo);
		connected = true;
	}


	public User login(String phoneNumber) throws IOException, ClassNotFoundException {
		
		verifyConnection();
		OutputStream os = serverConnection.getOutputStream();  
		ObjectOutputStream oos = new ObjectOutputStream(os); 
		
		/* Create the transfer object */
		LoginDTO loginDTO = createLoginDTO(phoneNumber);		
		
		/* Send the transfer object */
		oos.writeObject(loginDTO); 
		oos.flush();
		
		/* Wait for the response */
		InputStream is = serverConnection.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Object o = ois.readObject();
		
		/* Close connection */
		serverConnection.close();
		connected = false;
		
		
		return createUserFromUserDTO((UserDTO)o);
	}

	private User createUserFromUserDTO(UserDTO userDTO) {
		User user = new User(userDTO);
		return user;
	}

	private LoginDTO createLoginDTO(String phoneNumber) {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setPhoneNumber(phoneNumber);
		return loginDTO;
	}

	private void verifyConnection() throws IOException {
		if ( ! connected )
			connectToServer();		
	}
	
	
	
}
