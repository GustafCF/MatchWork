package com.br.MatchWork.entity.enums;

public enum JobModel {

    INPERSON(1),
    HYBRID(2),
    REMOTE(3);

    private int code;

    private JobModel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static JobModel valueCode(int code) {
        for(JobModel value: JobModel.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code!");
    }

}
