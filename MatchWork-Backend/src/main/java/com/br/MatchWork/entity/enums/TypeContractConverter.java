package com.br.MatchWork.entity.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeContractConverter implements AttributeConverter<TypeContract, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TypeContract contract) {
        if (contract == null) {
            return null;
        }
        return contract.getCode();
    }

    @Override
    public TypeContract convertToEntityAttribute(Integer code) {
        if(code == null) {
            return null;
        }
        return TypeContract.valueCode(code);
    }

}
