package cn.jiuyou2020.nettransmit.protocolencoding;

public class RpcMessage {
    public static final short MAGIC = (short) 0xCAFEBABE;      //2 bytes
    public static final short HEADER_SIZE = 21;                //2 bytes
    public static final short VERSION = 1;                     //2 bytes
    private byte serializationType;                     //1 byte //0是json序列化，1是protobuf序列化
    private boolean isHeartbeat;                        //1 byte
    private boolean isOneWay;                           //1 byte
    private boolean isResponse;                         //1 byte
    private byte statusCode;                            //1 byte
    private short reserved;                             //2 bytes
    private int messageId;                              //4 bytes
    private int bodySize;                               //4 bytes
    private byte[] body;                                //n bytes

    private RpcMessage(Builder builder) {
        this.serializationType = builder.serializationType;
        this.isHeartbeat = builder.isHeartbeat;
        this.isOneWay = builder.isOneWay;
        this.isResponse = builder.isResponse;
        this.statusCode = builder.statusCode;
        this.reserved = builder.reserved;
        this.messageId = builder.messageId;
        this.bodySize = builder.bodySize;
        this.body = builder.body;
    }

    public static class Builder {
        private byte serializationType;
        private boolean isHeartbeat;
        private boolean isOneWay;
        private boolean isResponse;
        private byte statusCode;
        private short reserved;
        private int messageId;
        private int bodySize;
        private byte[] body;

        public Builder setSerializationType(byte serializationType) {
            this.serializationType = serializationType;
            return this;
        }

        public Builder setIsHeartbeat(boolean isHeartbeat) {
            this.isHeartbeat = isHeartbeat;
            return this;
        }

        public Builder setIsOneWay(boolean isOneWay) {
            this.isOneWay = isOneWay;
            return this;
        }

        public Builder setIsResponse(boolean isResponse) {
            this.isResponse = isResponse;
            return this;
        }

        public Builder setStatusCode(byte statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder setReserved(short reserved) {
            this.reserved = reserved;
            return this;
        }

        public Builder setMessageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setBodySize(int bodySize) {
            this.bodySize = bodySize;
            return this;
        }

        public Builder setBody(byte[] body) {
            this.body = body;
            return this;
        }

        public RpcMessage build() {
            // Automatically set the body size if body is provided
            if (this.body != null) {
                this.bodySize = this.body.length;
            }
            return new RpcMessage(this);
        }
    }

    // Getters
    public byte getSerializationType() {
        return serializationType;
    }

    public boolean isHeartbeat() {
        return isHeartbeat;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public byte getStatusCode() {
        return statusCode;
    }

    public short getReserved() {
        return reserved;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getBodySize() {
        return bodySize;
    }

    public byte[] getBody() {
        return body;
    }
}
