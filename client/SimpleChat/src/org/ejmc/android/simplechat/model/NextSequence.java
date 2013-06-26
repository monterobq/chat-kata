package org.ejmc.android.simplechat.model;

public class NextSequence {
	
	private int nextSeq;
	
	public NextSequence() {
		nextSeq = 0;
	}
	
	public synchronized int getNextSeq() {
		return nextSeq;
	}
	
	public synchronized void setNextSeq(int nextSeq) {
		this.nextSeq = nextSeq;
	}

}
