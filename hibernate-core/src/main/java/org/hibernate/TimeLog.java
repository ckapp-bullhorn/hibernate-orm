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
	private boolean stillNeedToLog;

	public TimeLog(String message) {
		this.start = System.currentTimeMillis();
		this.message = message;
		LOG.info(message + ": started (" + Thread.currentThread().getId() + ")");
	}

	public void complete() {
		if (stillNeedToLog) {
			stillNeedToLog = false;
			final long duration = System.currentTimeMillis() - start;
			LOG.info(message + ": completed (" + Thread.currentThread().getId() + ") in " + duration);
		}
	}

	@Override
	public void close() /*throws Exception*/ {
		complete();
	}
}
