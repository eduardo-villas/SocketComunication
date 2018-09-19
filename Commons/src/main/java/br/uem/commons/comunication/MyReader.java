package br.uem.commons.comunication;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;

public class MyReader extends java.io.Reader {
 
	private BufferedReader bufferedReader;

	public MyReader(BufferedReader bufferedReader) {
		super(bufferedReader);
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void close() throws IOException {
		this.bufferedReader.close();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int messageLength = 0;
		try {
			messageLength = tryRead(cbuf, off, len);
		} catch (Exception e) {
			Logger.getLogger(MyReader.class).error("Erro ao tentar ler os dados.", e);
			throw e;
		}
		
		return messageLength;
	}

	public int tryRead(char[] cbuf, int off, int len) throws IOException {
		int offset = off;
		int messageLength = 0;
		do {
			Preconditions.checkState(offset >= 0, "Erro. O offset igual a {0} não pode ser menor que zero.", offset);
			offset = this.bufferedReader.read(cbuf, offset, len);
			messageLength += offset;
			len -= offset;
		} while (hasMoreBytes(cbuf, offset, len, messageLength));
		return messageLength;
	}
	
	private boolean hasMoreBytes(char[] buffer, int offset, int len, int lenMessage) {
		return len > 0;
	}
	
	public int readHeader(int off, int len) throws IOException {
		char buffer[] = readBytes(off, len);
		String stringPacketSize = new String(buffer, 0, Constants.HEADER_SIZE).trim();
		return Integer.parseInt(stringPacketSize);
	}
	
	public char[] readBytes(int off, int len) throws IOException {
		len = len - off;
		char buffer[] = new char[len];
		this.read(buffer, 0, len);
		return buffer; 
	}

}
