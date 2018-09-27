package com.fast.rxjava2.apply.retrofit.model;

/**
 * @author bowen.yan
 * @date 2018-09-18
 */
public class RegisterResp {
    private static final int SUCCESS_STATUS = 0;
    private int status;
    private content content;

    public boolean success() {
        return SUCCESS_STATUS == status;
    }

    @Override
    public String toString() {
        return "RegisterResp{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;

        @Override
        public String toString() {
            return "content{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", out='" + out + '\'' +
                    ", errNo=" + errNo +
                    '}';
        }
    }
}
