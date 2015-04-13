package com.awalterbos.jarvis.server.interfaces;

public interface Radio<T> {

	public int getCodeword();

	public T setCodeword(int codeword);

}
