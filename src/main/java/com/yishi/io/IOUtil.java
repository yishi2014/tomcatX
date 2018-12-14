package com.yishi.io;

import com.yishi.bit.calculate.BitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

public class IOUtil {
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    private static Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static byte[] getByteFromStream(InputStream in){
        return getByteFromStream(in,-1);
    }

    public static byte[] getByteFromStream(InputStream in, int length) {
        byte[] bytes;
        try {
            if (length < 0 || length > in.available()) {
                length = in.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        bytes = new byte[length];
        try {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = BitUtil.int2Byte(in.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bytes;
    }


//    public static byte[] getStreamAsByteArray(InputStream stream, int length) throws IOException {
//        if (length == 0) {
//            return new byte[0];
//        } else {
//            boolean checkLength = true;
//            if (length < 0) {
//                length = 2147483647;
//                checkLength = false;
//            }
//
//            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//            int nextValue = stream.read();
//            if (checkLength) {
//                --length;
//            }
//
//            while (nextValue > -1 && length >= 0) {
//                byteStream.write(nextValue);
//                nextValue = stream.read();
//                if (checkLength) {
//                    --length;
//                }
//            }
//
//            return byteStream.toByteArray();
//        }
//    }

    public static void writeWithBuffer_throw_close(InputStream in, OutputStream out) {
        writeWithBuffer(in, out, DEFAULT_BUFFER_SIZE, true, true);
    }

    public static void writeWithBuffer_nonThrow_colse(InputStream in, OutputStream out) {
        writeWithBuffer(in, out, DEFAULT_BUFFER_SIZE, false, true);
    }

    public static void writeWithBuffer_nonThrow_nonColse(InputStream in, OutputStream out) {
        writeWithBuffer(in, out, DEFAULT_BUFFER_SIZE, false, false);
    }

    public static void writeTextWithBuffer_throw_close(Reader in, Writer out) {
        writeTextWithBuffer(in, out, true, true);
    }

    public static void writeTextWithBuffer(Reader in, Writer out, boolean throwException, boolean close) {
        if (in == null) {
            if (throwException)
                throw new RuntimeException("inputStream is null");
            else
                return;
        }
        if (out == null) {
            if (throwException)
                throw new RuntimeException("outPutStream is null");
            else
                return;
        }
        BufferedReader br;
        BufferedWriter bw;
        if (in instanceof BufferedReader) {
            br = (BufferedReader) in;
        } else {
            br = new BufferedReader(in);
        }
        if (out instanceof BufferedWriter) {
            bw = (BufferedWriter) out;
        } else {
            bw = new BufferedWriter(out);
        }
        int c;
        try {
            while ((c = br.read()) > -1) {
                bw.write(c);
            }
        } catch (Exception e) {
            if (throwException)
                throw new RuntimeException(e);
            else {
                e.printStackTrace();
                return;
            }
        } finally {
            if (close) {
                try {
                    if (br != null)
                        br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (bw != null)
                        bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void writeTextWithBuffer(InputStream in, OutputStream out, boolean throwException, boolean close, String incomeEncoding, String outputEncoding) {
        if (in == null) {
            if (throwException)
                throw new RuntimeException("inputStream is null");
            else
                return;
        }
        if (out == null) {
            if (throwException)
                throw new RuntimeException("outPutStream is null");
            else
                return;
        }
        int c;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, incomeEncoding));
            bw = new BufferedWriter(new OutputStreamWriter(out, outputEncoding));
            while ((c = br.read()) > -1) {
                bw.write(c);
            }
        } catch (Exception e) {
            if (throwException)
                throw new RuntimeException(e);
            else {
                e.printStackTrace();
                return;
            }
        } finally {
            if (close) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (br != null)
                        br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (bw != null)
                        bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void writeWithBuffer(InputStream in, OutputStream out, int buffSize, boolean throwException, boolean close) {
        if (in == null) {
            if (throwException)
                throw new RuntimeException("inputStream is null");
            else
                return;
        }
        if (out == null) {
            if (throwException)
                throw new RuntimeException("outPutStream is null");
            else
                return;
        }
        if (buffSize < 1) {
            buffSize = DEFAULT_BUFFER_SIZE;
        }
        byte[] buffer = new byte[buffSize];
        int length;
        try {
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
                if (logger.isDebugEnabled()) {
                    logger.debug(Arrays.toString(buffer));
                }
            }
        } catch (Exception e) {
            if (throwException)
                throw new RuntimeException(e);
            else {
                e.printStackTrace();
                return;
            }
        } finally {
            if (close) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
