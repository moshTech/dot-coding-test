package com.mosh.dot_assessment_test.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mosh
 * @role software engineer
 * @createdOn 06 Fri Sep, 2024
 */
public class ApiResponseJson<T> {
    private String message;
    private boolean success;
    private T data;
    private Map<String, Object> metadata;
    private List<ApiError> errors = new ArrayList();

    public void addError(ApiError error) {
        this.errors.add(error);
    }

    public void addError(String errorMessage) {
        this.errors.add(new ApiError(errorMessage));
    }

    public static <T> ApiResponseJsonBuilder<T> builder() {
        return new ApiResponseJsonBuilder();
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public List<ApiError> getErrors() {
        return this.errors;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setErrors(final List<ApiError> errors) {
        this.errors = errors;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiResponseJson)) {
            return false;
        } else {
            ApiResponseJson<?> other = (ApiResponseJson)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != other.isSuccess()) {
                return false;
            } else {
                label49: {
                    Object this$message = this.getMessage();
                    Object other$message = other.getMessage();
                    if (this$message == null) {
                        if (other$message == null) {
                            break label49;
                        }
                    } else if (this$message.equals(other$message)) {
                        break label49;
                    }

                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$errors = this.getErrors();
                Object other$errors = other.getErrors();
                if (this$errors == null) {
                    if (other$errors != null) {
                        return false;
                    }
                } else if (!this$errors.equals(other$errors)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ApiResponseJson;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $errors = this.getErrors();
        result = result * 59 + ($errors == null ? 43 : $errors.hashCode());
        return result;
    }

    public ApiResponseJson() {
    }

    public ApiResponseJson(final String message, final boolean success, final T data, final Map<String, Object> metadata,final List<ApiError> errors) {
        this.message = message;
        this.success = success;
        this.data = data;
        this.metadata = metadata;
        this.errors = errors;
    }

    public String toString() {
        String var10000 = this.getMessage();
        return "ApiResponseJson(message=" + var10000 + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", metadata=" + this.getMetadata() + ", errors=" + this.getErrors() + ")";
    }

    public static class ApiResponseJsonBuilder<T> {
        private String message;
        private boolean success;
        private T data;
        private Map<String, Object> metadata;
        private List<ApiError> errors;

        ApiResponseJsonBuilder() {
        }

        public ApiResponseJsonBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public ApiResponseJsonBuilder<T> success(final boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseJsonBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        public ApiResponseJsonBuilder<T> metadata(final Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public ApiResponseJsonBuilder<T> errors(final List<ApiError> errors) {
            this.errors = errors;
            return this;
        }

        public ApiResponseJson<T> build() {
            return new ApiResponseJson(this.message, this.success, this.data, this.metadata, this.errors);
        }

        public String toString() {
            return "ApiResponseJson.ApiResponseJsonBuilder(message=" + this.message + ", success=" + this.success + ", data=" + this.data + ", metadata=" + this.metadata + ", errors=" + this.errors + ")";
        }
    }
}

