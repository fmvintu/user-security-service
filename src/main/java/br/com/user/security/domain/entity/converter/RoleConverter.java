package br.com.user.security.domain.entity.converter;

import br.com.user.security.domain.entity.enu.RoleEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<RoleEnum, String> {

    @Override
    public String convertToDatabaseColumn(RoleEnum attribute) {
        return attribute.getRoleValue();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String dbData) {
        return RoleEnum.fromValue(dbData)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not file role: [%s] in enum", dbData)));
    }
}
