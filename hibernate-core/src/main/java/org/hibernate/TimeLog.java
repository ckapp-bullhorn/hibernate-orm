/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate;

import org.jboss.logging.Logger;

public class TimeLog implements AutoCloseable {
	private static final Logger LOG = Logger.getLogger( TimeLog.class );
	private final long start;
	private final String message;
	private final boolean trace;
	private boolean stillNeedToLog = true;

	public TimeLog(String message) {
		this(message, false);
	}
	public TimeLog(String message, boolean trace) {
		this.start = System.currentTimeMillis();
		this.message = message;
		this.trace = trace;

		String logMessage = message + ": started (" + Thread.currentThread().getId() + ")";
		if (trace) {
			LOG.trace(logMessage);
		}
		else {
			LOG.debug(logMessage);
		}
	}

	public void complete() {
		if (stillNeedToLog) {
			stillNeedToLog = false;
			final long duration = System.currentTimeMillis() - start;
			String logMessage = message + ": completed (" + Thread.currentThread().getId() + ") in " + duration;
			if (trace) {
				LOG.trace(logMessage);

			}
			else {
				LOG.debug(logMessage);
			}
		}
	}

	@Override
	public void close() /*throws Exception*/ {
		complete();
	}
}
