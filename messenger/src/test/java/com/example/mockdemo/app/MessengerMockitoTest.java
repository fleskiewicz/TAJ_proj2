package com.example.mockdemo.app;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

@RunWith(MockitoJUnitRunner.class)
public class MessengerMockitoTest {

	@Mock
	private MessageService msgSrvMock;
	
	@InjectMocks
	private Messenger messenger;
	
	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";

	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "ab";
	

	@Test
	public void testConnection_ShouldReturn1WhenServerIsInvalid() {
		when(msgSrvMock.checkConnection(INVALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
		int actual = messenger.testConnection(INVALID_SERVER);
		assertEquals(1, actual);
	}
	
	@Test
	public void testConnection_ShouldReturn0WhenServerIsValid() {
		when(msgSrvMock.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.SUCCESS);
		int actual = messenger.testConnection(VALID_SERVER);
		assertEquals(0, actual);
		verify(msgSrvMock).checkConnection(VALID_SERVER);
	}
	
	@Test
	public void sendMessage_ShouldReturn0WhenMessageIsProperlySent() throws MalformedRecipientException {
		when(msgSrvMock.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
		int actual = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertEquals(0, actual);
		verify(msgSrvMock).send(VALID_SERVER, VALID_MESSAGE);
	}
	
	@Test
	public void sendMessage_ShouldReturn1WhenServerIsNotAccepted() throws MalformedRecipientException {
		when(msgSrvMock.send(INVALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
		int actual = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		assertThat(actual, is(1));
		verify(msgSrvMock).send(INVALID_SERVER, VALID_MESSAGE);
	}
	
	@Test
	public void sendMessage_ShouldReturn1WhenMessageIsTooShort() throws MalformedRecipientException {
		when(msgSrvMock.send(VALID_SERVER, INVALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
		int actual = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertSame(1, actual);
		verify(msgSrvMock).send(VALID_SERVER, INVALID_MESSAGE);
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenMessageIsNull() throws MalformedRecipientException {
		when(msgSrvMock.send(VALID_SERVER, null)).thenThrow(MalformedRecipientException.class);
		int actual = messenger.sendMessage(VALID_SERVER, null);
		assertTrue(actual == 2);
		verify(msgSrvMock).send(VALID_SERVER, null);
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenServerIsNull() throws MalformedRecipientException {
		when(msgSrvMock.send(null, VALID_MESSAGE)).thenThrow(MalformedRecipientException.class);
		int actual = messenger.sendMessage(null, VALID_MESSAGE);
		assertFalse(actual != 2);
		verify(msgSrvMock).send(null, VALID_MESSAGE);
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenBothServerAndMessageAreNull() throws MalformedRecipientException {
		when(msgSrvMock.send(null, null)).thenThrow(MalformedRecipientException.class);
		int actual = messenger.sendMessage(null, null);
		assertEquals(2, actual);
		verify(msgSrvMock).send(null, null);
	}

	@After
	public void tearDown() {
		messenger = null;
	}
	
	
}
