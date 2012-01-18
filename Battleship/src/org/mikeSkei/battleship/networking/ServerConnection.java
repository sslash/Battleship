package org.mikeSkei.battleship.networking;

import java.io.IOException;
import org.mikeySkei.battleship.user.User;

public interface ServerConnection {
	
	void connectToServer(String serverAddr, int portNo) throws IOException;
	User login(String phoneNumber) throws IOException, ClassNotFoundException;
	void connectToServer() throws IOException;	
}
