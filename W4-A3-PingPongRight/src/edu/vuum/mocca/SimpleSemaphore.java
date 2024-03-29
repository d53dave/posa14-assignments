package edu.vuum.mocca;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject (which is accessed via a
 *        Condition). It must implement both "Fair" and "NonFair" semaphore
 *        semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
	/**
	 * Define a ReentrantLock to protect the critical section.
	 */
	ReentrantLock mLock;

	/**
	 * Define a Condition that waits while the number of permits is 0.
	 */
	Condition mCondition;

	/**
	 * Define a count of the number of available permits.
	 */
	// TODO - you fill in here. Make sure that this data member will
	// ensure its values aren't cached by multiple Threads..
	volatile int mAvailablePermits;

	public SimpleSemaphore(int initialPermits, boolean fair) {
		// TODO - you fill in here to initialize the SimpleSemaphore,
		// making sure to allow both fair and non-fair Semaphore
		// semantics.
		this.mLock = new ReentrantLock(fair);
		mCondition = mLock.newCondition();
		// Prof. Schmidt mentioned in the officehours that the constructor need
		// not be locked for this operation
		mAvailablePermits = initialPermits;
	}

	/**
	 * Acquire one permit from the semaphore in a manner that can be
	 * interrupted.
	 */
	public void acquire() throws InterruptedException {
		// TODO - you fill in here.
		mLock.lockInterruptibly();
		try {
			while (mAvailablePermits < 1) {
				mCondition.await();
			}
			--mAvailablePermits;
		} finally {
			mLock.unlock();
		}

	}

	/**
	 * Acquire one permit from the semaphore in a manner that cannot be
	 * interrupted.
	 */
	public void acquireUninterruptibly() {
		mLock.lock();
		try {
			while (this.availablePermits() < 1) {
				mCondition.awaitUninterruptibly();
			}
			--mAvailablePermits;
		} finally {
			mLock.unlock();
		}
	}

	/**
	 * Return one permit to the semaphore.
	 */
	void release() {
		mLock.lock();
		try {
			++mAvailablePermits;
			mCondition.signal(); // this will tell one
		} finally {
			mLock.unlock();
		}
	}

	/**
	 * Return the number of permits available.
	 */
	public int availablePermits() {
		// TODO - you fill in here by changing null to the appropriate
		// return value.
		return mAvailablePermits; // volatile, therefore doesn't need a lock.
	}
}
