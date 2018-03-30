package com.bedrock.origin.common;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class OkHttpRequestBody extends RequestBody{

	final MediaType MEDIA_TYPE_MARKDOWN= MediaType.parse("text/x-markdown; charset=utf-8");
	
	String stream;
	
	private OkHttpRequestBody(){};
	
	public OkHttpRequestBody(String stream)
	{
		this.stream=stream;
	}
	
	@Override
	public MediaType contentType() {
		return MEDIA_TYPE_MARKDOWN;
	}

	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		sink.writeUtf8(stream);
	}

}
