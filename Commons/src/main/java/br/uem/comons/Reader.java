package br.uem.comons;

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
		return this.bufferedReader.read(cbuf, off, len);
	}

}
