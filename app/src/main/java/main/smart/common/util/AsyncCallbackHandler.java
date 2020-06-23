package main.smart.common.util;

public abstract interface AsyncCallbackHandler {


	public abstract void onFailure(int paramInt, String paramString);

	  public abstract void onSuccess(int paramInt, Object paramObject);

}
