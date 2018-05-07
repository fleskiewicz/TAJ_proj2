package com.example.mockdemo.app;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.MessageServiceImpl;

public class MessengerManualTest {

	private MessageService msgSrvMock;
	private Messenger messenger;
	
	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";

	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "ab";
	
	@BeforeEach
	public void setUp() {
		msgSrvMock = new MessageServiceImpl();
		messenger = new Messenger(msgSrvMock);
	}
	
	@AfterEach
	public void tearDown() {
		messenger = null;
	}
	
	
}
