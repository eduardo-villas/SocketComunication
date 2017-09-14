package br.uem.client.main;

public class ElapTime {
	
	private long start;
	private long finish;
	
	public void start() {
		this.start =  System.nanoTime();
	}
	
	public void finish() {
		this.finish = System.nanoTime();
	}
	
	public long elapTime() {
		return (finish - start)/1000;
	}
	
}
