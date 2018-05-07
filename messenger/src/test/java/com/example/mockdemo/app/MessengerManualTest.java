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
	
	@Test
	public void testConnection_ShouldReturn1WhenServerIsInvalid() {
		int actual = messenger.testConnection(INVALID_SERVER);
		assertEquals(1, actual);
	}
	
	@Test
	public void testConnection_ShouldReturn0WhenServerIsValid() {
		int actual = messenger.testConnection(VALID_SERVER);
		assertEquals(0, actual);
	}
	
	@Test
	public void sendMessage_ShouldReturn0WhenMessageIsProperlySent() {
		int actual = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertEquals(0, actual);
	}
	
	@Test
	public void sendMessage_ShouldReturn1WhenServerIsNotAccepted() {
		int actual = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		assertThat(actual, is(1));
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenMessageIsTooShort() {
		int actual = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertSame(2, actual);
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenMessageIsEmptyString() {
		int actual = messenger.sendMessage(VALID_SERVER, "");
		assertTrue(actual == 2);
	}
	
	@AfterEach
	public void tearDown() {
		messenger = null;
	}
	
	
}
