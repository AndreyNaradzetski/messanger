package com.bsu.fpm.message.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Stream;

import com.bsu.fpm.message.data.Message;
import com.bsu.fpm.message.utils.MessageUtils;
import com.bsu.fpm.message.utils.StringUtils;

public class MessageModel {
	
	private static final String FILE_NAME = "message_history.txt";
	
	private static final String USER_HOME = System.getProperty("user.home");
	
	private static MessageModel instance = null;
	
	public static MessageModel getInstance() {
		if ( instance == null ) {
			synchronized (MessageModel.class) {
				if ( instance == null ) {
					instance = new MessageModel();
				}
			}
		}
		return instance;
	}

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private final Lock writeLock = lock.writeLock();
	
	private final Lock readLock = lock.readLock();
	
	private Path pathToFile ;
	
	private MessageModel() {
		super();
	}
	
	private Path getPath() throws IOException {
		if ( pathToFile == null ) {
			String path = StringUtils.appendIfMissed(USER_HOME, File.separator) + FILE_NAME;
			File file = new File( path );
			file.createNewFile();
			pathToFile = Paths.get(path);
		}
		return pathToFile;
	} 
	
	private byte[] encode(byte[] bytes) {
		return Base64.getEncoder().encode(bytes);
	}
	
	private byte[] decode(String line) {
		return Base64.getDecoder().decode(line);
	}
	
	public void clearHistory() throws IOException {
		Files.deleteIfExists( getPath());
		pathToFile = null;
	}

	public void save(Message message) throws IOException {
		writeLock.lock();
		try(BufferedWriter bw = Files.newBufferedWriter(getPath(), StandardOpenOption.APPEND)){ 
			Files.write(getPath(), encode(MessageUtils.writeToBytes(message)), StandardOpenOption.APPEND);
			bw.newLine();
		} catch (IOException e) {
			//re-throw
			throw e;
		} finally {
			writeLock.unlock();
		}
	}
	
	public Message[] read() throws IOException{
		readLock.lock();
		try (Stream<String> stream = Files.lines(getPath(), Charset.defaultCharset())) {
	        return stream.map( new Function<String, Message>() {
	        	@Override
	        	public Message apply(String line) {
	        		try {
						return MessageUtils.readFromString(decode(line));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
	        	}
			}).toArray((size)-> new Message[size]);
		} catch (IOException e) {
			//re-throw
			throw e;
		} catch (Throwable e) {
			//re-throw
			throw new IOException(e);
		} finally {
			readLock.unlock();
		}
	}
	
}
