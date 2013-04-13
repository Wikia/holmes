package com.wikia.reader.util;

import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.*;
import java.nio.file.Path;
import java.rmi.UnexpectedException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 31.03.13
 * Time: 16:55
 */
public class AsyncFile implements CompletionHandler<Integer, ByteBuffer> {
    private static Logger logger = Logger.getLogger(AsyncFile.class.toString());
    private final AsynchronousFileChannel asynchronousFileChannel;
    private int bufferSize;
    // private final ByteBuffer byteBuffer;
    //private final ByteBuffer fullBuffer;
    private Charset charset = Charset.forName("UTF-8");
    private CharsetDecoder decoder = charset.newDecoder();
    private FutureCallback<String> callback;
    private int position;
    private StringBuilder stringBuilder = new StringBuilder();


    public AsyncFile(Path path) throws IOException {
        this(AsynchronousFileChannel.open(path));
    }

    public AsyncFile(AsynchronousFileChannel asynchronousFileChannel) {
        this(asynchronousFileChannel, 1024*16 ); // !!
    }

    public AsyncFile(AsynchronousFileChannel asynchronousFileChannel, int bufferSize) {
        this.asynchronousFileChannel = asynchronousFileChannel;
        this.bufferSize = bufferSize;
        //this.fullBuffer = ByteBuffer.allocate(bufferSize * 2);
    }

    public void start(FutureCallback futureCallback) {
        if(callback != null) throw new IllegalStateException("Already started.");
        callback = futureCallback;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferSize);
        asynchronousFileChannel.read(byteBuffer, 0, byteBuffer, this);
    }

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
        if( result == -1 ) {
            try {
                finnish();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Cannot close file.", e);
            }
        } else {
            position += result;
            byteBuffer.flip();
            CharBuffer charBuffer = ByteBuffer.allocateDirect(bufferSize*2).asCharBuffer();
            CoderResult coderResult = decoder.decode(byteBuffer, charBuffer, false);
            if( coderResult.isError() ) {
                logger.severe("Error while reading file.");
                try {
                    coderResult.throwException();
                } catch (CharacterCodingException e) {
                    logger.log(Level.SEVERE, "Error while reading file.", e);
                }
            }
            if( coderResult.isUnderflow() ) {
                logger.finer( "Underflow " + byteBuffer.position() + " " + byteBuffer.limit() );
                byteBuffer.compact();
                logger.finer( "After " + byteBuffer.position() + " " + byteBuffer.limit() );
            } else {
                byteBuffer.flip();
            }
            charBuffer.flip();
            while( charBuffer.remaining() > 0 ) {
                stringBuilder.append( charBuffer.get() );
            }
            asynchronousFileChannel.read(byteBuffer, position, byteBuffer, this);
        }
    }

    private void finnish() throws IOException {
        asynchronousFileChannel.close();
        //fullBuffer.flip();
        String result = stringBuilder.toString();
        callback.completed(result);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            asynchronousFileChannel.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Cannot close.", e);
        }
        if( exc instanceof Exception ) callback.failed((Exception) exc);
        else callback.failed(new UnexpectedException("Throwable is not exception."));
    }
}
