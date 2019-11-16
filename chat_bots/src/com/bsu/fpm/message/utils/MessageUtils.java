package com.bsu.fpm.message.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.bsu.fpm.message.data.AuthorType;
import com.bsu.fpm.message.data.Message;

public class MessageUtils {

	private MessageUtils() {
		throw new UnsupportedOperationException();
	}
	
	public static final boolean isPersonalMessage(Message message) {
		return AuthorType.PERSON == message.getAuthor().getAuthorType();
	}
	
	public static final byte[] writeToBytes(Message message) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try(ObjectOutputStream oos = new ObjectOutputStream(baos)){
		
			oos.writeObject(message);
			return baos.toByteArray();
		
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static final Message readFromString(byte[] bytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		try(ObjectInputStream oos = new ObjectInputStream(bais)){
			
			return (Message) oos.readObject();

		} catch (IOException e) {
			throw e;
		}catch (Throwable e) {
			throw new IOException(e);
		}
	}


}
