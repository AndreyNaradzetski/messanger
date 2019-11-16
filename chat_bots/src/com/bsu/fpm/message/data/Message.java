package com.bsu.fpm.message.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import com.bsu.fpm.message.utils.ObjectUtils;

public class Message implements Externalizable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8004702675961956095L;

	private Author author;
	
	private Date date;
	
	private String message;

	public Message(Author author, String message) {
		this(new Date(), author, message);
	}
	
	public Message() {
		super();
	}
	
	public Message(Date date, Author author, String message) {
		super();
		if ( ObjectUtils.isNull(date) || ObjectUtils.isNull(author) || ObjectUtils.isNull(message)) {
			throw new IllegalArgumentException("null date / author or message");
		}
		this.author = author;
		this.date = date;
		this.message = message;
	}

	public Author getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.author = (Author)in.readObject();
		this.message = in.readUTF();
		this.date = new Date(in.readLong());
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(getAuthor());
		out.writeUTF(getMessage());
		out.writeLong(getDate().getTime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
}
