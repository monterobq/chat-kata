package chat.kata

import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

class ChatService {

	private final List<ChatMessage> messages = new ArrayList()
	private final ReadWriteLock lock = new ReentrantReadWriteLock()
	
	/**
	 * Collects chat messages in the provided collection
	 *
	 * @param if specified messages are collected from the provided sequence (exclusive)
	 * @param messages the collection where to add collected messages
	 *
	 * @return the sequence of the last message collected.
	 */
	Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
		lock.readLock().lock()
		def last = messages.size()
		def first = fromSeq == null? 0 :  fromSeq+1
		collector.addAll(messages.subList(first, last))
		lock.readLock().unlock()
		return last-1
	}
	
	/**
	 * Puts a new message at the bottom of the chat
	 *
	 * @param message the message to add to the chat
	 */
	void putChatMessage(ChatMessage message){
		lock.writeLock().lock()
		messages.add(message)
		lock.writeLock().unlock()
	}
	
	
}
