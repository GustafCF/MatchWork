package com.br.MatchWork.entity.enums;

public enum TypeContract {

    CLT_PERMANENT(1),
    CLT_TEMPORARY(2),
    PJ_PERMANENT(3),
    PJ_TEMPORARY(4);

    private int code;

    private TypeContract(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TypeContract valueCode(int code) {
        for(TypeContract value : TypeContract.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code!");
    }

}
