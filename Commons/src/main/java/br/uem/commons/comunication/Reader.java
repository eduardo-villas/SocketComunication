package br.uem.commons.comunication;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader extends java.io.Reader {

	private BufferedReader bufferedReader;

	public Reader(BufferedReader bufferedReader) {
		super(bufferedReader);
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void close() throws IOException {
		this.bufferedReader.close();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		
		int offset = off;
		int messageLength = 0;
		do {
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
		int sizePacket = Integer.parseInt(stringPacketSize); 
		return sizePacket;
	}
	
	public char[] readBytes(int off, int len) throws IOException {
		len = len - off;
		char buffer[] = new char[len];
		this.read(buffer, 0, len);
		return buffer; 
	}

}
