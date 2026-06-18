package com.br.MatchWork.entity.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JobModelConverter implements AttributeConverter<JobModel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(JobModel job) {
        if (job == null) {
            return null;
        }
        return job.getCode();
    }

    @Override
    public JobModel convertToEntityAttribute(Integer code) {
        if(code == null) {
            return null;
        }
        return JobModel.valueCode(code);
    }
}
