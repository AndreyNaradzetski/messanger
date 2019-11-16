package com.bsu.fpm.message.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.bsu.fpm.message.utils.ObjectUtils;
import com.bsu.fpm.message.utils.StringUtils;

public class Author implements Externalizable{

	private AuthorType authorType;
	
	private String authorName;
	
	public Author() {
		super();
	}

	public Author(AuthorType authorType, String authorName) {
		super();
		if ( ObjectUtils.isNull(authorType) || StringUtils.isEmpty(authorName)) {
			throw new IllegalArgumentException("null author type or author name");
		}
		this.authorType = authorType;
		this.authorName = authorName;
	}

	public AuthorType getAuthorType() {
		return authorType;
	}

	public String getAuthorName() {
		return authorName;
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.authorType = AuthorType.valueOf(in.readUTF());
		this.authorName = in.readUTF();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(getAuthorType().toString());
		out.writeUTF(getAuthorName());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((authorType == null) ? 0 : authorType.hashCode());
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
		Author other = (Author) obj;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (authorType != other.authorType)
			return false;
		return true;
	}
}
