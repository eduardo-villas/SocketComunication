package br.uem.commons.stats;

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
		return (finish - start);
	}
	
	public static long toMiliseconds(long nanoseconds) {
		return nanoseconds / 1000000;
	}

}
