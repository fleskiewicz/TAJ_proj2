package com.example.mockdemo.app;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

import static org.hamcrest.CoreMatchers.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(EasyMockRunner.class)
public class MessengerEasyMockTest {
	
	@Mock(type = MockType.NICE)
	private MessageService msgSrvMock;
	
	@TestSubject
	private Messenger messenger = new Messenger(msgSrvMock);
	
	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";

	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "ab";
	

	@Test
	public void testConnection_ShouldReturn1WhenServerIsInvalid() {
		expect(msgSrvMock.checkConnection(INVALID_SERVER)).andReturn(ConnectionStatus.FAILURE);
		replay(msgSrvMock);
		int actual = messenger.testConnection(INVALID_SERVER);
		assertEquals(1, actual);
	}
	
	@Test
	public void testConnection_ShouldReturn0WhenServerIsValid() {
		expect(msgSrvMock.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS);
		replay(msgSrvMock);
		int actual = messenger.testConnection(VALID_SERVER);
		assertEquals(0, actual);
	}
	
	@Test
	public void sendMessage_ShouldReturn0WhenMessageIsProperlySent() throws MalformedRecipientException {
		expect(msgSrvMock.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT);
		replay(msgSrvMock);
		int actual = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertEquals(0, actual);
	}
	
	@Test
	public void sendMessage_ShouldReturn1WhenServerIsNotAccepted() throws MalformedRecipientException {
		expect(msgSrvMock.send(INVALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR);
		replay(msgSrvMock);
		int actual = messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE);
		assertThat(actual, is(1));
	}
	
	@Test
	public void sendMessage_ShouldReturn1WhenMessageIsTooShort() throws MalformedRecipientException {
		expect(msgSrvMock.send(VALID_SERVER, INVALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR);
		replay(msgSrvMock);
		int actual = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertSame(1, actual);
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenMessageIsEmptyString() throws MalformedRecipientException {
		expect(msgSrvMock.send(VALID_SERVER, "")).andThrow(new MalformedRecipientException());
		replay(msgSrvMock);
		int actual = messenger.sendMessage(VALID_SERVER, "");
		assertTrue(actual == 2);
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenServerIsEmptyString() throws MalformedRecipientException {
		expect(msgSrvMock.send("", VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(msgSrvMock);
		int actual = messenger.sendMessage("", VALID_MESSAGE);
		assertFalse(actual != 2);
	}
	
	@Test
	public void sendMessage_ShouldReturn2WhenBothServerAndMessageAreEmptyStrings() throws MalformedRecipientException {
		expect(msgSrvMock.send("", "")).andThrow(new MalformedRecipientException());
		replay(msgSrvMock);
		int actual = messenger.sendMessage("", "");
		assertEquals(2, actual);
	}

	@After
	public void tearDown() {
		messenger = null;
	}

}
