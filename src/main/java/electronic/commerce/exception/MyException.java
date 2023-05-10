package electronic.commerce.exception;

public class MyException extends Exception{
private Integer errorCode;

    public MyException(Integer errorCode,String message) {
        super(message);
        this.errorCode=errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }


}
