package com.example.mockdemo.messenger;

public class MessageServiceImpl implements MessageService {

	private static final String domainRegex = ".*\\.pl";
	
	public ConnectionStatus checkConnection(String server) {
		if (server.matches(domainRegex)) return ConnectionStatus.SUCCESS;
		return ConnectionStatus.FAILURE;
	}

	public SendingStatus send(String server, String message) throws MalformedRecipientException {
		if (message.length() < 3 || server.length() < 4) throw new MalformedRecipientException();
		if (checkConnection(server) == ConnectionStatus.FAILURE) return SendingStatus.SENDING_ERROR;
		
		return SendingStatus.SENT;
	}
	

}
