package br.uem.comons;

import java.io.BufferedWriter;
import java.io.IOException;

public class Writer extends java.io.Writer {

	private BufferedWriter bufferedWriter;

	public Writer(BufferedWriter bufferedWriter) {
		super(bufferedWriter);
		this.bufferedWriter = bufferedWriter;
	}

	@Override
	public void close() throws IOException {
		this.bufferedWriter.close();
	}

	@Override
	public void flush() throws IOException {
		this.bufferedWriter.flush();
	}

	@Override
	public void write(char[] arg0, int off, int len) throws IOException {
		this.bufferedWriter.write(arg0, off, len);
	}

}
