package net.flower.ixmsxms.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

public abstract class SimpleAsyncTask<Result> extends EnhancedAsyncTask<Void, Result> {
	protected Context context;
	private View button;

	protected abstract Result doInBackgroundInternal();


    protected SimpleAsyncTask(Context context) {
		this.context = context;
		useDefaultErrorHandler(context);

	}

	/**
	 * AsyncTask가 실행되는 동안 button의 동작을 중지시킨다.
	 */
	public SimpleAsyncTask<Result> useButtonBlocking(View button) {
		this.button = button;
		return this;
	}

	@Override
	protected void onTaskStarted() {
		if (button != null && button.isEnabled()) {
			button.setClickable(false);
		}
		super.onTaskStarted();
	}

	@Override
	protected void onTaskCompleted() {
		if (button != null) {
			button.setClickable(true);
		}
		super.onTaskCompleted();
	}

	@Override
	protected Result doInBackgroundInternal(Void... voids) {
		return doInBackgroundInternal();
	}


	@Override
	public SimpleAsyncTask<Result> setErrorHandler(ErrorHandler errorHandler) {
		super.setErrorHandler(errorHandler);
		return this;
	}

	@Override
	public SimpleAsyncTask<Result> setDelegateHandler(AsyncTaskHandler<Result> delegateHandler) {
		super.setDelegateHandler(delegateHandler);
		return this;
	}

	@Override
	public SimpleAsyncTask<Result> success(Callback<Result> successCallback) {
		super.success(successCallback);
		return this;
	}

	@Override
	public SimpleAsyncTask<Result> fail(Callback<RuntimeException> failCallback) {
		super.fail(failCallback);
		return this;
	}

	public AsyncTask<Void, Object, Result> execute() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return super.executeOnExecutor(THREAD_POOL_EXECUTOR);
		} else {
			return super.execute();
		}
	}

	public Context getContext() {
		return context;
	}

	public SimpleAsyncTask<Result> useDefaultErrorHandler(Context context) {
		setErrorHandler(new DefaultErrorHandler(context));
		return this;
	}

	/**
	 * 에러 메시지를 Toast로 표시해주는 ErrorHandler
	 */
	public static class DefaultErrorHandler implements ErrorHandler {
		private final Context context;

		public DefaultErrorHandler(Context context) {
			this.context = context;
		}

		@Override
		public void execute(RuntimeException e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}


}
