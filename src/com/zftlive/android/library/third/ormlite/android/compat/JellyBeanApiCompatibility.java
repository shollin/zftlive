package com.zftlive.android.library.third.ormlite.android.compat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;

/**
 * Basic class which provides no-op methods for all Android version.
 * 
 * <p>
 * <b>NOTE:</b> Will show as in error if compiled with previous Android versions.
 * </p>
 * 
 * @author graywatson
 */
public class JellyBeanApiCompatibility extends BasicApiCompatibility {

	@Override
	public Cursor rawQuery(SQLiteDatabase db, String sql, String[] selectionArgs, CancellationHook cancellationHook) {
		if (cancellationHook == null) {
			return db.rawQuery(sql, selectionArgs);
		} else {
			return db.rawQuery(sql, selectionArgs, ((JellyBeanCancellationHook) cancellationHook).cancellationSignal);
		}
	}

	@Override
	public CancellationHook createCancellationHook() {
		return new JellyBeanCancellationHook();
	}

	protected static class JellyBeanCancellationHook implements CancellationHook {

		private final CancellationSignal cancellationSignal;

		public JellyBeanCancellationHook() {
			this.cancellationSignal = new CancellationSignal();
		}

		public void cancel() {
			cancellationSignal.cancel();
		}
	}
}
