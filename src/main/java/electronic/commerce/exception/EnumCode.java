package electronic.commerce.exception;

public enum EnumCode {

    ACTIVE(1), DEACTIVATE(0);

    private Integer value;

    EnumCode(Integer code) {
        this.value = code;
    }

    public Integer getValue() {
        return this.value;
    }
}
