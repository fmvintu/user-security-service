package br.com.user.security.service;

import br.com.user.security.cache.Caches;
import br.com.user.security.domain.dto.UserDto;
import br.com.user.security.domain.dto.UserInfoDto;
import br.com.user.security.domain.entity.UserInfo;
import br.com.user.security.domain.mapper.UserDataMapper;
import br.com.user.security.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserDataMapper userDataMapper;

    public UserInfoDto save(UserInfoDto userInfoDto, UserDto userDto) {
        UserInfo userInfo = userDataMapper.toUserInfo(userInfoDto);
        userInfo.setUser(userDataMapper.toUser(userDto));

        return userDataMapper.toUserInfoDto(userInfoRepository.save(userInfo));
    }

    @Cacheable(cacheNames = Caches.USERNAME_USERINFO_CACHE_NAME, key = "#userName", unless = "#result == null")
    public Optional<UserInfoDto> getByUserName(String userName) {
        return userInfoRepository.findByUserName(userName)
                .map(ui -> userDataMapper.toUserInfoDto(ui));
    }

    @Transactional(transactionManager = "transactionManager", readOnly = true)
    @Cacheable(cacheNames = Caches.ID_USERINFO_CACHE_NAME, key = "#id", unless = "#result == null")
    public Optional<UserInfoDto> getUserInfo(Long userInfoUid) {
        return userInfoRepository.findById(userInfoUid)
                .map(c -> userDataMapper.toUserInfoDto(c));
    }

    @Transactional(transactionManager = "transactionManager", readOnly = true)
    public Optional<List<UserInfoDto>> getAllUserInfo() {
        List<UserInfoDto> userInfoDtos = userInfoRepository.findAll().stream()
                .map(c -> userDataMapper.toUserInfoDto(c))
                .collect(toList());

        if(userInfoDtos.isEmpty()) {
            return empty();
        }

        return of(userInfoDtos);
    }
}
