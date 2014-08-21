package net.flower.ixmsxms.async;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

// influenced by
// https://github.com/mttkay/ignition/blob/master/ignition-core/ignition-core-lib/src/com/github/ignition/core/tasks/IgnitedAsyncTask.java
// https://github.com/roboguice/roboguice/blob/master/roboguice/src/main/java/roboguice/util/SafeAsyncTask.java
public abstract class EnhancedAsyncTask<Param, Result> extends AsyncTask<Param, Object, Result> {
	private RuntimeException exception;

	protected abstract Result doInBackgroundInternal(Param... params);

	protected AsyncTaskHandler<Result> delegateHandler;

	protected ErrorHandler errorHandler;

	protected Callback<Result> successCallback;

	protected Callback<RuntimeException> failCallback;

	public EnhancedAsyncTask<Param, Result> setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		return this;
	}

	public EnhancedAsyncTask<Param, Result> setDelegateHandler(AsyncTaskHandler<Result> delegateHandler) {
		this.delegateHandler = delegateHandler;
		return this;
	}

	public EnhancedAsyncTask<Param, Result> success(Callback<Result> successCallback) {
		this.successCallback = successCallback;
		return this;
	}

	public EnhancedAsyncTask<Param, Result> fail(Callback<RuntimeException> failCallback) {
		this.failCallback = failCallback;
		return this;
	}

	@Override
	protected Result doInBackground(Param... params) {
		try {
			return doInBackgroundInternal(params);
		} catch (RuntimeException e) {
			exception = e;
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
		if (delegateHandler != null) {
			delegateHandler.onTaskStarted();
		}
		onTaskStarted();
		super.onPreExecute();
	}

	@Override
	protected void onCancelled() {
		handleTaskCancelled();
		super.onCancelled();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCancelled(Result result) {
		handleTaskCancelled();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			super.onCancelled(result);
		} else {
			super.onCancelled();
		}
	}

	@Override
	protected void onPostExecute(Result result) {
		handleTaskCompleted();
		if (exception != null) {
			handleTaskFailed(exception);
		} else {
			handleTaskSucceeded(result);
		}
	}

	private void handleTaskCompleted() {
		if (delegateHandler != null) {
			delegateHandler.onTaskCompleted();
		}
		onTaskCompleted();
	}

	private void handleTaskSucceeded(Result result) {
		if (delegateHandler != null) {
			delegateHandler.onTaskSucceeded(result);
		}
		if (successCallback != null) {
			successCallback.call(result);
		}
		onTaskSucceeded(result);
	}

	private void handleTaskFailed(RuntimeException exception) {
		if (delegateHandler != null) {
			delegateHandler.onTaskFailed(exception);
		}
		if (errorHandler != null) {
			errorHandler.execute(exception);
		}
		if (failCallback != null) {
			failCallback.call(exception);
		}
		onTaskFailed(exception);
	}

	private void handleTaskCancelled() {
		if (delegateHandler != null) {
			delegateHandler.onTaskCompleted();
		}
		onTaskCompleted();
	}

	protected void onTaskStarted() {
	}

	protected void onTaskCompleted() {
	}

	protected void onTaskFailed(RuntimeException e) {
	}

	protected void onTaskSucceeded(Result result) {
	}

	public interface ErrorHandler {
		void execute(RuntimeException e);
	}

	public interface AsyncTaskHandler<Result> {
		void onTaskStarted();

		void onTaskCompleted();

		void onTaskSucceeded(Result result);

		void onTaskFailed(RuntimeException e);
	}

	public static class SimpleAsyncTaskHandler<Result> implements AsyncTaskHandler<Result> {

		@Override
		public void onTaskStarted() {
		}

		@Override
		public void onTaskCompleted() {
		}

		@Override
		public void onTaskSucceeded(Result result) {
		}

		@Override
		public void onTaskFailed(RuntimeException e) {
		}
	}
}
