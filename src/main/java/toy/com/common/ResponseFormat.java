package toy.com.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({ "status", "data" })
public class ResponseFormat<T> {

    private T data;

    private Status status;

    public ResponseFormat(ResponseCode responseCode, T data) {
        this.bindMeta(responseCode);
        this.data = data;
    }

    private void bindMeta(ResponseCode responseCode) {
        this.status = new Status(responseCode);
    }

    public static <T> ResponseFormat<T> normal(T data) {
        return new ResponseFormat<>(ResponseCode.OK, data);
    }

    public static <T> ResponseFormat<T> exception(T data) {
        return new ResponseFormat<>(ResponseCode.ERROR, data);
    }

    @Getter
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Status {

        private int code;
        private String message;

        public Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public Status(ResponseCode responseCode) {
            this.code = responseCode.getCode();
            this.message = responseCode.getMessage();
        }
    }
}
