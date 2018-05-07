package com.example.mockdemo.app;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

public class Messenger {

	private MessageService ms;

	public Messenger(MessageService ms) {
		this.ms = ms;
	}

	public int testConnection(String server) {
		if (ms.checkConnection(server) == ConnectionStatus.FAILURE) return 1;
		return 0;
	}

	public int sendMessage(String server, String message) {
		try {
			SendingStatus sendStatus = ms.send(server, message);
			if (sendStatus == SendingStatus.SENDING_ERROR) return 1;
		} catch (MalformedRecipientException mre) {
				return 2;
		}
		return 0;
	}
}
