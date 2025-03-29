package com.slowv.udemi.service.mapper;


import com.slowv.udemi.entity.AccountEntity;
import com.slowv.udemi.entity.RoleEntity;
import com.slowv.udemi.entity.enums.TokenType;
import com.slowv.udemi.service.dto.AccountRecord;
import com.slowv.udemi.service.dto.request.SignUpRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        config = DefaultConfigMapper.class,
        uses = {AccountInfoMapper.class},
        imports = {TokenType.class, DateTimeUtils.class}
)
public interface AccountMapper extends EntityMapper<AccountRecord, AccountEntity> {
//    @Mapping(target = "accessToken", source = "token", qualifiedByName = "toTokenRecord")
//    @Mapping(target = "refreshToken", source = "refreshToken", qualifiedByName = "toTokenRecord")
//    @Mapping(target = "email", source = "token.subject")
//    @Mapping(target = "accountInfo", source = "accountInfo")
//    @Mapping(target = "roleNames", source = "roles", qualifiedByName = "toRoleString")
//    AccountRecord toDto(Jwt token, Jwt refreshToken, AccountInfoEntity accountInfo, List<RoleEntity> roles);
//
//    @Named("toTokenRecord")
//    default TokenRecord toTokenRecord(Jwt token) {
//        return new TokenRecord(token.getTokenValue(), DateTimeUtils.convertToLocalDateTime(token.getExpiresAt()), TokenType.Bearer.name());
//    }

    @Mapping(target = "password", ignore = true)
    AccountEntity toEntity(SignUpRequest request);

    @Named("toRoleString")
    default List<String> toRoleString(List<RoleEntity> roles) {
        return roles.stream().map(RoleEntity::getName).map(Enum::name).toList();
    }
}
