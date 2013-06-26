package org.ejmc.android.simplechat.model;

public class NextSeq {
	
	private int nextSeq;
	
	public NextSeq() {
		nextSeq = 0;
	}
	
	public synchronized int getNextSeq() {
		return nextSeq;
	}
	
	public synchronized void setNextSeq(int nextSeq) {
		this.nextSeq = nextSeq;
	}

}
