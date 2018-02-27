package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendFile extends ObjectOutputStream {

	public AppendFile(OutputStream arg0) throws IOException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		// TODO Auto-generated method stub
		reset();
	}
}
